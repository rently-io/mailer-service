package io.rently.mailerservice;

import io.rently.mailerservice.services.Mailer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MailerAPI {

	public static void main(String[] args) {
		SpringApplication.run(MailerAPI.class, args);

		Mailer.sendMail();
	}

}
