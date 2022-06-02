package io.rently.mailerservice.mailer;

import io.rently.mailerservice.configs.BugsnagTestConfigs;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ContextConfiguration;

import javax.mail.MessagingException;
import java.util.Properties;

@ContextConfiguration(classes = BugsnagTestConfigs.class)
class MailerTest {

    public Mailer mailer;

    @BeforeEach
    public void setup() {
        Properties properties = System.getProperties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", 465);
        properties.put("mail.smtp.ssl.enable", true);
        properties.put("mail.smtp.auth", true);

        this.mailer = new Mailer(
                "info.rently.io@gmail.com",
                "info.rently.io@gmail.com",
                "ncsyuuohohavmgss",
                properties);
    }

    @Test
    void sendEmail() throws MessagingException {
        String recipient = "greffchandler80@gmail.com";
        String subject = "This is a subject";
        String content = "This is some content";

        mailer.sendEmail(recipient, subject, content);
    }
}