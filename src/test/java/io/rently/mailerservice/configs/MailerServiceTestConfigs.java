package io.rently.mailerservice.configs;

import io.rently.mailerservice.interfaces.IMailer;
import io.rently.mailerservice.services.ReporterService;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

@TestConfiguration
public class MailerServiceTestConfigs {

    @Bean
    @Primary
    public IMailer iMailer() {
        return Mockito.mock(IMailer.class);
    }

    @Bean
    @Primary
    public ReporterService reporterService() {
        return Mockito.mock(ReporterService.class);
    }
}
