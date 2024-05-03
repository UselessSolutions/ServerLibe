package org.useless.serverlibe.internal;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.useless.serverlibe.api.Listener;
import org.useless.serverlibe.api.annotations.EventListener;
import org.useless.serverlibe.api.enums.Priority;
import org.useless.serverlibe.api.event.Cancellable;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@ApiStatus.Internal
public final class EventContainer {
	@NotNull
	private final Map<Priority, List<EventEntry>> priorityEventCallbackMap = new HashMap<>();
	public void addEvent(
		@NotNull final Listener listener,
		@NotNull final Method event,
		@NotNull final EventListener annotation)
	{
		priorityEventCallbackMap.putIfAbsent(annotation.priority(), new ArrayList<>());
		priorityEventCallbackMap.get(annotation.priority()).add(new EventEntry(listener, event, annotation));
	}
	@NotNull
	public <T> T runMethods(@NotNull final T event) {
		for (final Priority priority : Priority.values()){
			final List<EventEntry> entries = priorityEventCallbackMap.get(priority);
			if (entries != null){
				for (final EventEntry entry : entries){
					if (event instanceof Cancellable && ((Cancellable) event).isCancelled() && entry.annotation.ignoreCancelled()) continue; // Skip methods which are set to not run when event has been cancelled
                    try {
                        entry.event.invoke(entry.listener, event);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        throw new RuntimeException(e);
                    }
                }
			}
		}
		return event;
	}

	static class EventEntry{
		@NotNull public final Listener listener;
		@NotNull public final Method event;
		@NotNull public final EventListener annotation;
		public EventEntry(
			@NotNull final Listener listener,
			@NotNull final Method event,
			@NotNull final EventListener annotation
		){
			this.listener = listener;
			this.event = event;
			this.annotation = annotation;
		}
	}
}
