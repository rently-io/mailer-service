package io.rently.mailerservice.configs;

import io.rently.mailerservice.interfaces.IMailer;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class MailerControllerTestConfigs {

    @Bean
    @Primary
    public IMailer mailer() {
        return Mockito.mock(IMailer.class);
    }
}
