package io.github.donespeak.protoservice.account;

import io.github.donespeak.protoservice.core.EnableProtoService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableProtoService
@SpringBootApplication
public class AccountApp {
    public static void main(String[] args) {
        SpringApplication.run(AccountApp.class);
    }
}