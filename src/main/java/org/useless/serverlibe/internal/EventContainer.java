package org.useless.serverlibe.internal;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;
import org.useless.serverlibe.api.Listener;
import org.useless.serverlibe.api.annotations.EventListener;
import org.useless.serverlibe.api.event.ICancellable;
import org.useless.serverlibe.data.Priority;
import org.useless.serverlibe.data.Triple;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@ApiStatus.Internal
public final class EventContainer {
	@NotNull
	public final Map<Priority, List<Triple<Listener, Method, EventListener>>> priorityEventCallbackMap = new HashMap<>();
	public void addEvent(
		@NotNull final Listener listener,
		@NotNull final Method event,
		@NotNull final EventListener annotation)
	{
		priorityEventCallbackMap.putIfAbsent(annotation.priority(), new ArrayList<>());
		priorityEventCallbackMap.get(annotation.priority()).add(new Triple<>(listener, event, annotation));
	}
	@Unmodifiable
	public List<Triple<Listener, Method, EventListener>> getEntries(@NotNull Priority priority){
		return Collections.unmodifiableList(priorityEventCallbackMap.getOrDefault(priority, new ArrayList<>()));
	}
	@NotNull
	public <T> T runMethods(@NotNull final T event) {
		for (final Priority priority : Priority.values()){
			final List<Triple<Listener, Method, EventListener>> entries = priorityEventCallbackMap.get(priority);
			if (entries != null){
				for (final Triple<Listener, Method, EventListener> entry : entries){
					if (event instanceof ICancellable && ((ICancellable) event).isCancelled() && entry.third.ignoreCancelled()) continue; // Skip methods which are set to not run when event has been cancelled
                    try {
                        entry.second.invoke(entry.first, event);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        throw new RuntimeException(e);
                    }
                }
			}
		}
		return event;
	}
}
