package pwr.groupproject.vouchers.configuration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pwr.groupproject.vouchers.dao.CompanySurveyDaoImpl;
import pwr.groupproject.vouchers.dao.VoucherDaoImpl;

import java.util.Locale;

@SpringBootApplication(scanBasePackages = {"pwr.groupproject.vouchers.configuration","pwr.groupproject.vouchers.dao","pwr.groupproject.vouchers.services","pwr.groupproject.vouchers.controller"})
public class SpringBootConfiguration {

    public static void main(String[] args){
        SpringApplication.run(SpringBootConfiguration.class);
    }
}
