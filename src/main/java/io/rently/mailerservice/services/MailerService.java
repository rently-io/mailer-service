package io.rently.mailerservice.services;

import io.rently.mailerservice.errors.Errors;
import io.rently.mailerservice.mailer.Mailer;
import io.rently.mailerservice.mailer.templates.*;
import io.rently.mailerservice.utils.Broadcaster;
import io.rently.mailerservice.utils.Properties;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.List;

@Service
public class MailerService {
    public static final Mailer mailer = new Mailer.Builder("info.rently.io@gmail.com").credentials("info.rently.io@gmail.com", "ncsyuuohohavmgss").host("smtp.gmail.com").build();

    public static void sendNotification(JSONObject data) {
        String subject = data.getString("subject");
        String body = data.getString("body");
        String email = data.getString("email");
        Broadcaster.info("Sending generic notification to " + email);
        try {
            mailer.sendMailTo(email, subject, Notification.getTemplate(subject, body));
        } catch(MessagingException ex) {
            throw Errors.INVALID_EMAIL_ADDRESS;
        }
    }

    public static void sendGreetings(JSONObject data) {
        String name = data.getString("name");
        String email = data.getString("email");
        Broadcaster.info("Sending greetings to " + email);
        try {
            mailer.sendMailTo(email, "Nice to meet you, " + name, Welcome.getTemplate(name));
        } catch(MessagingException ex) {
            throw Errors.INVALID_EMAIL_ADDRESS;
        }
    }

    public static void sendNewListingNotification(JSONObject data) {
        String email = data.getString("email");
        String link = data.getString("link");
        String image = data.getString("image");
        String title = data.getString("title");
        String description = data.getString("description");
        description = description.substring(0, 100).trim() + "...";
        Broadcaster.info("Sending new listing prompt to " + email);
        try {
            mailer.sendMailTo(email, "Listing online!", NewListing.getTemplate(link, image, title, description));
        } catch(MessagingException ex) {
            throw Errors.INVALID_EMAIL_ADDRESS;
        }
    }

    public static void sendAccountDeletionNotification(JSONObject data) {
        String email = data.getString("email");
        String name = data.getString("name");
        Broadcaster.info("Sending goodbyes to " + email);
        try {
            mailer.sendMailTo(email, "Account remove from Rently", Goodbye.getTemplate(name));
        } catch(MessagingException ex) {
            throw Errors.INVALID_EMAIL_ADDRESS;
        }
    }

    public static void sendErrorToDev(JSONObject data) {
        List<Object> emails = data.getJSONArray("emails").toList();
        String datetime = Properties.tryGetOptional("datetime", data, "Not specified");;
        String service = Properties.tryGetOptional("service", data, "Unknown source");
        String message = Properties.tryGetOptional("message", data, "No message");
        String cause = Properties.tryGetOptional("cause", data, "No cause");
        String trace = Properties.tryGetOptional("trace", data, "No trace");
        String exceptionType = Properties.tryGetOptional("exceptionType", data, "Unknown");;
        Broadcaster.info("Dispatching error to " + emails.size() + " dev(s)");
        for (Object email : emails) {
            try {
                mailer.sendMailTo(email.toString(), "[ERROR] " + service, DevError.getTemplate(service, message, cause, trace, exceptionType, emails, datetime));
            } catch(MessagingException ex) {
                Broadcaster.warn("Could not notify email: " + email);
            }
        }
    }
}
