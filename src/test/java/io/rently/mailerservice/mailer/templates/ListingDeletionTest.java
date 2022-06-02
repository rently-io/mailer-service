package io.rently.mailerservice.mailer.templates;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ListingDeletionTest {

    @Test
    void getTemplate_validHtml() {
        String title = "title";
        String description = "desc";
        ListingDeletion listingDeletion = new ListingDeletion(title, description);

        String template = listingDeletion.getTemplate();

        assert template.contains(title);
        assert template.contains(description);
    }
}