package io.rently.mailerservice.configs;

import com.bugsnag.Bugsnag;
import com.bugsnag.BugsnagSpringConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(BugsnagSpringConfiguration.class)
public class BugsnagConfigs {
    private static final String API_KEY= "df26958608eb1ba71395ab7714759c71";

    @Bean
    public Bugsnag bugsnag() {
        return new Bugsnag(API_KEY);
    }
}