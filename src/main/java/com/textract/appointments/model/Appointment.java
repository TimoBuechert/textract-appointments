package com.textract.appointments.model;

import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Builder
@Getter
public class Appointment {

    private final Date date;

    private final String description;

}
