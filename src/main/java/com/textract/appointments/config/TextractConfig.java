package com.textract.appointments.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.textract.TextractClient;

@Configuration
@Getter
public class TextractConfig {

    @Value("${textract.region}")
    private Region textractRegion;

    @Value("${textract.appointment.line-proximity}")
    private double lineProximity;

    @Value("${textract.appointment.default-duration}")
    private long appointmentDefaultDuration;

    @Value("${textract.appointment.time-zone}")
    private String timeZoneString;

    @Bean(destroyMethod = "close")
    public TextractClient textractClient() {
        return TextractClient.builder()
                .region(textractRegion)
                .credentialsProvider(ProfileCredentialsProvider.create())
                .build();
    }
}
