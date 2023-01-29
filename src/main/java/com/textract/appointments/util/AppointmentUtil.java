package com.textract.appointments.util;


import com.textract.appointments.model.Appointment;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AppointmentUtil {

    public static List<Appointment> parseAppointmentList(final List<String> stringList) {
        return stringList.stream()
                .map(AppointmentUtil::parseAppointment)
                .filter(Optional::isPresent)
                .map(Optional::get).collect(Collectors.toList());
    }

    public static Optional<Appointment> parseAppointment(String appointmentString) {
        final Pattern datePattern = Pattern.compile("\\d{2}\\.\\d{2}\\.\\d{4}\\s\\d{2}:\\d{2}");
        final Matcher dateMatcher = datePattern.matcher(appointmentString);

        final Pattern descriptionPattern = Pattern.compile("(?<=\\d{2}\\.\\d{2}\\.\\d{4}\\s\\d{2}:\\d{2}).*");
        final Matcher descriptionMatcher = descriptionPattern.matcher(appointmentString);

        Optional<Date> dateOptional = Optional.empty();
        if (dateMatcher.find()) {
            final String cleanedTimestamp = dateMatcher.group();
            try {
                dateOptional = Optional.of(DateUtils.parseDate(cleanedTimestamp, "dd.MM.yyyy HH:mm"));
            } catch (ParseException ignored) {
            }
        }

        Optional<String> descriptionOptional = Optional.empty();
        if (descriptionMatcher.find()) {
            final String description = descriptionMatcher.group();
            descriptionOptional = Optional.of(description.trim());
        }

        if (dateOptional.isPresent() && descriptionOptional.isPresent()) {
            return Optional.of(Appointment.builder()
                    .date(dateOptional.get())
                    .description(descriptionOptional.get())
                    .build());
        }

        return Optional.empty();
    }
}
