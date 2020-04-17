package io.github.donespeak.protoservice.account;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.protobuf.Message;
import io.github.donespeak.protoservice.account.proto.AccountApi;
import io.github.donespeak.protoservice.account.proto.api.ListAccount;
import io.github.donespeak.protoservice.account.proto.api.ListAccountRequest;
import io.github.donespeak.protoservice.common.proto.Pagination;
import io.github.donespeak.protoservice.core.util.ProtobufHttpClient;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Yang Guanrong
 */
public class ProtoEndpointTest {

    private static String HOST = "http://localhost:8080";

    private static Map<String, String> getAuthHeaders() {
        Map<String, String > headers = new HashMap<>();
        headers.put("content-type", "application/x-protobuf");
        headers.put("Authorization", "Bearer eyJhbGciOiJIUzUxMiJ9...");
        return headers;
    }

    @Test
    public void testPostModeling() throws IOException {
        String uri = HOST + "/proto/AccountApi/ListAccount";
        Pagination page = Pagination.newBuilder()
            .setPage(1)
            .setSize(100)
            .build();
        ListAccountRequest request = ListAccountRequest.newBuilder()
            .setPage(page)
            .build();
        AccountApi accountApi = AccountApi.newBuilder().build();
        ListAccount listAccount = accountApi.getListAccount().newBuilderForType()
            .setRequest(request)
            .build();

        Message message = ProtobufHttpClient.post(listAccount.getRequest(), uri, getAuthHeaders(), listAccount.getResponse().newBuilderForType());
        System.out.println(com.google.protobuf.util.JsonFormat.printer().print(message));
    }

}
