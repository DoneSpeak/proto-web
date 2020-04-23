package io.github.donespeak.protoweb.protorest.account;

import io.github.donespeak.protoweb.protorest.core.EnableProtoRest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.protobuf.ProtobufHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@Slf4j
@EnableProtoRest
@SpringBootApplication
public class ProtoRestAccountApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProtoRestAccountApplication.class, args);
	}
}
