package io.github.donespeak.protoservice.account.endpoint;

import io.github.donespeak.protoservice.account.proto.api.ListAccountRequest;
import io.github.donespeak.protoservice.account.proto.api.ListAccountResponse;
import io.github.donespeak.protoservice.core.annotation.ProtoMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Yang Guanrong
 */
@RestController
@RequestMapping("account")
public class AccountController {

    @ProtoMapping("ListAccount")
    public ListAccountResponse listAccount(ListAccountRequest request) {
        // do something
        ListAccountResponse response = ListAccountResponse.newBuilder().build();
        return response;
    }

    @GetMapping("")
    public String get() {
        return "dfdf";
    }
}
