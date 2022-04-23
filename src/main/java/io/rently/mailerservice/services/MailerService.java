package io.rently.mailerservice.services;

import io.rently.mailerservice.errors.Errors;
import io.rently.mailerservice.mailer.Mailer;
import io.rently.mailerservice.mailer.templates.*;
import io.rently.mailerservice.utils.Broadcaster;
import io.rently.mailerservice.utils.Properties;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.List;

@Service
public class MailerService {
    public final Mailer mailer;

    public MailerService(@Value("${mailer.host}") String host, @Value("${mailer.email}") String email, @Value("${mailer.password}") String password) {
        mailer = new Mailer.Builder(email).credentials(email, password).host(host).build();
    }

    public void sendNotification(JSONObject data) {
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

    public void sendGreetings(JSONObject data) {
        String name = data.getString("name");
        String email = data.getString("email");
        Broadcaster.info("Sending greetings to " + email);
        try {
            mailer.sendMailTo(email, "Nice to meet you, " + name, Welcome.getTemplate(name));
        } catch(MessagingException ex) {
            throw Errors.INVALID_EMAIL_ADDRESS;
        }
    }

    public void sendNewListingNotification(JSONObject data) {
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

    public void sendAccountDeletionNotification(JSONObject data) {
        String email = data.getString("email");
        String name = data.getString("name");
        Broadcaster.info("Sending goodbyes to " + email);
        try {
            mailer.sendMailTo(email, "Account remove from Rently", Goodbye.getTemplate(name));
        } catch(MessagingException ex) {
            throw Errors.INVALID_EMAIL_ADDRESS;
        }
    }

    public void sendErrorToDev(JSONObject data) {
        List<Object> emails = data.getJSONArray("emails").toList();
        String datetime = Properties.tryGetOptional("datetime", data, "Not specified");;
        String service = Properties.tryGetOptional("service", data, "Unknown source");
        String message = Properties.tryGetOptional("message", data, "No message");
        String cause = Properties.tryGetOptional("cause", data, "No cause");
        String trace = Properties.tryGetOptional("trace", data, "No trace");
        String exceptionType = Properties.tryGetOptional("exceptionType", data, "Unknown");;
        Broadcaster.info("Dispatching error to " + emails.size() + " dev(s) from " + service);
        for (Object email : emails) {
            try {
                mailer.sendMailTo(email.toString(), "[ERROR] " + service, DevError.getTemplate(service, message, cause, trace, exceptionType, emails, datetime));
            } catch(MessagingException ex) {
                Broadcaster.warn("Could not notify email: " + email);
            }
        }
    }
}
