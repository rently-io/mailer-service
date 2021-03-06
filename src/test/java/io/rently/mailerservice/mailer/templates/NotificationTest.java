package io.rently.mailerservice.mailer.templates;

import org.junit.jupiter.api.Test;

class NotificationTest {

    @Test
    void getTemplate_validHtml() {
        String subject = "subject";
        String body = "body";
        Notification notification = new Notification(subject, body);

        String template = notification.getTemplate();

        assert template.contains(subject);
        assert template.contains(body);
    }
}