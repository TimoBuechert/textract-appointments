package com.textract.appointments.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MatcherUtil {

    private static final String DATE_REGEX = "([A-Za-z]{2})\\s([0-9]{2})\\.([0-9]{2})\\.([0-9]{4})\\s([0-9]{2}):([0-9]{2})";
    private static final String TYPE_REGEX = "([A-Z]{1,3})\\s([A-Z])?\\s?([(])([A-Z]{1,}\\.?\\s?[A-Za-zÄÖÜäöü]*)([\\s/]*[A-Z]{1}\\.\\s?[A-Za-zÄÖÜäöü]*)*([)])";

    private static final String COMPLETE_LINE_REGEX = DATE_REGEX + "\s" + TYPE_REGEX;

    public static boolean matchesDateFormat(final String line) {
        return line.matches(DATE_REGEX);
    }

    public static boolean matchesTypeFormat(final String line) {
        return line.matches(TYPE_REGEX);
    }

    public static boolean matchesWholeLine(final String line) {
        return line.matches(COMPLETE_LINE_REGEX);
    }

}
