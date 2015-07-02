package ch.sportchef.server.event;

import java.time.LocalDate;

public class EventGenerator {

    public static Event getFootballWm(final long eventId) {
        // TODO TEST NOK: Umlaut generates "unmappable character for encoding UTF-8"
        return new Event(eventId, "Football WM 2014", "Zuerich", LocalDate.of(2014, 5, 6));
    }

    public static Event getTennisWm(final long eventId) {
        return new Event(eventId, "Tennis WM 2015", "Zuerich", LocalDate.of(2015, 5, 6));
    }

    public static Event getIcehokeyWm(final long eventId) {
        return new Event(eventId, "Icehockey WM 2015", "Zuerich", LocalDate.of(2015, 5, 6));
    }
}
