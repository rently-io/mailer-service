package io.rently.mailerservice.mailer.templates;

import org.junit.jupiter.api.Test;

class NewListingTest {

    @Test
    void getTemplate_validHtml() {
        String link = "link";
        String image = "image";
        String desc = "desc";
        String title = "title";
        NewListing listingDeletion = new NewListing(link, image, title, desc);

        String template = listingDeletion.getTemplate();

        assert template.contains(title);
        assert template.contains(desc);
        assert template.contains(image);
        assert template.contains(link);
    }
}