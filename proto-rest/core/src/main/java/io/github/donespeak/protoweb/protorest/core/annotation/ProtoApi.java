package io.github.donespeak.protoweb.protorest.core.annotation;

import org.springframework.core.annotation.AliasFor;
import org.springframework.web.bind.annotation.RestController;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Yang Guanrong
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@RestController
public @interface ProtoApi {

    @AliasFor(annotation = RestController.class, attribute = "value")
    String name() default "";

    /**
     * api 定义类
     */
    Class<? extends com.google.protobuf.GeneratedMessageV3> value();
}