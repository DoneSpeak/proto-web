package io.github.donespeak.protoweb.protorest.account;

import com.google.protobuf.DescriptorProtos;
import com.google.protobuf.Descriptors;
import com.google.protobuf.GeneratedMessageV3;
import io.github.donespeak.protoweb.protorest.account.proto.rest.api.GetAccount;
import io.github.donespeak.protoweb.protorest.account.proto.rest.data.Account;
import io.github.donespeak.protoweb.protorest.setting.proto.ApiEndpoint;
import io.github.donespeak.protoweb.protorest.setting.proto.Rest;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * @author Yang Guanrong
 */
public class GetAccountOptionTest {

    public static void main(String[] args)
        throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        DescriptorProtos.MessageOptions options = GetAccount.getDescriptor().getOptions();
        for(Map.Entry<Descriptors.FieldDescriptor, Object> entry: options.getAllFields().entrySet()) {
            System.out.println("OPTION: " + entry.getKey() + ": " + entry.getValue() + "-" + entry.getValue().getClass());
            System.out.println(entry.getKey().getClass().getName() + ": " + entry.getValue().getClass());
        }

        ApiEndpoint api = (ApiEndpoint) options.getAllFields().get(Rest.endpoint.getDescriptor());

        System.out.println(api);

        test(GetAccount.class);
        test(Account.class);
    }

    public static void test(Class<? extends GeneratedMessageV3> clazz)
        throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = clazz.getMethod("getDescriptor");
        com.google.protobuf.Descriptors.Descriptor descriptor = (com.google.protobuf.Descriptors.Descriptor) method.invoke(null);

        DescriptorProtos.MessageOptions options = descriptor.getOptions();
        Map<Descriptors.FieldDescriptor, Object> optionMap = options.getAllFields();
        if(optionMap.containsKey(Rest.endpoint.getDescriptor())) {
            ApiEndpoint api = (ApiEndpoint)optionMap.get(Rest.endpoint.getDescriptor());
            System.out.println(api);
        } else {
            System.out.println("Not found");
        }
    }
}
