package io.rently.mailerservice.services;

import io.rently.mailerservice.errors.Errors;
import io.rently.mailerservice.interfaces.IMailer;
import io.rently.mailerservice.mailer.templates.*;
import io.rently.mailerservice.mailer.templates.interfaces.ITemplate;
import io.rently.mailerservice.utils.Broadcaster;
import io.rently.mailerservice.utils.Fields;
import org.springframework.beans.factory.annotation.Autowired;
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
        Notification notification = new Notification(subject, body);
        Broadcaster.info("Sending generic notification to " + email);
        trySendingEmail(email, subject, notification);
    }

    public void sendGreetings(Map<String, Object> data) {
        String name = Fields.tryGet("name", data);
        String email = Fields.tryGet("email", data);
        Welcome welcome = new Welcome(name);
        Broadcaster.info("Sending greetings to " + email);
        trySendingEmail(email, "Nice to meet you, " + name, welcome);
    }

    public void sendNewListingNotification(Map<String, Object> data) {
        String email = Fields.tryGet("email", data);
        String link = Fields.tryGet("link", data);
        String image = Fields.tryGet("image", data);
        String title = Fields.tryGet("title", data);
        Broadcaster.info("Sending new listing prompt to " + email);
        String description = Fields.tryGetElipsed("description", data, 100);
        NewListing newListing = new NewListing(title, image, description, link);
        trySendingEmail(email, "Listing online!", newListing);
    }

    public void sendAccountDeletionNotification(Map<String, Object> data) {
        String email = Fields.tryGet("email", data);
        String name = Fields.tryGet("name", data);
        Goodbye goodbyes = new Goodbye(name);
        Broadcaster.info("Sending goodbyes to " + email);
        trySendingEmail(email, "Account remove from Rently", goodbyes);
    }

    public void sendListingDeletionNotification(Map<String, Object> data) {
        String email = Fields.tryGet("email", data);
        String title = Fields.tryGet("title", data);
        String description = Fields.tryGetElipsed("description", data, 100);
        ListingDeletion listingDeletion = new ListingDeletion(title, description);
        Broadcaster.info("Sending listing deletion prompt to " + email);
        trySendingEmail(email, "Listing removed!", listingDeletion);
    }

    public void sendUpdateListingNotification(Map<String, Object> data) {
        String email = Fields.tryGet("email", data);
        String link = Fields.tryGet("link", data);
        String image = Fields.tryGet("image", data);
        String title = Fields.tryGet("title", data);
        String description = Fields.tryGetElipsed("description", data, 100);
        UpdatedListing updatedListing = new UpdatedListing(link, image, title, description);
        Broadcaster.info("Sending updated listing prompt to " + email);
        trySendingEmail(email, "Listing updated!", updatedListing);
    }

    public void trySendingEmail(String email, String subject, ITemplate template) {
        try {
            mailer.sendEmail(email, subject, template.getTemplate());
        } catch(Exception ex) {
            throw Errors.INVALID_EMAIL_ADDRESS;
        }
    }
}
