package org.useless.serverlibe.internal;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.useless.serverlibe.callbacks.IEvent;
import org.useless.serverlibe.callbacks.player.IPlayerDig;
import org.useless.serverlibe.callbacks.player.IPlayerMovement;
import org.useless.serverlibe.callbacks.player.IPlayerPlace;
import org.useless.serverlibe.data.EventId;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

@ApiStatus.Internal
public class InternalStorageClass {
	@NotNull
	public static final Set<Class<?>> eventHandlerClassesSet = new HashSet<>();
	@NotNull
	public static final Map<EventId<?>, EventContainer<?>> eventContainerMap = new HashMap<>();
	@NotNull
	public static final EventContainer<IPlayerPlace> playerPlaceEventContainer = registerEventContainer(EventId.PLAYER_PLACE_EVENT_ID, new EventContainer<>());
	@NotNull
	public static final EventContainer<IPlayerDig> playerDigEventContainer = registerEventContainer(EventId.PLAYER_DIG_EVENT_ID, new EventContainer<>());
	@NotNull
	public static final EventContainer<IPlayerMovement> playerMoveEventContainer = registerEventContainer(EventId.PLAYER_MOVE_EVENT_ID, new EventContainer<>());
	public static <T extends IEvent> EventContainer<T> registerEventContainer(
		@NotNull final EventId<T> id,
		@NotNull final EventContainer<T> container)
	{
		eventContainerMap.put(Objects.requireNonNull(id), Objects.requireNonNull(container));
		return container;
	}
	@SuppressWarnings("unchecked")
	public static <T extends IEvent> EventContainer<T> getEventContainer(@NotNull final EventId<T> id){
		return (EventContainer<T>) eventContainerMap.get(Objects.requireNonNull(id));
	}
}
