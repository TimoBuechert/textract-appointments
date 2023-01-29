package com.textract.appointments.service;

import com.textract.appointments.config.TextractConfig;
import com.textract.appointments.model.Appointment;
import jakarta.annotation.PostConstruct;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.DateTime;
import net.fortuna.ical4j.model.TimeZoneRegistryFactory;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.component.VTimeZone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Date;
import java.util.List;

@Service
public class IcsService {

    private final TextractConfig textractConfig;

    private VTimeZone timeZone;

    public IcsService(@Autowired final TextractConfig textractConfig) {
        this.textractConfig = textractConfig;
    }

    @PostConstruct
    private void postConstruct() {
        this.timeZone = TimeZoneRegistryFactory.getInstance().createRegistry().getTimeZone(textractConfig.getTimeZoneString()).getVTimeZone();
    }

    public Resource convertToIcsFile(final List<Appointment> appointmentList) {
        final Calendar icsCalendar = new Calendar().withDefaults().getFluentTarget();

        for (final Appointment appointment : appointmentList) {
            final DateTime dateTimeBegin = new DateTime(appointment.getDate());
            final DateTime dateTimeEnd = new DateTime(Date.from(dateTimeBegin.toInstant().plus(Duration.ofHours(textractConfig.getAppointmentDefaultDuration()))));

            final VEvent meeting = new VEvent(dateTimeBegin, dateTimeEnd, appointment.getDescription())
                    .withProperty(timeZone.getTimeZoneId())
                    .getFluentTarget();
            icsCalendar.withComponent(meeting);
        }
        return new ByteArrayResource(icsCalendar.toString().getBytes());
    }
}
