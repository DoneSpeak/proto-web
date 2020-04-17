package io.github.donespeak.protoservice.core;

import io.github.donespeak.protoservice.core.config.ProtoServiceConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Yang Guanrong
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({ ProtoServiceConfig.class })
public @interface EnableProtoService {
}
