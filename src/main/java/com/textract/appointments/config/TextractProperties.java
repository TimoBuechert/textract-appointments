package com.textract.appointments.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.regions.Region;

@Component
@Data
@ConfigurationProperties(prefix = "textract")
public class TextractProperties {

    private Region region;

    private double appointmentLineProximity;

    private long appointmentDefaultDuration;

    private String appointmentTimeZone;

}
