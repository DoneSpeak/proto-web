package io.github.donespeak.protoservice.account.endpoint;

import com.google.protobuf.GeneratedMessageV3;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message;
import io.github.donespeak.protoservice.account.proto.AccountApi;
import io.github.donespeak.protoservice.account.proto.api.GetAccount;
import io.github.donespeak.protoservice.account.proto.api.GetAccountRequest;
import io.github.donespeak.protoservice.account.proto.api.GetAccountResponse;
import io.github.donespeak.protoservice.account.proto.api.ListAccount;
import io.github.donespeak.protoservice.account.proto.api.ListAccountRequest;
import io.github.donespeak.protoservice.account.proto.api.ListAccountResponse;
import io.github.donespeak.protoservice.account.proto.data.Account;
import io.github.donespeak.protoservice.core.annotation.ProtoApi;
import io.github.donespeak.protoservice.core.annotation.ProtoEndpoint;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author Yang Guanrong
 */
@RequestMapping("proto/")
@ProtoApi(AccountApi.class)
public class AccountCombineController {

    // POST proto/AccountApi/bee/ListAccount
    @ProtoEndpoint(prePath = {"/bee"}, value = ListAccount.class)
    public Object listAccount(@RequestBody Map<String, Object> param) {
        return param;
    }

    // POST proto/AccountApi/ListAccount
    @ProtoEndpoint(ListAccount.class)
    public ListAccountResponse listAccount(@RequestBody ListAccountRequest request) {
        // do something
        ListAccountResponse response = ListAccountResponse.newBuilder()
            .addAccounts(Account.newBuilder().setEmail("gr@gr.com").build())
            .setPage(request.getPage())
            .build();
        return response;
    }
    // POST proto/AccountApi/GetAccount
    @ProtoEndpoint(GetAccount.class)
    public GetAccountResponse getAccount(@RequestBody GetAccountRequest request) {
        GetAccount getAccount = AccountApi.newBuilder()
            .getGetAccountBuilder()
            .setRequest(request)
            .build();
        getAccount.getResponse();
        try {
            request(AccountApi.class, getAccount);
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
        return GetAccountResponse.newBuilder().build();
    }

    private Message request(Class<? extends GeneratedMessageV3> api, GetAccount endpoint)
        throws InvalidProtocolBufferException {
        String prefix = api.getClass().getSimpleName();
        String path = endpoint.getClass().getSimpleName();
        String url = String.format("%s/%s", prefix, path);
        String result = "";
        return endpoint.getResponse().newBuilderForType()
            .mergeFrom(result.getBytes()).build();
    }
}
