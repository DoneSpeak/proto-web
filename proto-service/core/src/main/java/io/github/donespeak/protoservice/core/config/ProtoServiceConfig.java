package io.github.donespeak.protoservice.core.config;

import io.github.donespeak.protoservice.core.EnableProtoService;
import io.github.donespeak.protoservice.core.support.ProtoRequestMappingHandlerMapping;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcRegistrations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.protobuf.ProtobufHttpMessageConverter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

/**
 * @author Yang Guanrong
 */
@Configuration
@ComponentScan(basePackageClasses = EnableProtoService.class)
public class ProtoServiceConfig {

    @Configuration
    public class ProtoWebMvcRegistrations implements WebMvcRegistrations {

        @Override
        public RequestMappingHandlerMapping getRequestMappingHandlerMapping() {
            return new ProtoRequestMappingHandlerMapping();
        }

        @Bean
        public ProtobufHttpMessageConverter protobufHttpMessageConverter() {
            // 使用 protocol buffer 做数据交换协议
            return new ProtobufHttpMessageConverter();
        }
    }
}
