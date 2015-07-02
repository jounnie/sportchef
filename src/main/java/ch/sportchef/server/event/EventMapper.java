package ch.sportchef.server.event;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EventMapper implements ResultSetMapper<Event> {

    @Override
    public Event map(final int index, final ResultSet r, final StatementContext ctx) throws SQLException {
        return new Event(
                r.getLong("eventId"),
                r.getString("title"),
                r.getString("location"),
                r.getDate("date").toLocalDate()
        );
    }
}
