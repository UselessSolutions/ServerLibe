package org.useless.serverlibe.api.event.player.inventory;

import net.minecraft.core.entity.player.EntityPlayer;
import org.jetbrains.annotations.NotNull;
import org.useless.serverlibe.api.event.player.PlayerEvent;

abstract class InventoryEvent extends PlayerEvent {
	public final int windowID;
	public InventoryEvent
		(
		@NotNull EntityPlayer player,
		final int windowID
		)
	{
		super(player);
		this.windowID = windowID;
	}
}
