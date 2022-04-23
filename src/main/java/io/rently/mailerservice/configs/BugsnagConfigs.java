package io.rently.mailerservice.configs;

import com.bugsnag.Bugsnag;
import com.bugsnag.BugsnagSpringConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import javax.annotation.PostConstruct;

@Configuration
@Import(BugsnagSpringConfiguration.class)
public class BugsnagConfigs {
    private final String key;

    public BugsnagConfigs(@Value("${bugsnag.key}") String key) {
        this.key = key;
    }

    @Bean
    public Bugsnag bugsnag() {
        return new Bugsnag(key);
    }
}