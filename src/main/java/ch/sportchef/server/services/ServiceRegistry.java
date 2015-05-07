package ch.sportchef.server.services;

import javax.annotation.Nonnull;
import javax.management.ServiceNotFoundException;
import java.util.HashMap;
import java.util.Map;

/**
 * ...Documentation of an whiteboard pattern
 */
public final class ServiceRegistry {

    private static final Map<String, Service> services = new HashMap<>();

    private ServiceRegistry (){}

    public static <T extends Service> void register (final T service)
            throws NullPointerException {
        if (service == null) {
            throw new NullPointerException("Given service is null.");
        }
        services.put(service.getClass().getName(), service);
    }

    public static <T extends Service> T getService(@Nonnull Class<T> serviceClass)
            throws ServiceNotFoundException {
        Service service = services.get(serviceClass.getName());
        if ( service == null ) {
            throw new ServiceNotFoundException(String.format("Can't find service for %s.",serviceClass.getName()));
        }
        return serviceClass.cast(service);
    }
}