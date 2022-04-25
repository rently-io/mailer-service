package io.rently.mailerservice.services;

import io.rently.mailerservice.errors.Errors;
import io.rently.mailerservice.interfaces.IMessenger;
import io.rently.mailerservice.mailer.Mailer;
import io.rently.mailerservice.mailer.templates.*;
import io.rently.mailerservice.utils.Broadcaster;
import io.rently.mailerservice.utils.Properties;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MailerService {
    private final IMessenger mailer;

    public MailerService(
            @Value("${mailer.password}") String password,
            @Value("${mailer.email}") String email,
            @Value("${mailer.host}") String host
    ) {
        this.mailer = new Mailer.Builder(email).credentials(email, password).host(host).build();
    }

    public void sendNotification(JSONObject data) {
        String subject = data.getString("subject");
        String body = data.getString("body");
        String email = data.getString("email");
        Broadcaster.info("Sending generic notification to " + email);
        try {
            mailer.sendEmail(email, subject, Notification.getTemplate(subject, body));
        } catch(Exception ex) {
            throw Errors.INVALID_EMAIL_ADDRESS;
        }
    }

    public void sendGreetings(JSONObject data) {
        String name = data.getString("name");
        String email = data.getString("email");
        Broadcaster.info("Sending greetings to " + email);
        try {
            mailer.sendEmail(email, "Nice to meet you, " + name, Welcome.getTemplate(name));
        } catch(Exception ex) {
            throw Errors.INVALID_EMAIL_ADDRESS;
        }
    }

    public void sendNewListingNotification(JSONObject data) {
        String email = data.getString("email");
        String link = data.getString("link");
        String image = data.getString("image");
        String title = data.getString("title");
        String description = data.getString("description");
        if (description.length() > 100) {
            description = description.substring(0, 100).trim() + "...";
        }
        Broadcaster.info("Sending new listing prompt to " + email);
        try {
            mailer.sendEmail(email, "Listing online!", NewListing.getTemplate(link, image, title, description));
        } catch(Exception ex) {
            throw Errors.INVALID_EMAIL_ADDRESS;
        }
    }

    public void sendAccountDeletionNotification(JSONObject data) {
        String email = data.getString("email");
        String name = data.getString("name");
        Broadcaster.info("Sending goodbyes to " + email);
        try {
            mailer.sendEmail(email, "Account remove from Rently", Goodbye.getTemplate(name));
        } catch(Exception ex) {
            throw Errors.INVALID_EMAIL_ADDRESS;
        }
    }

    public void sendListingDeletionNotification(JSONObject data) {
        String email = data.getString("email");
        String title = data.getString("title");
        String description = data.getString("description");
        if (description.length() > 100) {
            description = description.substring(0, 100).trim() + "...";
        }
        Broadcaster.info("Sending listing deletion prompt to " + email);
        try {
            mailer.sendEmail(email, "Listing removed", ListingDeletion.getTemplate(title, description));
        } catch(Exception ex) {
            throw Errors.INVALID_EMAIL_ADDRESS;
        }
    }
}
