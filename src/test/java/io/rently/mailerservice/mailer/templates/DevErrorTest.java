package io.rently.mailerservice.mailer.templates;

import org.junit.jupiter.api.Test;

import java.util.List;

class DevErrorTest {

    @Test
    void getTemplate_validHtml() {
        String service = "service";
        String message = "message";
        String cause = "cause";
        String trace = "trace";
        String exceptionType = "exception";
        List<String> emails = List.of("email1", "email2");
        String time = "time";
        DevError devError = new DevError(service, message, cause, trace, exceptionType, emails, time);

        String template = devError.getTemplate();

        assert template.contains(service);
        assert template.contains(message);
        assert template.contains(cause);
        assert template.contains(trace);
        assert template.contains(exceptionType);
        assert template.contains(time);
    }
}