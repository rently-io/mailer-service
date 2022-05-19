package io.rently.mailerservice.configs;

import io.jsonwebtoken.SignatureAlgorithm;
import io.rently.mailerservice.middlewares.Interceptor;
import io.rently.mailerservice.utils.Broadcaster;
import io.rently.mailerservice.utils.Jwt;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AppConfigs implements WebMvcConfigurer {

    @Value("${server.secret}")
    public String secret;
    @Value("${server.algo}")
    public SignatureAlgorithm algo;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedMethods("*").allowedHeaders("*");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new Interceptor(new Jwt(secret, algo), RequestMethod.GET));
    }

    @Bean
    public Jwt jwt() {
        Broadcaster.info("Loaded service middleware with secret `" + secret + "`");
        Broadcaster.info("Loaded service middleware with algo `" + algo + "`");
        return new Jwt(secret, algo);
    }
}