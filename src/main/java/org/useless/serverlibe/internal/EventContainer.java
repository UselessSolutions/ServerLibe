package org.useless.serverlibe.internal;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;
import org.useless.serverlibe.callbacks.IEvent;
import org.useless.serverlibe.data.Order;
import org.useless.serverlibe.data.Priority;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@ApiStatus.Internal
public final class EventContainer<T extends IEvent> {
	@NotNull
	public final Map<Priority, List<T>> priorityEventCallbackMapBefore = new HashMap<>();
	@NotNull
	public final Map<Priority, List<T>> priorityEventCallbackMapAfter = new HashMap<>();
	public void addEvent(
		@NotNull final T event,
		@NotNull final Priority priority,
		@NotNull final Order order)
	{
		switch (order){
			case BEFORE:
				priorityEventCallbackMapBefore.putIfAbsent(priority, new ArrayList<>());
				priorityEventCallbackMapBefore.get(priority).add(event);
				break;
			case AFTER:
				priorityEventCallbackMapAfter.putIfAbsent(priority, new ArrayList<>());
				priorityEventCallbackMapAfter.get(priority).add(event);
				break;
			default:
				throw new RuntimeException("Unexpected Order token '" + order + "'!");
		}
	}
	@Unmodifiable
	public List<T> getEvents(Priority priority, Order order){
		switch (order){
			case BEFORE:
				return Collections.unmodifiableList(priorityEventCallbackMapBefore.getOrDefault(priority, new ArrayList<>()));
			case AFTER:
				return Collections.unmodifiableList(priorityEventCallbackMapAfter.getOrDefault(priority, new ArrayList<>()));
			default:
				throw new RuntimeException("Unexpected Order token '" + order + "'!");
		}
	}
}
