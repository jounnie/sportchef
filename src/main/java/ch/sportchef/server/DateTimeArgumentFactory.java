package ch.sportchef.server;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.Argument;
import org.skife.jdbi.v2.tweak.ArgumentFactory;

import java.sql.Date;
import java.time.LocalDate;

public class DateTimeArgumentFactory implements ArgumentFactory<LocalDate> {
    @Override
    public boolean accepts(Class<?> expectedType, Object value, StatementContext ctx) {
        return value != null && LocalDate.class.isAssignableFrom(value.getClass());
    }

    @Override
    public Argument build(Class<?> expectedType, final LocalDate value, StatementContext ctx) {
        return (position, statement, ctx1) -> statement.setDate(position, Date.valueOf(value));
    }
}
