package io.github.donespeak.protoweb.protorest.core;

import io.github.donespeak.protoweb.protorest.core.config.ProtoRestConfig;
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
@Import({ ProtoRestConfig.class })
public @interface EnableProtoRest {
}
