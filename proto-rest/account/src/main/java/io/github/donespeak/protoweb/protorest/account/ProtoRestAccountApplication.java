package io.github.donespeak.protoweb.protorest.account;

import io.github.donespeak.protoweb.protorest.core.EnableProtoRest;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableProtoRest
@SpringBootApplication
public class ProtoRestAccountApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProtoRestAccountApplication.class, args);
	}

}
