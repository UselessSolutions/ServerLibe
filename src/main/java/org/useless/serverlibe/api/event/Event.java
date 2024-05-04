package org.useless.serverlibe.api.event;

/**
 * Base class for all events
 *
 * @implNote All usable events must include both a
 * <br>{@code public EventContainer getEvents()}<br/>
 * and a
 * <br>{@code public static EventContainer getEventContainer()}<br/>
 * in order to function
 *
 * @author Useless
 * @since beta-1
 */
public abstract class Event {
}
