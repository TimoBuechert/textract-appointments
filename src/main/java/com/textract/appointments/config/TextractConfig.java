package com.textract.appointments.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.textract.TextractClient;

@Configuration
public class TextractConfig {

    @Value("${textract.region}")
    private Region textractRegion;

    @Bean(destroyMethod = "close")
    public TextractClient textractClient() {
        return TextractClient.builder()
                .region(textractRegion)
                .credentialsProvider(ProfileCredentialsProvider.create())
                .build();
    }
}
