package pl.kmiecik.holistek.email.application.port;

public interface GmailService {
    void sendSimpleMessage(
            String to, String subject, String text);
}
