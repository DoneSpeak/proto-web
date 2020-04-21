package io.github.donespeak.protoweb.protorest.account.controller;

import io.github.donespeak.protoweb.protorest.account.proto.rest.AccountApi;
import io.github.donespeak.protoweb.protorest.account.proto.rest.api.ListAccount;
import io.github.donespeak.protoweb.protorest.account.proto.rest.api.ListAccountRequest;
import io.github.donespeak.protoweb.protorest.account.proto.rest.api.ListAccountResponse;
import io.github.donespeak.protoweb.protorest.core.annotation.ProtoApi;
import io.github.donespeak.protoweb.protorest.core.annotation.ProtoEndpoint;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author Yang Guanrong
 */
@ProtoApi(AccountApi.class)
public class AccountCustomizedController {

    @ProtoEndpoint(ListAccount.class)
    public ListAccountResponse listAccount(@PathVariable long accountId, @RequestBody ListAccountRequest request) {
        ListAccountResponse response = ListAccountResponse.newBuilder().build();
        return response;
    }
}
