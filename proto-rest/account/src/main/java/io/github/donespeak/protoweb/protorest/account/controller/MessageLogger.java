package io.github.donespeak.protoweb.protorest.account.controller;

import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.MessageOrBuilder;
import com.google.protobuf.util.JsonFormat;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Yang Guanrong
 * @date 2020/04/22 23:49
 */
@Slf4j
public class MessageLogger {

    public static void log(MessageOrBuilder messageOrBuilder) {
        if(messageOrBuilder == null) {
            log.info("null");
            return;
        }
        try {
            String requestStr = JsonFormat.printer().print(messageOrBuilder);
            log.info(requestStr);
        } catch (InvalidProtocolBufferException e) {
            log.error(e.getMessage(), e);
        }
    }
}
