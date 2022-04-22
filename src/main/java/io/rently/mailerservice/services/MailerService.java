package io.rently.mailerservice.services;

import io.rently.mailerservice.errors.Errors;
import io.rently.mailerservice.mailer.templates.NewListing;
import io.rently.mailerservice.mailer.templates.Welcome;
import io.rently.mailerservice.mailer.Mailer;
import io.rently.mailerservice.utils.Properties;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.Map;

@Service
public class MailerService {
    public static final Mailer mailer = new Mailer.Builder("info.rently.io@gmail.com").credentials("info.rently.io@gmail.com", "ncsyuuohohavmgss").host("smtp.gmail.com").build();

    public static void sendGreetings(Map<String, Object> data) {
        String name = Properties.tryGetProperty("name", data);
        String email = Properties.tryGetProperty("email", data);

        try {
            mailer.sendMailTo(email, "Nice to meet you, " + name, new Welcome(name).toString());
        } catch(MessagingException ex) {
            throw Errors.INVALID_EMAIL_ADDRESS;
        }
    }

    public static void sendNewListingNotification(Map<String, Object> data) {
        String email = Properties.tryGetProperty("email", data);
        String link = Properties.tryGetProperty("link", data);
        String image = Properties.tryGetProperty("image", data);
        String title = Properties.tryGetProperty("title", data);
        String description = Properties.tryGetProperty("description", data);

        try {
            mailer.sendMailTo(email, "Listing online!", new NewListing(link, image, title, description).toString());
        } catch(MessagingException ex) {
            throw Errors.INVALID_EMAIL_ADDRESS;
        }
    }
}
