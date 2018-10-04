package com.learncamel.springboot.domain;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@Data
public class Country {

    private String name;
    private String capital;
    private String alpha3Code;
    private String population;

}
