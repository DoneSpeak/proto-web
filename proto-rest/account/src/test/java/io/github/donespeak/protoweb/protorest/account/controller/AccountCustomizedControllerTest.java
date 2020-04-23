package io.github.donespeak.protoweb.protorest.account.controller;

import io.github.donespeak.protoweb.protorest.account.proto.rest.api.DeleteAccountRequest;
import io.github.donespeak.protoweb.protorest.account.proto.rest.api.DeleteAccountResponse;
import io.github.donespeak.protoweb.protorest.account.proto.rest.api.UpdateAccountRequest;
import io.github.donespeak.protoweb.protorest.account.proto.rest.api.UpdateAccountResponse;
import io.github.donespeak.protoweb.protorest.account.proto.rest.data.Account;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import io.github.donespeak.protoweb.protorest.account.ProtoRestAccountApplication;
import io.github.donespeak.protoweb.protorest.account.TestConfig;
import io.github.donespeak.protoweb.protorest.account.proto.rest.api.CreateAccountRequest;
import io.github.donespeak.protoweb.protorest.account.proto.rest.api.CreateAccountResponse;
import io.github.donespeak.protoweb.protorest.account.proto.rest.api.ListAccountResponse;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = ProtoRestAccountApplication.class)
@Import(TestConfig.class)
public class AccountCustomizedControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    // @Autowired
    // private RestTemplate restTemplate;

    @Test
    public void listAccount() {
        ResponseEntity<ListAccountResponse> response = restTemplate
            .getForEntity("/account/account?email=gr@gr.com&createdAtFrom=12345678", ListAccountResponse.class);

        MessageLogger.log(response.getBody());
    }

    @Test
    public void getAccount() {
        ResponseEntity<ListAccountResponse> response = restTemplate
            .getForEntity("/account/account?email=gr@gr.com&createdAtFrom=12345678", ListAccountResponse.class);

        MessageLogger.log(response.getBody());
    }

    @Test
    public void createAccount() {
        CreateAccountRequest request = CreateAccountRequest.newBuilder()
            .setAccount(Account.newBuilder().setEmail("gg@gt.com").setFirstName("Gg"))
            .build();
        ResponseEntity<CreateAccountResponse> response =
            restTemplate.postForEntity("/account/account", request, CreateAccountResponse.class);

        MessageLogger.log(response.getBody());
    }

    @Test
    public void updateAccount() {
        UpdateAccountRequest request = UpdateAccountRequest.newBuilder()
            .setAccount(Account.newBuilder().setEmail("gg@gt.com").setFirstName("Gg"))
            .build();
        HttpEntity<UpdateAccountRequest> httpEntity = new HttpEntity<>(request);
        ResponseEntity<UpdateAccountResponse> response =
            restTemplate.exchange("/account/account/10001", HttpMethod.PUT, httpEntity, UpdateAccountResponse.class);

        MessageLogger.log(response.getBody());
    }

    @Test
    public void deleteAccount() {
        DeleteAccountRequest request = DeleteAccountRequest.newBuilder().build();
        HttpEntity<UpdateAccountRequest> httpEntity = new HttpEntity(request);
        ResponseEntity<DeleteAccountResponse> response =
            restTemplate.exchange("/account/account/10001", HttpMethod.DELETE, httpEntity, DeleteAccountResponse.class);
        // DeleteAccountResponse 空消息体，response.getBody() 会返回null
        MessageLogger.log(response.getBody());
    }
}