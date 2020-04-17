package io.github.donespeak.protoservice.account;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.Map;

/**
 * @author Yang Guanrong
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class RequestMappingHandlerMappingTest {

    @Autowired
    private RequestMappingHandlerMapping requestMappingHandlerMapping;

    @Test
    public void printAllEndPoints() {
        StringBuilder sb = new StringBuilder("\n\nrequestMappingHandlerMapping: " + requestMappingHandlerMapping.getClass().getName() + "\n");
        for(Map.Entry entry: requestMappingHandlerMapping.getHandlerMethods().entrySet()) {
            sb.append("\t" + entry.getKey() + ": \n\t\t" + entry.getValue() + "\n");
        }
        log.info(sb.toString());
    }
}
