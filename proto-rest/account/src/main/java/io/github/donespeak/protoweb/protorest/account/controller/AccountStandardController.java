package io.github.donespeak.protoweb.protorest.account.controller;
import io.github.donespeak.protoweb.protorest.account.proto.rest.api.CreateAccountRequest;
import io.github.donespeak.protoweb.protorest.account.proto.rest.api.CreateAccountResponse;
import io.github.donespeak.protoweb.protorest.account.proto.rest.api.DeleteAccountResponse;
import io.github.donespeak.protoweb.protorest.account.proto.rest.api.GetAccountRequest;
import io.github.donespeak.protoweb.protorest.account.proto.rest.api.GetAccountResponse;
import io.github.donespeak.protoweb.protorest.account.proto.rest.api.ListAccountRequest;
import io.github.donespeak.protoweb.protorest.account.proto.rest.api.ListAccountResponse;
import io.github.donespeak.protoweb.protorest.account.proto.rest.api.UpdateAccountRequest;
import io.github.donespeak.protoweb.protorest.account.proto.rest.api.UpdateAccountResponse;
import io.github.donespeak.protoweb.protorest.account.proto.rest.data.Account;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Yang Guanrong
 */
@Slf4j
@RestController
@RequestMapping("/std/account")
public class AccountStandardController {

    @GetMapping(
        path = "account",
        consumes = {"application/x-protobuf", "application/x-protobuf;charset=UTF-8"},
        produces = {"application/x-protobuf", "application/x-protobuf;charset=UTF-8"})
    public ListAccountResponse listAccount(ListAccountRequest.Builder request) {
        MessageLogger.log(request);
        ListAccountResponse response = ListAccountResponse.newBuilder()
            .addAccounts(Account.newBuilder().setFirstName("First name"))
            .build();
        return response;
    }

    @GetMapping(
        path = "account/{accountId}",
        consumes = {"application/x-protobuf", "application/x-protobuf;charset=UTF-8"},
        produces = {"application/x-protobuf", "application/x-protobuf;charset=UTF-8"})
    public GetAccountResponse getAccount(@PathVariable long accountId, GetAccountRequest.Builder request) {
        log.info("accountId: {}", accountId);
        MessageLogger.log(request);
        GetAccountResponse response = GetAccountResponse.newBuilder()
            .setAccount(Account.newBuilder().setEmail("gg@gg.com").setFirstName("Gg").setAccountId(accountId))
            .build();
        return response;
    }

    @PostMapping(
        path = "account",
        consumes = {"application/x-protobuf", "application/x-protobuf;charset=UTF-8"},
        produces = {"application/x-protobuf", "application/x-protobuf;charset=UTF-8"})
    public CreateAccountResponse createAccount(@RequestBody CreateAccountRequest request) {
        MessageLogger.log(request);
        return CreateAccountResponse.newBuilder().setAccount(request.getAccount().toBuilder().setAccountId(10000L)).build();
    }

    @PutMapping(
        path = "account/{accountId}",
        consumes = {"application/x-protobuf", "application/x-protobuf;charset=UTF-8"},
        produces = {"application/x-protobuf", "application/x-protobuf;charset=UTF-8"})
    public UpdateAccountResponse updateAccount(@PathVariable long accountId, @RequestBody UpdateAccountRequest request) {
        log.info("accountId: {}", accountId);
        MessageLogger.log(request);
        return UpdateAccountResponse.newBuilder().setAccount(request.getAccount().toBuilder().setAccountId(accountId)).build();
    }

    @DeleteMapping(
        path = "account/{accountId}",
        consumes = {"application/x-protobuf", "application/x-protobuf;charset=UTF-8"},
        produces = {"application/x-protobuf", "application/x-protobuf;charset=UTF-8"})
    public DeleteAccountResponse updateAccount(@PathVariable long accountId) {
        log.info("accountId: {}", accountId);
        return DeleteAccountResponse.newBuilder().build();
    }
}
