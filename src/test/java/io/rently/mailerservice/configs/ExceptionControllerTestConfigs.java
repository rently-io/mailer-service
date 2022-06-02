package io.rently.mailerservice.configs;

import com.bugsnag.Bugsnag;
import io.rently.mailerservice.interfaces.IMailer;
import io.rently.mailerservice.services.ReporterService;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

@TestConfiguration
public class ExceptionControllerTestConfigs {

    @Bean
    @Primary
    public ReporterService reporterService() {
        return Mockito.mock(ReporterService.class);
    }

    @Bean
    @Primary
    public Bugsnag bugsnag() {
        return Mockito.mock(Bugsnag.class);
    }

    @Bean
    @Primary
    public IMailer iMailer() {
        return Mockito.mock(IMailer.class);
    }
}
