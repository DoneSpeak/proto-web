package io.github.donespeak.protoservice.account.endpoint;

import io.github.donespeak.protoservice.account.proto.api.GetAccountRequest;
import io.github.donespeak.protoservice.account.proto.api.GetAccountResponse;
import io.github.donespeak.protoservice.account.proto.api.ListAccountRequest;
import io.github.donespeak.protoservice.account.proto.api.ListAccountResponse;
import io.github.donespeak.protoservice.core.annotation.ProtoMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Yang Guanrong
 */
@RestController
@RequestMapping("/std/AccountApi")
public class AccountStandardController {

    @RequestMapping(
        path = "ListAccount",
        method = RequestMethod.POST,
        consumes = {"application/x-protobuf", "application/x-protobuf;charset=UTF-8"},
        produces = {"application/x-protobuf", "application/x-protobuf;charset=UTF-8"})
    public ListAccountResponse listAccount(ListAccountRequest request) {
        // do something
        ListAccountResponse response = ListAccountResponse.newBuilder().build();
        return response;
    }

    @PostMapping("GetAccount")
    public GetAccountResponse listAccount(GetAccountRequest request) {
        // do something
        GetAccountResponse response = GetAccountResponse.newBuilder().build();
        return response;
    }
}
