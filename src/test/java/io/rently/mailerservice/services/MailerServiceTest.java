package io.rently.mailerservice.services;

import io.rently.mailerservice.configs.BugsnagTestConfigs;
import io.rently.mailerservice.configs.MailerServiceTestConfigs;
import io.rently.mailerservice.configs.ReporterServiceTestConfigs;
import io.rently.mailerservice.errors.Errors;
import io.rently.mailerservice.interfaces.IMailer;
import io.rently.mailerservice.mailer.templates.*;
import io.rently.mailerservice.utils.Fields;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = { MailerServiceTestConfigs.class, BugsnagTestConfigs.class })
@WebMvcTest(MailerService.class)
class MailerServiceTest {

    @Autowired
    public MailerService service;
    @Autowired
    public IMailer mailer;

    @BeforeEach
    public void setup() {
        reset(mailer);
    }

    @Test
    void sendNotification_mailerInvoked_void() throws Exception {
        String subject = "subject";
        String body = "body";
        String email = "email";
        Map<String, Object> data = new HashMap<>();
        data.put("subject", subject);
        data.put("body", body);
        data.put("email", email);
        Notification notification = new Notification(subject, body);

        service.sendNotification(data);

        verify(mailer, times(1)).sendEmail(
                argThat(email_ -> {
                    assert Objects.equals(email_, email);
                    return true;
                }),
                argThat(subject_ -> {
                    assert Objects.equals(subject_, subject);
                    return true;
                }),
                argThat(content_ -> {
                    assert Objects.equals(content_, notification.getTemplate());
                    return true;
                })
        );
    }

    @Test
    void sendGreetings_mailerInvoked_void() throws Exception {
        String name = "name";
        String email = "email";
        Map<String, Object> data = new HashMap<>();
        data.put("name", name);
        data.put("email", email);
        Welcome welcome = new Welcome(name);
        String subject = "Nice to meet you, " + name;

        service.sendGreetings(data);

        verify(mailer, times(1)).sendEmail(
                argThat(email_ -> {
                    assert Objects.equals(email_, email);
                    return true;
                }),
                argThat(subject_ -> {
                    assert Objects.equals(subject_, subject);
                    return true;
                }),
                argThat(content_ -> {
                    assert Objects.equals(content_, welcome.getTemplate());
                    return true;
                })
        );
    }

    @Test
    void sendNewListingNotification_mailerInvoked_void() throws Exception {
        String email = "email";
        String title = "title";
        String image = "image";
        String link = "link";
        String description = "description";
        Map<String, Object> data = new HashMap<>();
        data.put("email", email);
        data.put("title", title);
        data.put("image", image);
        data.put("link", link);
        data.put("description", description);
        NewListing newListing = new NewListing(title, image, description, link);
        String subject = "Listing online!";

        service.sendNewListingNotification(data);

        verify(mailer, times(1)).sendEmail(
                argThat(email_ -> {
                    assert Objects.equals(email_, email);
                    return true;
                }),
                argThat(subject_ -> {
                    assert Objects.equals(subject_, subject);
                    return true;
                }),
                argThat(content_ -> {
                    assert Objects.equals(content_, newListing.getTemplate());
                    return true;
                })
        );
    }

    @Test
    void sendAccountDeletionNotification_mailerInvoked_void() throws Exception {
        String name = "name";
        String email = "email";
        Map<String, Object> data = new HashMap<>();
        data.put("name", name);
        data.put("email", email);
        Goodbye goodbyes = new Goodbye(name);
        String subject = "Account remove from Rently";

        service.sendAccountDeletionNotification(data);

        verify(mailer, times(1)).sendEmail(
                argThat(email_ -> {
                    assert Objects.equals(email_, email);
                    return true;
                }),
                argThat(subject_ -> {
                    assert Objects.equals(subject_, subject);
                    return true;
                }),
                argThat(content_ -> {
                    assert Objects.equals(content_, goodbyes.getTemplate());
                    return true;
                })
        );
    }

    @Test
    void sendListingDeletionNotification_mailerInvoked_void() throws Exception {
        String email = "email";
        String title = "title";
        String description = "description";
        Map<String, Object> data = new HashMap<>();
        data.put("email", email);
        data.put("title", title);
        data.put("description", description);
        ListingDeletion listingDeletion = new ListingDeletion(title, description);
        String subject = "Listing removed!";

        service.sendListingDeletionNotification(data);

        verify(mailer, times(1)).sendEmail(
                argThat(email_ -> {
                    assert Objects.equals(email_, email);
                    return true;
                }),
                argThat(subject_ -> {
                    assert Objects.equals(subject_, subject);
                    return true;
                }),
                argThat(content_ -> {
                    assert Objects.equals(content_, listingDeletion.getTemplate());
                    return true;
                })
        );
    }

    @Test
    void sendUpdateListingNotification_mailerInvoked_void() throws Exception {
        String email = "email";
        String title = "title";
        String image = "image";
        String link = "link";
        String description = "description";
        Map<String, Object> data = new HashMap<>();
        data.put("email", email);
        data.put("title", title);
        data.put("image", image);
        data.put("link", link);
        data.put("description", description);
        UpdatedListing updatedListing = new UpdatedListing(link, image, title, description);
        String subject = "Listing updated!";

        service.sendUpdateListingNotification(data);

        verify(mailer, times(1)).sendEmail(
                argThat(email_ -> {
                    assert Objects.equals(email_, email);
                    return true;
                }),
                argThat(subject_ -> {
                    assert Objects.equals(subject_, subject);
                    return true;
                }),
                argThat(content_ -> {
                    assert Objects.equals(content_, updatedListing.getTemplate());
                    return true;
                })
        );
    }

    @Test
    void trySendEmail_mailerInvoked_void() throws Exception {
        String email = "email";
        String subject = "subject";
        String body = "body";
        Notification template = new Notification(subject, body);

        service.trySendEmail(email, subject, template);

        verify(mailer, times(1)).sendEmail(
                argThat(email_ -> {
                    assert Objects.equals(email_, email);
                    return true;
                }),
                argThat(subject_ -> {
                    assert Objects.equals(subject_, subject);
                    return true;
                }),
                argThat(content_ -> {
                    assert Objects.equals(content_, template.getTemplate());
                    return true;
                })
        );
    }

    @Test
    void trySendEmail_invalidEmail_invalidEmailThrown() throws Exception {
        String email = "email";
        String subject = "subject";
        String body = "body";
        Notification template = new Notification(subject, body);

        Mockito.doThrow(new Exception())
                .when(mailer).sendEmail(email, subject, template.getTemplate());

        assertThrows(Errors.INVALID_EMAIL_ADDRESS.getClass(),
                () -> service.trySendEmail(email, subject, template));
    }
}