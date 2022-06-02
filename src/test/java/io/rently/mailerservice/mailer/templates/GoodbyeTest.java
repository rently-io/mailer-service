package io.rently.mailerservice.mailer.templates;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GoodbyeTest {

    @Test
    void getTemplate_validHtml() {
        String person = "person";
        Goodbye goodbyes = new Goodbye(person);

        String template = goodbyes.getTemplate();

        assert template.contains(person);
    }
}