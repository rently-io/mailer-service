package io.rently.mailerservice.mailer.templates;

import org.junit.jupiter.api.Test;

class WelcomeTest {

    @Test
    void getTemplate_validHtml() {
        String person = "person";
        Welcome welcome = new Welcome(person);

        String template = welcome.getTemplate();

        assert template.contains(person);
    }
}