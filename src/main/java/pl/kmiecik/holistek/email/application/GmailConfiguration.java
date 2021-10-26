package pl.kmiecik.holistek.email.application;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

/**
 * https://www.baeldung.com/spring-email
 */


@Configuration
class GmailConfiguration {

    @Value("${fixture-service-usecase.emailFrom}")
    private String emailFrom;

    @Value("${fixture-service-usecase.emailFromPass}")
    private String emailFromPass;

    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        /*
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);
        mailSender.setUsername("kk1975@gmail.com");
        mailSender.setPassword("");

        */


        mailSender.setHost("smtp.aptiv.com");
        mailSender.setPort(25);
        mailSender.setUsername(emailFrom);
        mailSender.setPassword(emailFromPass);

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");

        /* kontener dockerowy linagora/james-jpa-sample trzeba uruchomic
           zmienic c:/windows/system32/drivers/etc/hosts
        127.0.0.1	james.local
        127.0.0.1	eoltserverprod.local
         zmiana w notepadzie zeby zapisac plik bez rozszerzenia*/
/*
        mailSender.setHost("james.local");
        mailSender.setPort(25);*/

        return mailSender;
    }
}
