package io.rently.mailerservice.services;

import io.rently.mailerservice.errors.Errors;
import io.rently.mailerservice.interfaces.IMailer;
import io.rently.mailerservice.mailer.Mailer;
import io.rently.mailerservice.mailer.templates.*;
import io.rently.mailerservice.utils.Broadcaster;
import io.rently.mailerservice.utils.Fields;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class MailerService {

    @Autowired
    private IMailer mailer;

    public void sendNotification(Map<String, Object> data) {
        String subject = Fields.tryGet("subject", data);
        String body = Fields.tryGet("body", data);
        String email = Fields.tryGet("email", data);

        Broadcaster.info("Sending generic notification to " + email);

        try {
            mailer.sendEmail(email, subject, Notification.getTemplate(subject, body));
        } catch(Exception ex) {
            throw Errors.INVALID_EMAIL_ADDRESS;
        }
    }

    public void sendGreetings(Map<String, Object> data) {
        String name = Fields.tryGet("name", data);
        String email = Fields.tryGet("email", data);

        Broadcaster.info("Sending greetings to " + email);

        try {
            mailer.sendEmail(email, "Nice to meet you, " + name, Welcome.getTemplate(name));
        } catch(Exception ex) {
            throw Errors.INVALID_EMAIL_ADDRESS;
        }
    }

    public void sendNewListingNotification(Map<String, Object> data) {
        String email = Fields.tryGet("email", data);
        String link = Fields.tryGet("link", data);
        String image = Fields.tryGet("image", data);
        String title = Fields.tryGet("title", data);
        String description = Fields.tryGetElipsed("description", data, 100);

        Broadcaster.info("Sending new listing prompt to " + email);

        try {
            mailer.sendEmail(email, "Listing online!", NewListing.getTemplate(link, image, title, description));
        } catch(Exception ex) {
            throw Errors.INVALID_EMAIL_ADDRESS;
        }
    }

    public void sendAccountDeletionNotification(Map<String, Object> data) {
        String email = Fields.tryGet("email", data);
        String name = Fields.tryGet("name", data);

        Broadcaster.info("Sending goodbyes to " + email);

        try {
            mailer.sendEmail(email, "Account remove from Rently", Goodbye.getTemplate(name));
        } catch(Exception ex) {
            throw Errors.INVALID_EMAIL_ADDRESS;
        }
    }

    public void sendListingDeletionNotification(Map<String, Object> data) {
        String email = Fields.tryGet("email", data);
        String title = Fields.tryGet("title", data);
        String description = Fields.tryGetElipsed("description", data, 100);

        Broadcaster.info("Sending listing deletion prompt to " + email);

        try {
            mailer.sendEmail(email, "Listing removed", ListingDeletion.getTemplate(title, description));
        } catch(Exception ex) {
            throw Errors.INVALID_EMAIL_ADDRESS;
        }
    }

    public void sendUpdateListingNotification(Map<String, Object> data) {
        String email = Fields.tryGet("email", data);
        String link = Fields.tryGet("link", data);
        String image = Fields.tryGet("image", data);
        String title = Fields.tryGet("title", data);
        String description = Fields.tryGetElipsed("description", data, 100);

        Broadcaster.info("Sending updated listing prompt to " + email);

        try {
            mailer.sendEmail(email, "Listing updated!", UpdatedListing.getTemplate(link, image, title, description));
        } catch(Exception ex) {
            throw Errors.INVALID_EMAIL_ADDRESS;
        }
    }
}
