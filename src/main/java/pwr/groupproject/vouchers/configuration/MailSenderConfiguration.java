package pwr.groupproject.vouchers.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
@PropertySource("classpath:/properties/mailsender.properties")
public class MailSenderConfiguration {
    @Autowired
    private Environment env;
/*
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=pwrvoucher.email1@gmail.com
spring.mail.password=zaq1@WSX3
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
spring.mail.debug = true
spring.mail.transport.protocol = smtp
 */
    @Bean
    public JavaMailSender mailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(env.getProperty("spring.mail.host"));
        mailSender.setPort(Integer.parseInt(env.getProperty("spring.mail.port")));

        mailSender.setUsername(env.getProperty("spring.mail.username"));
        mailSender.setPassword(env.getProperty("spring.mail.password"));

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", env.getProperty("spring.mail.transport.protocol"));
        props.put("mail.smtp.auth", env.getProperty("spring.mail.properties.mail.smtp.auth"));
        props.put("mail.smtp.starttls.enable", env.getProperty("spring.mail.properties.mail.smtp.starttls.enable"));
        props.put("mail.debug", env.getProperty("spring.mail.debug"));
        return mailSender;
    }
}
