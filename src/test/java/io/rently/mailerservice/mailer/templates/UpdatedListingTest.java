package io.rently.mailerservice.mailer.templates;

import org.junit.jupiter.api.Test;

class UpdatedListingTest {

    @Test
    void getTemplate_validHtml() {
        String link = "link";
        String image = "image";
        String desc = "desc";
        String title = "title";
        UpdatedListing updatedListing = new UpdatedListing(link, image, title, desc);

        String template = updatedListing.getTemplate();

        assert template.contains(link);
        assert template.contains(image);
        assert template.contains(desc);
        assert template.contains(title);
    }
}