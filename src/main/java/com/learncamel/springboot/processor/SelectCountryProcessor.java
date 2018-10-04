package com.learncamel.springboot.processor;

import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Component
@Slf4j
public class SelectCountryProcessor implements Processor {

    List<String> countryAlpha2CodeList = Arrays.asList("in","us", "ch", "ca","mx", "au" );


    @Override
    public void process(Exchange exchange) throws Exception {

        log.info("Entered into : SelectCountryProcessor");

        Random randomSelection = new Random();

        String selectedCountryCode = countryAlpha2CodeList.get(randomSelection.nextInt(countryAlpha2CodeList.size()-1));
        log.info("Selected Country Code : "+selectedCountryCode);

        exchange.getIn().setHeader("countryAlpha2Code", selectedCountryCode);

        log.info("Exited into : SelectCountryProcessor");
    }
}
