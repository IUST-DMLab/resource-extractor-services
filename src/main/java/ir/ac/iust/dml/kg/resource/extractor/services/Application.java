package ir.ac.iust.dml.kg.resource.extractor.services;

import ir.ac.iust.dml.kg.resource.extractor.services.web.Jackson2ObjectMapperPrettier;
import ir.ac.iust.dml.kg.resource.extractor.services.web.filter.FilterRegistrationConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
//@ImportResource(value = {})
@EnableAutoConfiguration(exclude = {
        Jackson2ObjectMapperPrettier.class,
        FilterRegistrationConfiguration.class})
@ComponentScan
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
