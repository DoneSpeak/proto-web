package io.github.donespeak.protoweb.protorest.account.controller;

import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.util.JsonFormat;
import io.github.donespeak.protoweb.protorest.account.proto.rest.AccountApi;
import io.github.donespeak.protoweb.protorest.account.proto.rest.api.CreateAccount;
import io.github.donespeak.protoweb.protorest.account.proto.rest.api.CreateAccountRequest;
import io.github.donespeak.protoweb.protorest.account.proto.rest.api.CreateAccountResponse;
import io.github.donespeak.protoweb.protorest.account.proto.rest.api.DeleteAccount;
import io.github.donespeak.protoweb.protorest.account.proto.rest.api.DeleteAccountRequest;
import io.github.donespeak.protoweb.protorest.account.proto.rest.api.DeleteAccountResponse;
import io.github.donespeak.protoweb.protorest.account.proto.rest.api.GetAccount;
import io.github.donespeak.protoweb.protorest.account.proto.rest.api.GetAccountRequest;
import io.github.donespeak.protoweb.protorest.account.proto.rest.api.GetAccountResponse;
import io.github.donespeak.protoweb.protorest.account.proto.rest.api.ListAccount;
import io.github.donespeak.protoweb.protorest.account.proto.rest.api.ListAccountRequest;
import io.github.donespeak.protoweb.protorest.account.proto.rest.api.ListAccountResponse;
import io.github.donespeak.protoweb.protorest.account.proto.rest.api.UpdateAccount;
import io.github.donespeak.protoweb.protorest.account.proto.rest.api.UpdateAccountRequest;
import io.github.donespeak.protoweb.protorest.account.proto.rest.api.UpdateAccountResponse;
import io.github.donespeak.protoweb.protorest.account.proto.rest.data.Account;
import io.github.donespeak.protoweb.protorest.core.annotation.ProtoApi;
import io.github.donespeak.protoweb.protorest.core.annotation.ProtoEndpoint;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author Yang Guanrong
 */
@Slf4j
@ProtoApi(AccountApi.class)
public class AccountCustomizedController {

    @ProtoEndpoint(ListAccount.class)
    public ListAccountResponse listAccount(ListAccountRequest.Builder requestBuilder) {
        MessageLogger.log(requestBuilder);
        ListAccountResponse response = ListAccountResponse.newBuilder()
            .addAccounts(Account.newBuilder().setFirstName("First name"))
            .build();
        return response;
    }

    @ProtoEndpoint(GetAccount.class)
    public GetAccountResponse getAccount(@PathVariable long accountId, GetAccountRequest.Builder requestBuilder) {
        log.info("accountId: {}", accountId);
        MessageLogger.log(requestBuilder);
        GetAccountResponse response = GetAccountResponse.newBuilder()
            .setAccount(Account.newBuilder().setEmail("gg@gg.com").setFirstName("Gg").setAccountId(accountId))
            .build();
        return response;
    }

    @ProtoEndpoint(CreateAccount.class)
    public CreateAccountResponse createAccount(@RequestBody CreateAccountRequest request) {
        MessageLogger.log(request);
        return CreateAccountResponse.newBuilder().setAccount(request.getAccount().toBuilder().setAccountId(10000L)).build();
    }

    @ProtoEndpoint(UpdateAccount.class)
    public UpdateAccountResponse updateAccount(@PathVariable long accountId, @RequestBody UpdateAccountRequest request) {
        log.info("accountId: {}", accountId);
        MessageLogger.log(request);
        return UpdateAccountResponse.newBuilder().setAccount(request.getAccount().toBuilder().setAccountId(accountId)).build();
    }

    @ProtoEndpoint(DeleteAccount.class)
    public DeleteAccountResponse deleteAccount(@PathVariable long accountId) {
        log.info("accountId: {}", accountId);
        DeleteAccountResponse response = DeleteAccountResponse.newBuilder().build();
        return response;
    }
}
