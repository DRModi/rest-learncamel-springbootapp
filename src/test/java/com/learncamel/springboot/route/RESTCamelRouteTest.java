package com.learncamel.springboot.route;

import org.apache.camel.CamelContext;
import org.apache.camel.CamelExecutionException;
import org.apache.camel.ConsumerTemplate;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.apache.camel.test.spring.CamelSpringBootRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;

@ActiveProfiles("dev")
@RunWith(CamelSpringBootRunner.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class RESTCamelRouteTest extends CamelTestSupport {

    @Autowired
    private CamelContext camelContext;

    @Autowired
    protected CamelContext createCamelContext(){
        return  camelContext;
    }

    @Override
    public RouteBuilder createRouteBuilder(){
        return new CountryRestRoute();
    }

    @Autowired
    private ProducerTemplate producerTemplate;

    @Autowired
    private ConsumerTemplate consumerTemplate;

    @Autowired
    Environment environment;

    @Before
    public void setUp() {

    }

    @Test
    public void REST_COUNTRY_RouteTest(){

        String input = "{\"name\":\"India\",\"topLevelDomain\":[\".in\"],\"alpha2Code\":\"IN\",\"alpha3Code\":\"IND\",\"callingCodes\":[\"91\"]," +
                "\"capital\":\"New Delhi\",\"altSpellings\":[\"IN\",\"Bhārat\",\"Republic of India\",\"Bharat Ganrajya\"],\"region\":\"Asia\"," +
                "\"subregion\":\"Southern Asia\",\"population\":1295210000,\"latlng\":[20.0,77.0],\"demonym\":\"Indian\",\"area\":3287590.0," +
                "\"gini\":33.4,\"timezones\":[\"UTC+05:30\"],\"borders\":[\"AFG\",\"BGD\",\"BTN\",\"MMR\",\"CHN\",\"NPL\",\"PAK\",\"LKA\"]," +
                "\"nativeName\":\"भारत\",\"numericCode\":\"356\",\"currencies\":[{\"code\":\"INR\",\"name\":\"Indian rupee\",\"symbol\":\"₹\"}]," +
                "\"languages\":[{\"iso639_1\":\"hi\",\"iso639_2\":\"hin\",\"name\":\"Hindi\",\"nativeName\":\"हिन्दी\"}," +
                "{\"iso639_1\":\"en\",\"iso639_2\":\"eng\",\"name\":\"English\",\"nativeName\":\"English\"}],\"translations\":" +
                "{\"de\":\"Indien\",\"es\":\"India\",\"fr\":\"Inde\",\"ja\":\"インド\",\"it\":\"India\",\"br\":\"Índia\",\"pt\":\"Índia\"," +
                "\"nl\":\"India\",\"hr\":\"Indija\",\"fa\":\"هند\"},\"flag\":\"https://restcountries.eu/data/ind.svg\"," +
                "\"regionalBlocs\":[{\"acronym\":\"SAARC\",\"name\":\"South Asian Association for Regional Cooperation\",\"otherAcronyms\":[],\"otherNames\":[]}]," +
                "\"cioc\":\"IND\"}";

        String countryList = producerTemplate.requestBody("http://localhost:8081/country",input,String.class);

        System.out.println("*** Country List : "+countryList);

        assertNotNull(countryList);
    }

}
