package io.rently.mailerservice.mailer.templates;

import org.junit.jupiter.api.Test;

class GoodbyeTest {

    @Test
    void getTemplate_validHtml() {
        String person = "person";
        Goodbye goodbyes = new Goodbye(person);

        String template = goodbyes.getTemplate();

        assert template.contains(person);
    }
}