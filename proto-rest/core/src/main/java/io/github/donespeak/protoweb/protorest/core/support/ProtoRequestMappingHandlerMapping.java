package io.github.donespeak.protoweb.protorest.core.support;

import com.google.protobuf.DescriptorProtos;
import com.google.protobuf.Descriptors;
import io.github.donespeak.protoweb.protorest.core.annotation.ProtoApi;
import io.github.donespeak.protoweb.protorest.core.annotation.ProtoEndpoint;
import io.github.donespeak.protoweb.protorest.setting.proto.ApiEndpoint;
import io.github.donespeak.protoweb.protorest.setting.proto.ApiGlobal;
import io.github.donespeak.protoweb.protorest.setting.proto.Rest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.Map;

/**
 * @author Yang Guanrong
 */
@Slf4j public class ProtoRequestMappingHandlerMapping extends RequestMappingHandlerMapping {

    @Override protected RequestMappingInfo getMappingForMethod(Method method, Class<?> handlerType) {
        try {
            changeProtoEndpointPath(method, handlerType);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
        RequestMappingInfo info = super.getMappingForMethod(method, handlerType);
        return info;
    }

    private void changeProtoEndpointPath(Method method, Class<?> handlerType)
        throws NoSuchFieldException, IllegalAccessException {
        if (method.isAnnotationPresent(ProtoEndpoint.class)) {
            ProtoEndpoint protoEndpoint = method.getAnnotation(ProtoEndpoint.class);
            Class<? extends com.google.protobuf.GeneratedMessageV3> endpoint = protoEndpoint.value();
            ProtoApi protoApi = null;
            if (!handlerType.isAnnotationPresent(RequestMapping.class) && handlerType.isAnnotationPresent(ProtoApi.class)) {
                protoApi = handlerType.getAnnotation(ProtoApi.class);
            }
            configProtoEndpoint(protoEndpoint, protoApi, endpoint);
        }
    }

    private void configProtoEndpoint(ProtoEndpoint protoEndpoint, ProtoApi protoApi,
        Class<? extends com.google.protobuf.GeneratedMessageV3> endpoint)
        throws IllegalAccessException, NoSuchFieldException {

        String[] paths = protoEndpoint.path();
        if (paths.length > 0) {
            // 自定义的路径覆盖默认配置路径
            return;
        }

        ApiEndpoint apiEndpoint = null;
        try {
            apiEndpoint = getApiEndPointConfig(endpoint);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException("Fail to get ApiEndpoint option from " + endpoint.getName(), e);
        }
        Assert.isTrue(!StringUtils.isEmpty(apiEndpoint.getPath()), "ApiEndpoint#path shouldn't be empty.");
        Assert.isTrue(!StringUtils.isEmpty(apiEndpoint.getMethod()), "ApiEndpoint#method shouldn't be empty.");
        RequestMethod requestMethod = null;
        try {
            requestMethod = RequestMethod.valueOf(apiEndpoint.getMethod().toUpperCase());
        } catch (Exception e) {
            String message = String
                .format("The value of ApiEndpoint#method should be one of %s", Arrays.toString(RequestMethod.values()));
            throw new IllegalArgumentException(message, e);
        }
        paths = new String[] {apiEndpoint.getPath()};
        if(protoApi != null) {
            ApiGlobal apiGlobal = null;
            try {
                apiGlobal = getApiGlobalConfig(protoApi.value());
            } catch (ReflectiveOperationException e) {
                throw new IllegalArgumentException("Fail to get ApiGlobal from " + protoApi.getClass().getName(), e);
            }
            for(int i = 0;i < paths.length; i ++) {
                paths[i] = connectPath(apiGlobal.getPath(), paths[i]);
            }
        }

        try {
            setValueForAnnotation(protoEndpoint, "path", paths);
            setValueForAnnotation(protoEndpoint, "method", new RequestMethod[] {requestMethod});
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException("Fail to set field value of annotation " + protoEndpoint.getClass().getName(), e);
        }
    }

    private void setValueForAnnotation(ProtoEndpoint protoEndpoint, String field, Object value)
        throws ReflectiveOperationException {
        InvocationHandler requestMappingHandler = Proxy.getInvocationHandler(protoEndpoint);
        Field memberValuesFiled = requestMappingHandler.getClass().getDeclaredField("memberValues");
        memberValuesFiled.setAccessible(true);
        Map memberValues = (Map)memberValuesFiled.get(requestMappingHandler);
        memberValues.put(field, value);
    }

    private String connectPath(String... paths) {
        if (paths.length == 0) {
            return "";
        }
        StringBuilder pathLink = new StringBuilder();
        for (String path : paths) {
            if (StringUtils.isEmpty(path)) {
                continue;
            }
            if (path.startsWith("/")) {
                path = path.substring(1);
            }
            if (!path.endsWith("/")) {
                path += "/";
            }
            pathLink.append(path);
        }
        String result = pathLink.toString();
        return paths[paths.length - 1].endsWith("/") ? result : result.substring(0, result.length() - 1);
    }

    private void changeRequestMappingPath(ProtoEndpoint protoEndpoint, String path)
        throws NoSuchFieldException, IllegalAccessException {
        InvocationHandler requestMappingHandler = Proxy.getInvocationHandler(protoEndpoint);
        // spring的annotation的代理类为：SynthesizedAnnotationInvocationHandler
        //
        Field memberValuesFiled = requestMappingHandler.getClass().getDeclaredField("memberValues");
        memberValuesFiled.setAccessible(true);
        Map memberValues = (Map)memberValuesFiled.get(requestMappingHandler);

        memberValues.put("prePath", new String[] {path});
    }

    private ApiGlobal getApiGlobalConfig(Class<? extends com.google.protobuf.GeneratedMessageV3> apiGlobalClazz)
        throws ReflectiveOperationException {

        DescriptorProtos.MessageOptions options = getMessageOptions(apiGlobalClazz);

        Map<Descriptors.FieldDescriptor, Object> optionMap = options.getAllFields();
        if (optionMap.containsKey(Rest.global.getDescriptor())) {
            return (ApiGlobal)optionMap.get(Rest.global.getDescriptor());
        } else {
            return ApiGlobal.getDefaultInstance();
        }
    }

    private ApiEndpoint getApiEndPointConfig(Class<? extends com.google.protobuf.GeneratedMessageV3> endpointClazz)
        throws ReflectiveOperationException {

        DescriptorProtos.MessageOptions options = getMessageOptions(endpointClazz);

        Map<Descriptors.FieldDescriptor, Object> optionMap = options.getAllFields();
        if (optionMap.containsKey(Rest.endpoint.getDescriptor())) {
            return (ApiEndpoint)optionMap.get(Rest.endpoint.getDescriptor());
        } else {
            return ApiEndpoint.getDefaultInstance();
        }
    }

    private DescriptorProtos.MessageOptions getMessageOptions(Class<? extends com.google.protobuf.GeneratedMessageV3> endpointClazz)
        throws ReflectiveOperationException {

            Method method = endpointClazz.getMethod("getDescriptor");
            com.google.protobuf.Descriptors.Descriptor descriptor =
                (com.google.protobuf.Descriptors.Descriptor)method.invoke(null);

            return descriptor.getOptions();
    }
}
