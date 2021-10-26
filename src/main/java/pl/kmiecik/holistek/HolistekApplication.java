package pl.kmiecik.holistek;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class HolistekApplication {

    public static void main(String[] args) {
        SpringApplication.run(HolistekApplication.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void getProp(){
        System.out.println("hasło "+propPass);
        System.out.println("login "+propLogin);
    }

    @Value("${proxyUser}")
    private String propLogin;

    @Value("${proxyPassword}")
    private String propPass;
}
