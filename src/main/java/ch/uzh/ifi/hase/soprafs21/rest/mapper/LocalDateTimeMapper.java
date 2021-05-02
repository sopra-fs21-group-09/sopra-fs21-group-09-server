package ch.uzh.ifi.hase.soprafs21.rest.mapper;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class LocalDateTimeMapper {

    public String asString(LocalDateTime time) {
        return time != null ? time.toLocalDate().toString() : null;
    }

    public LocalDateTime asLocalDateTime(String time) {
        try {
            return time != null ? LocalDate.parse(time).atTime(12,0) : null;
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
