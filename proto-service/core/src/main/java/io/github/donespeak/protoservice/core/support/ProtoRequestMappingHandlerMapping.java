package io.github.donespeak.protoservice.core.support;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;

import org.springframework.util.StringUtils;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import io.github.donespeak.protoservice.core.annotation.ProtoApi;
import io.github.donespeak.protoservice.core.annotation.ProtoEndpoint;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Yang Guanrong
 * @date 2020/04/17 11:20
 */
@Slf4j
public class ProtoRequestMappingHandlerMapping extends RequestMappingHandlerMapping {

    private ProtoEndpointConverter protoEndpointConverter;

    public void setProtoEndpointConverter(ProtoEndpointConverter protoEndpointConverter) {
        this.protoEndpointConverter = protoEndpointConverter;
    }

    @Override
    protected RequestMappingInfo getMappingForMethod(Method method, Class<?> handlerType) {
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
            String basePath = getBasePath(handlerType);
            changeProtoEndpointPath(protoEndpoint, basePath, endpoint);
        }
    }

    private String getBasePath(Class<?> handlerType) {
        if (handlerType.isAnnotationPresent(ProtoApi.class)) {
            ProtoApi protoApi = handlerType.getAnnotation(ProtoApi.class);
            Class<? extends com.google.protobuf.GeneratedMessageV3> endpoint = protoApi.value();
            return getEndPointPath(endpoint);
        }
        return "";
    }

    private void changeProtoEndpointPath(ProtoEndpoint protoEndpoint, String basePath,
        Class<? extends com.google.protobuf.GeneratedMessageV3> endpoint)
        throws IllegalAccessException, NoSuchFieldException {
        String endpointPath = getEndPointPath(endpoint);
        String[] paths = protoEndpoint.prePath().length == 0 ? new String[] {""} : protoEndpoint.prePath();

        for (int i = 0; i < paths.length; i++) {
            paths[i] = connectPath(basePath, paths[i], endpointPath);
        }
        InvocationHandler requestMappingHandler = Proxy.getInvocationHandler(protoEndpoint);
        Field memberValuesFiled = requestMappingHandler.getClass().getDeclaredField("memberValues");
        memberValuesFiled.setAccessible(true);
        Map memberValues = (Map)memberValuesFiled.get(requestMappingHandler);

        memberValues.put("prePath", paths);
    }

    private String connectPath(String... paths) {
        if(paths.length == 0) {
            return "";
        }
        StringBuilder pathLink = new StringBuilder();
        for(String path: paths) {
            if(StringUtils.isEmpty(path)) {
                continue;
            }
            if(path.startsWith("/")) {
                path = path.substring(1);
            }
            if(!path.endsWith("/")) {
                path += "/";
            }
            pathLink.append(path);
        }
        String result = pathLink.toString();
        return paths[paths.length - 1].endsWith("/")? result: result.substring(0, result.length() - 1);
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

    private String getEndPointPath(Class<? extends com.google.protobuf.GeneratedMessageV3> endpoint) {
        String path = endpoint.getSimpleName();
        if (protoEndpointConverter == null) {
            return path;
        }
        return protoEndpointConverter.convert(path);
    }
}
