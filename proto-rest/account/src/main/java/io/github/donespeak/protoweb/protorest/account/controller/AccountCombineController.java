package io.github.donespeak.protoweb.protorest.account.controller;

import com.google.protobuf.GeneratedMessageV3;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message;
import io.github.donespeak.protoweb.protorest.account.proto.rest.AccountApi;
import io.github.donespeak.protoweb.protorest.account.proto.rest.api.GetAccount;
import io.github.donespeak.protoweb.protorest.account.proto.rest.api.GetAccountRequest;
import io.github.donespeak.protoweb.protorest.account.proto.rest.api.GetAccountResponse;
import io.github.donespeak.protoweb.protorest.account.proto.rest.api.ListAccount;
import io.github.donespeak.protoweb.protorest.account.proto.rest.api.ListAccountRequest;
import io.github.donespeak.protoweb.protorest.account.proto.rest.api.ListAccountResponse;
import io.github.donespeak.protoweb.protorest.account.proto.rest.data.Account;
import io.github.donespeak.protoweb.protorest.core.annotation.ProtoApi;
import io.github.donespeak.protoweb.protorest.core.annotation.ProtoEndpoint;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * {code RequestMapping} 会覆盖 {code ProtoApi} 中设置的路径
 * @author Yang Guanrong
 */
@RequestMapping("/cb")
@ProtoApi(AccountApi.class)
public class AccountCombineController {

    /**
     * ProtoEndpoint#path 会覆盖 ProtoEndpoint#value 中的配置
     */
    // GET cb/cb-list-account
    @ProtoEndpoint(method = RequestMethod.GET, path = "cb-list-account", value = ListAccount.class)
    public ListAccountResponse listAccountCustomizePath(ListAccountRequest.Builder requestBuilder) {
        MessageLogger.log(requestBuilder);
        return ListAccountResponse.newBuilder().build();
    }

    // GET cb/account
    @ProtoEndpoint(ListAccount.class)
    public ListAccountResponse listAccount(ListAccountRequest.Builder requestBuilder) {
        MessageLogger.log(requestBuilder);
        // do something
        ListAccountResponse response = ListAccountResponse.newBuilder()
            .addAccounts(Account.newBuilder().setEmail("gr@gr.com").build())
            .build();
        return response;
    }
}
