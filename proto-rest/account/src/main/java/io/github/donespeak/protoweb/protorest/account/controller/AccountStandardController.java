package io.github.donespeak.protoweb.protorest.account.controller;
import io.github.donespeak.protoweb.protorest.account.proto.rest.api.ListAccountRequest;
import io.github.donespeak.protoweb.protorest.account.proto.rest.api.ListAccountResponse;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Yang Guanrong
 */
@RestController
@RequestMapping("/std/account")
public class AccountStandardController {

    @PostMapping(
        path = "account",
        consumes = {"application/x-protobuf", "application/x-protobuf;charset=UTF-8"},
        produces = {"application/x-protobuf", "application/x-protobuf;charset=UTF-8"})
    public ListAccountResponse listAccount(@PathVariable long accountId, @RequestBody ListAccountRequest request) {
        ListAccountResponse response = ListAccountResponse.newBuilder().build();
        return response;
    }
}
