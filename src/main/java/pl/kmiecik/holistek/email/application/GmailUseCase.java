package pl.kmiecik.holistek.email.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import pl.kmiecik.holistek.email.application.port.GmailService;


/**
 * https://www.baeldung.com/spring-email
 */

@Service
public class GmailUseCase implements GmailService {

    private final JavaMailSender emailSender;


    @Autowired
    public GmailUseCase(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    @Value("${fixture-service-usecase.emailFrom}")
    private String fromEmail;
    @Override
    public void sendSimpleMessage(final String to, final String subject, final String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);
    }


}
