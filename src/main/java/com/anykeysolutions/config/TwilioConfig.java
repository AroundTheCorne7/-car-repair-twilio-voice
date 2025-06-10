package com.anykeysolutions.config;

import com.twilio.Twilio;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;

import jakarta.annotation.PostConstruct;

/**
 * Configuration class for Twilio integration.
 * Loads Twilio properties from application.properties and initializes the Twilio client.
 */
@Configuration
public class TwilioConfig {

    @Value("${twilio.account.sid:}")
    private String accountSid;

    @Value("${twilio.auth.token:}")
    private String authToken;

    @Value("${twilio.phone.number:}")
    private String phoneNumber;

    /**
     * Initialize Twilio client with account credentials.
     * This method is called after the bean is constructed and all dependencies are injected.
     */
    @PostConstruct
    public void initTwilio() {
        if (accountSid != null && !accountSid.isEmpty() && authToken != null && !authToken.isEmpty()) {
            Twilio.init(accountSid, authToken);
            System.out.println("Twilio initialized successfully");
        } else {
            System.out.println("Twilio credentials not provided - Twilio not initialized");
        }
    }

    /**
     * Expose Twilio account SID as a bean for dependency injection.
     * @return the Twilio account SID
     */
    @Bean
    public String twilioAccountSid() {
        return accountSid;
    }

    /**
     * Expose Twilio auth token as a bean for dependency injection.
     * @return the Twilio auth token
     */
    @Bean
    public String twilioAuthToken() {
        return authToken;
    }

    /**
     * Expose Twilio phone number as a bean for dependency injection.
     * @return the Twilio phone number
     */
    @Bean
    public String twilioPhoneNumber() {
        return phoneNumber;
    }
}
