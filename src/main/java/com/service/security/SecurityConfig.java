package com.service.security;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.oauth2.client.servlet.OAuth2ClientAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * The Security config of the application.
 */
@Configuration
public class SecurityConfig {


    /**
     * The web security config which disables all security by default for this example application.
     */
    @Profile("no-auth")
    @Configuration
    @EnableAutoConfiguration(exclude = {SecurityAutoConfiguration.class, OAuth2ClientAutoConfiguration.class})
    protected static class DefaultWebSecurityConfig {
    }

    /**
     * The web security config which enables oauth security when running the prod profile.
     */
    @Configuration
    @EnableAutoConfiguration
    @EnableWebSecurity
    @Profile("prod")
    protected static class LocalWebSecurityConfig extends WebSecurityConfigurerAdapter {
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.authorizeRequests().anyRequest().authenticated().and().oauth2Login().and().csrf().disable();
        }
    }

}
