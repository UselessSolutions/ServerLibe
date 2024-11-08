package org.useless.serverlibe.api.event.player.inventory;

import net.minecraft.core.entity.player.Player;
import org.jetbrains.annotations.NotNull;
import org.useless.serverlibe.api.event.Event;

import java.util.Objects;

abstract class InventoryEvent extends Event {
	@NotNull
	public final Player player;
	public final int windowID;
	public InventoryEvent
		(
		@NotNull Player player,
		final int windowID
		)
	{
		this.player = Objects.requireNonNull(player);
		this.windowID = windowID;
	}
}
