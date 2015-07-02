package ch.sportchef.server.event;

import org.skife.jdbi.v2.sqlobject.*;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import java.util.List;

@RegisterMapper(EventMapper.class)
public interface EventDAO {

    @GetGeneratedKeys
    @SqlUpdate("INSERT INTO event (eventId, title, location, date) VALUES (NULL, :title, :location, :date)")
    long create(@BindBean Event event);

    @SqlQuery("SELECT * FROM event ORDER BY eventId")
    List<Event> readAllEvents();

    @SqlQuery("SELECT * FROM event WHERE eventId = :eventId")
    Event readById(@Bind("eventId") final long eventId);

    @SqlUpdate("UPDATE event SET title = :title, location = :location, date = :date WHERE eventId = :eventId")
    void update(@BindBean Event event);

    @SqlUpdate("DELETE FROM event WHERE eventId = :eventId")
    void delete(@BindBean Event event);
}
