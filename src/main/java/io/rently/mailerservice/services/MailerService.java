package io.rently.mailerservice.services;

import io.rently.mailerservice.utils.Mailer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MailerService {
    private final Mailer mailer;

    public MailerService(Mailer mailer) {
        this.mailer = mailer;
    }

    public MailerService() {
        this(
                new Mailer.Builder("info.rently.io@gmail.com")
                    .credentials("info.rently.io@gmail.com", "ncsyuuohohavmgss")
                    .host("smtp.gmail.com")
                    .build()
        );
    }

    public void sendMail() {
        mailer.sendMailTo("greffchandler80@gmail.com", "test mail", "this is a test mail from the mailer service");
    }
}
