package com.learncamel.springboot.processor;

import com.learncamel.springboot.domain.Country;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class BuildSQLProcessor implements Processor {

    @Override
    public void process(Exchange exchange) throws Exception {

        log.info("**** Build SQL Processor : START ");

        if(exchange!=null){

            Country country = (Country) exchange.getIn().getBody();

            /*COUNTRY_NAME TEXT,
            COUNTRY_CAPITAL TEXT,
            COUNTRY_ALPHA3CODE TEXT,
            COUNTRY_POPULATION NUMERIC,*/

            StringBuilder sqlQuery = new StringBuilder();
            sqlQuery.append("INSERT INTO COUNTRY (COUNTRY_NAME, COUNTRY_CAPITAL, COUNTRY_ALPHA3CODE, COUNTRY_POPULATION) VALUES ('");
            sqlQuery.append(country.getName()+"','"+country.getCapital()+"','"+country.getAlpha3Code()+"',"+country.getPopulation()+")");


            log.info("**** Build SQL Processor : Constructed SQL Query is :" + sqlQuery.toString());

            exchange.getIn().setBody(sqlQuery.toString());
            exchange.getIn().setHeader("countryIDFromHeader", country.getAlpha3Code());

        }

        log.info("**** Build SQL Processor : EXIT ");
    }
}
