package kbaldr2.helper;

import java.time.*;
import java.time.format.DateTimeFormatter;

/**
 * A utility class for handling date and time operations, such as conversions
 * between different time zones and formatting.
 */
public class Formatter {
    
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");
    
    /**
     * Parses a time string in the format "HH:mm" and returns a LocalTime object.
     *
     * @param timeString the time string to parse
     * @return a LocalTime object representing the parsed time
     */
    public static LocalTime parseTime(String timeString) {
        
        return LocalTime.parse(timeString, TIME_FORMATTER);
    }
    
    /**
     * Formats a LocalTime object as a time string in the format "HH:mm:ss".
     *
     * @param time the LocalTime object to format
     * @return a string representing the formatted time
     */
    public static String formatTime(LocalTime time) {
        
        return time.format(TIME_FORMATTER);
    }
    
    /**
     * Converts a UTC LocalDateTime to a LocalDateTime object in the local time zone.
     *
     * @param inputDateTime the LocalDateTime object in UTC to convert
     * @return a LocalDateTime object representing the input time in the local time zone
     */
    public static LocalDateTime UTCtoLocal(LocalDateTime inputDateTime) {
        
        ZonedDateTime zonedDateTime = inputDateTime.atZone(ZoneId.of("UTC"));
        ZonedDateTime localZonedDateTime = zonedDateTime.withZoneSameInstant(ZoneId.systemDefault());
        return localZonedDateTime.toLocalDateTime();
    }
    
    /**
     * Converts a local LocalDateTime to a LocalDateTime object in the UTC time zone.
     *
     * @param inputDateTime the LocalDateTime object in the local time zone to convert
     * @return a LocalDateTime object representing the input time in the UTC time zone
     */
    public static LocalDateTime localToUTC(LocalDateTime inputDateTime) {
        
        ZonedDateTime localZonedDateTime = inputDateTime.atZone(ZoneId.systemDefault());
        ZonedDateTime utcZonedDateTime = localZonedDateTime.withZoneSameInstant(ZoneId.of("UTC"));
        return utcZonedDateTime.toLocalDateTime();
    }
    
    /**
     * Converts an Eastern Time LocalTime to a LocalTime object in the local time zone.
     *
     * @param easternTime the LocalTime object in Eastern Time to convert
     * @return a LocalTime object representing the input time in the local time zone
     */
    public static LocalTime ESTtoLocal(LocalTime easternTime) {
        
        ZonedDateTime easternDateTime = ZonedDateTime.of(LocalDate.now(), easternTime, ZoneId.of("America/New_York"));
        ZonedDateTime localDateTime = easternDateTime.withZoneSameInstant(ZoneId.systemDefault());
        return localDateTime.toLocalTime();
    }
    
}