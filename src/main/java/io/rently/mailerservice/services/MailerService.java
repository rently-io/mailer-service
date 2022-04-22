package io.rently.mailerservice.services;

import io.rently.mailerservice.errors.Errors;
import io.rently.mailerservice.mailer.templates.NewListing;
import io.rently.mailerservice.mailer.templates.Welcome;
import io.rently.mailerservice.utils.Broadcaster;
import io.rently.mailerservice.utils.Mailer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.mail.MessagingException;
import java.util.HashMap;
import java.util.Map;

@Service
public class MailerService {
    private static final Mailer mailer = new Mailer.Builder("info.rently.io@gmail.com").credentials("info.rently.io@gmail.com", "ncsyuuohohavmgss").host("smtp.gmail.com").build();

    public void handleSendGreetings(Map<String, Object> data) {
        String name = tryGetProperty("name" ,data);
        String email = tryGetProperty("email" ,data);

        try {
            mailer.sendMailTo(email, "Nice to meet you, " + name, new Welcome(name).toString());
        } catch(MessagingException ex) {
            throw Errors.INVALID_EMAIL_ADDRESS;
        }
    }

    public void handleSendNewListingPrompt(Map<String, Object> data) {
        String email = tryGetProperty("email" ,data);

//        try {
//            mailer.sendMailTo(email, "Listing online!", new NewListing(link, image, title, description).toString());
//        } catch(MessagingException ex) {
//            throw Errors.INVALID_EMAIL_ADDRESS;
//        }
    }

    public String tryGetProperty(String property, Map<String, Object> data) {
        try {
            return data.get(property).toString();
        } catch (Exception ignore) {
            throw new Errors.HttpBodyFieldMissing(property);
        }
    }
}
