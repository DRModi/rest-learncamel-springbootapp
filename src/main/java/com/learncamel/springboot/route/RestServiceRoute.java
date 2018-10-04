package com.learncamel.springboot.route;

import com.learncamel.springboot.exception.DataException;
import com.learncamel.springboot.processor.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.postgresql.util.PSQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
@Slf4j
public class RestServiceRoute extends RouteBuilder {


    @Autowired
    Environment environment;

    @Qualifier("myDataSource")
    @Autowired
    DataSource myDataSource;

    @Autowired
    BuildSQLProcessor buildSQLProcessor;

    @Autowired
    SuccessProcessor successProcessor;

    @Autowired
    MailProcessor mailProcessor;

    @Autowired
    SelectCountryProcessor selectCountryProcessor;


    @Override
    public void configure() throws Exception {

        //no need to create LoggerFactory b'coz we are using @Sl4j lombok (see additional info)
        log.info("Before Route Starting - CAMEL ROUTE");


        //this will be raised in DB connectivity issue - so retry make sense
        onException(PSQLException.class).log(LoggingLevel.ERROR, "PSQL Exception in the route and Body is ${body}")
                .maximumRedeliveries(3).redeliveryDelay(3000).backOffMultiplier(2).retryAttemptedLogLevel(LoggingLevel.ERROR);

        //Basically no need of retry since it is data issue and in this case it is going to be same exception in all retry.
        onException(DataException.class).log(LoggingLevel.ERROR, "DataException in Route and Body is ${body}")
                .process(mailProcessor);


        //Using predicates and content based routing - achieving the mock testing
        from("{{fromRoute}}").routeId("mainRouteId")
                .process(selectCountryProcessor)
                .setHeader(Exchange.HTTP_METHOD, constant("GET"))
                .setHeader(Exchange.HTTP_URI,simple("https://restcountries.eu/rest/v2/alpha/${header.countryAlpha2Code}"))
                .to("https://restcountries.eu/rest/v2/alpha/us").convertBodyTo(String.class)
                .log("The REST COUNTRIES api response is : ${body} ")
                .removeHeader(Exchange.HTTP_URI) //to remove previously set URI value which rest service
                .setHeader(Exchange.HTTP_METHOD, constant("POST"))
                .to("{{toRoute}}");


        log.info("After Route Completing - CAMEL ROUTE");
    }
}
