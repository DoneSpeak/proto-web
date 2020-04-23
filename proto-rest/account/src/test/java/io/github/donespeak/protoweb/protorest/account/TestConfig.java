package io.github.donespeak.protoweb.protorest.account;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.converter.protobuf.ProtobufHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;

/**
 * @author Yang Guanrong
 */
@Slf4j
@TestConfiguration
public class TestConfig {

    @Autowired
    private TestRestTemplate testRestTemplate;

    // @Bean
    // public RestTemplate restTemplate(ProtobufHttpMessageConverter hmc) {
    //     log.info("ProtobufHttpMessageConverter: {}", hmc);
    //     RestTemplate restTemplate = new RestTemplate(Arrays.asList(hmc));
    //     restTemplate.setInterceptors(Collections.singletonList(protobufHeanderInterceptor()));
    //     return restTemplate;
    // }

    @PostConstruct
    public void postConstruct() {
        RestTemplate restTemplate = testRestTemplate.getRestTemplate();
        if(restTemplate.getInterceptors() == null) {
            restTemplate.setInterceptors(Collections.singletonList(protobufHeanderInterceptor()));
        } else {
            restTemplate.getInterceptors().add(protobufHeanderInterceptor());
        }
    }

    public ClientHttpRequestInterceptor protobufHeanderInterceptor() {
        return (request, body, execution) -> {
            HttpHeaders headers = request.getHeaders();
            headers.add("Content-Type", "application/x-protobuf;charset=UTF-8");
            return execution.execute(request, body);
        };
    }
}
