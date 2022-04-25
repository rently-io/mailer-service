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
    private static List<String> devs;
    private static String host;
    private static String email;
    private static String password;
    private static IMessenger mailer;

    @Value("${mailer.host}")
    public void setHost(String host) {
        MailerService.host = host;
        tryBuildMailer();
    }

    @Value("${mailer.email}")
    public void setEmail(String email) {
        MailerService.email = email;
        tryBuildMailer();
    }

    @Value("${mailer.password}")
    public void setPassword(String password) {
        MailerService.password = password;
        tryBuildMailer();
    }

    public void tryBuildMailer() {
        if (email != null && host != null && password != null) {
            mailer = new Mailer.Builder(email).credentials(email, password).host(host).build();
        }
    }

    @Value("#{'${first.responders}'.split(',')}")
    public void setDevs(List<String> devs) {
        MailerService.devs = devs;
    }

    public static void sendNotification(JSONObject data) {
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

    public static void sendGreetings(JSONObject data) {
        String name = data.getString("name");
        String email = data.getString("email");
        Broadcaster.info("Sending greetings to " + email);
        try {
            mailer.sendEmail(email, "Nice to meet you, " + name, Welcome.getTemplate(name));
        } catch(Exception ex) {
            throw Errors.INVALID_EMAIL_ADDRESS;
        }
    }

    public static void sendNewListingNotification(JSONObject data) {
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

    public static void sendAccountDeletionNotification(JSONObject data) {
        String email = data.getString("email");
        String name = data.getString("name");
        Broadcaster.info("Sending goodbyes to " + email);
        try {
            mailer.sendEmail(email, "Account remove from Rently", Goodbye.getTemplate(name));
        } catch(Exception ex) {
            throw Errors.INVALID_EMAIL_ADDRESS;
        }
    }

    public static void sendListingDeletionNotification(JSONObject data) {
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

    public static void sendErrorToDev(JSONObject data) {
        String datetime = Properties.tryGetOptional("datetime", data, "Not specified");
        String service = Properties.tryGetOptional("service", data, "Unknown source");
        String message = Properties.tryGetOptional("message", data, "No message");
        String cause = Properties.tryGetOptional("cause", data, "Not specified");
        String trace = Properties.tryGetOptional("trace", data, "No trace");
        String exceptionType = Properties.tryGetOptional("exceptionType", data, "Unknown");
        Broadcaster.info("Dispatching error to " + devs.size() + " dev(s) from " + service);
        for (String email : devs) {
            try {
                mailer.sendEmail(email, "[ERROR] " + service, DevError.getTemplate(service, message, cause, trace, exceptionType, devs, datetime));
            } catch(Exception ex) {
                Broadcaster.warn("Could not notify email: " + email);
            }
        }
    }
}
