package com.textract.appointments.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.services.textract.TextractClient;

@Configuration
public class TextractConfig {

    private final TextractProperties textractProperties;

    public TextractConfig(@Autowired final TextractProperties textractProperties) {
        this.textractProperties = textractProperties;
    }

    @Bean(destroyMethod = "close")
    public TextractClient textractClient() {
        return TextractClient.builder()
                .region(textractProperties.getRegion())
                .credentialsProvider(ProfileCredentialsProvider.create())
                .build();
    }
}
