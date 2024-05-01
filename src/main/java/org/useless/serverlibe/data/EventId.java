package org.useless.serverlibe.data;

import org.jetbrains.annotations.NotNull;
import org.useless.serverlibe.callbacks.IEvent;
import org.useless.serverlibe.callbacks.player.IPlayerDig;
import org.useless.serverlibe.callbacks.player.IPlayerPlace;

import java.util.Locale;
import java.util.Objects;

public final class EventId<T extends IEvent> {
	@NotNull
	public static final EventId<IPlayerPlace> PLAYER_PLACE_EVENT_ID = new EventId<>("serverlibe$player_place", IPlayerPlace.class);
	public static final EventId<IPlayerDig> PLAYER_DIG_EVENT_ID = new EventId<>("serverlibe$player_dig", IPlayerDig.class);
	@NotNull
	public final String identifier;
	@NotNull
	public final Class<T> eventClass;
	public EventId(
		@NotNull final String id,
		@NotNull final Class<T> eventClass)
	{
		this.identifier = Objects.requireNonNull(id).toLowerCase(Locale.ROOT);
		this.eventClass = Objects.requireNonNull(eventClass);
	}
}
