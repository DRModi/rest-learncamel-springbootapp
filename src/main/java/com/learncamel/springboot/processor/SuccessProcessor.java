package com.learncamel.springboot.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

@Component
public class
SuccessProcessor implements Processor {
    @Override
    public void process(Exchange exchange) throws Exception {

        exchange.getIn().setBody("Data update successfully!");
    }
}
