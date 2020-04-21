package io.github.donespeak.protoservice.account.endpoint;

import io.github.donespeak.protoservice.account.proto.AccountApi;
import io.github.donespeak.protoservice.account.proto.api.ListAccount;
import io.github.donespeak.protoservice.account.proto.api.ListAccountRequest;
import io.github.donespeak.protoservice.account.proto.api.ListAccountResponse;
import io.github.donespeak.protoservice.account.proto.data.Account;
import io.github.donespeak.protoservice.core.annotation.ProtoApi;
import io.github.donespeak.protoservice.core.annotation.ProtoEndpoint;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author Yang Guanrong
 */
@ProtoApi(AccountApi.class)
public class AccountCustomizedController {

    // POST /AccountApi/ListAccount
    @ProtoEndpoint(ListAccount.class)
    public ListAccountResponse listAccount(@RequestBody ListAccountRequest request) {
        // do something
        ListAccountResponse response = ListAccountResponse.newBuilder()
            .addAccounts(Account.newBuilder().setEmail("gr@gr.com").build())
            .setPage(request.getPage())
            .build();
        return response;
    }
}
