package org.useless.serverlibe.api.event.player.inventory;

import net.minecraft.core.entity.player.Player;
import org.jetbrains.annotations.NotNull;
import org.useless.serverlibe.internal.EventContainer;

public class InventoryCloseEvent extends InventoryEvent {
	public InventoryCloseEvent
		(
			@NotNull Player player,
			final int windowID
		)
	{
		super(player, windowID);
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
