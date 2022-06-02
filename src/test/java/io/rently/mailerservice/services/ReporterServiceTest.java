package io.rently.mailerservice.services;

import io.rently.mailerservice.configs.BugsnagTestConfigs;
import io.rently.mailerservice.configs.ReporterServiceTestConfigs;
import io.rently.mailerservice.interfaces.IMailer;
import io.rently.mailerservice.mailer.templates.DevError;
import io.rently.mailerservice.mailer.templates.Notification;
import io.rently.mailerservice.utils.Broadcaster;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = { ReporterServiceTestConfigs.class, BugsnagTestConfigs.class })
@WebMvcTest(ReporterService.class)
class ReporterServiceTest {

    @Autowired
    public ReporterService service;
    @Autowired
    public IMailer mailer;

    @BeforeEach
    public void setup() {
        reset(mailer);
    }

    @Test
    void sendReportToDevs_mailerInvoked_void() throws Exception {
        String serviceName = "Mailer service";
        Exception exception = new Exception("This is an exception");
        Map<String, Object> report = new HashMap<>();
        report.put("datetime", new Date());
        report.put("message", exception.getMessage());
        report.put("service", serviceName);
        report.put("cause", "cause");
        report.put("trace", Arrays.toString(exception.getStackTrace()));
        report.put("exceptionType", exception.getClass());
        DevError devError = new DevError(
                serviceName,
                exception.getMessage(),
                "cause",
                Arrays.toString(exception.getStackTrace()),
                exception.getClass().toString(),
                service.firstResponders,
                new Date().toString()
        );
        String subject = "[ERROR] " + serviceName;

        service.sendReportToDevs(report);

        verify(mailer, times(service.firstResponders.size())).sendEmail(
                argThat(email_ -> {
                    assert service.firstResponders.contains(email_);
                    return true;
                }),
                argThat(subject_ -> {
                    assert Objects.equals(subject_, subject);
                    return true;
                }),
                argThat(content_ -> {
                    Broadcaster.debug(content_);
                    Broadcaster.debug(devError.getTemplate());
                    assert Objects.equals(content_, devError.getTemplate());
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

        service.trySendingEmail(email, subject, template);

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
    void trySendEmail_invalidEmail_noExceptionThrown() throws Exception {
        String email = "email";
        String subject = "subject";
        String body = "body";
        Notification template = new Notification(subject, body);

        Mockito.doThrow(new Exception())
                .when(mailer).sendEmail(email, subject, template.getTemplate());

        assertDoesNotThrow(() -> service.trySendingEmail(email, subject, template));
    }
}