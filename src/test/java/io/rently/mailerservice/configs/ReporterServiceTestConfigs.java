package io.rently.mailerservice.configs;

import io.rently.mailerservice.interfaces.IMailer;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

@TestConfiguration
public class ReporterServiceTestConfigs {

    @Bean
    @Primary
    public IMailer iMailer() {
        return Mockito.mock(IMailer.class);
    }
}
