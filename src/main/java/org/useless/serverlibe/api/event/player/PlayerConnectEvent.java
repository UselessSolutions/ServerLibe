package org.useless.serverlibe.api.event.player;

import net.minecraft.core.entity.player.Player;
import org.jetbrains.annotations.NotNull;
import org.useless.serverlibe.internal.EventContainer;

public class PlayerConnectEvent extends PlayerEvent{
	public PlayerConnectEvent(
		@NotNull final Player player
	) {
		super(player);
	}

	private static final EventContainer eventContainer = new EventContainer();
	@NotNull
	public EventContainer getEvents() {
		return eventContainer;
	}

	@NotNull
	public static EventContainer getEventContainer() {
		return eventContainer;
	}
}
