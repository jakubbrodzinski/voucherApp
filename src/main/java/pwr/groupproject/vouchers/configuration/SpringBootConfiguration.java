package pwr.groupproject.vouchers.configuration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@Configuration
@ComponentScan(basePackages = {"pwr.groupproject.vouchers.controller","pwr.groupproject.vouchers.configuration"})
public class SpringBootConfiguration {

    public static void main(String[] args){
        SpringApplication.run(SpringBootConfiguration.class);
    }
}
