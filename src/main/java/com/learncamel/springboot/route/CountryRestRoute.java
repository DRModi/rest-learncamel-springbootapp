package com.learncamel.springboot.route;

import com.learncamel.springboot.domain.Country;
import com.learncamel.springboot.processor.BuildSQLProcessor;
import com.learncamel.springboot.processor.MailProcessor;
import com.learncamel.springboot.processor.SelectCountryProcessor;
import com.learncamel.springboot.processor.SuccessProcessor;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.gson.GsonDataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
@Slf4j
public class CountryRestRoute extends RouteBuilder {

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


    GsonDataFormat countryGsonDataFormat = new GsonDataFormat(Country.class);

    @Override
    public void configure() throws Exception {



        from("restlet:http://localhost:8081/country?restletMethods=POST").routeId("countryPostRoute")
                .log("*** Retrieved Body is : ${body}")
                .convertBodyTo(String.class)
                .unmarshal(countryGsonDataFormat)
                .log("Un-marshaled Country Object is : ${body}")
                .process(buildSQLProcessor)
                .to("{{tojdbcRoute}}")
                .to("{{toSQLNode}}")
                .log("inserted Country is ${body}");
    }
}
