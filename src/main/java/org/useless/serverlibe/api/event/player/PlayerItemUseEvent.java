package org.useless.serverlibe.api.event.player;

import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;
import org.jetbrains.annotations.NotNull;
import org.useless.serverlibe.api.event.Cancellable;
import org.useless.serverlibe.internal.EventContainer;

public class PlayerItemUseEvent extends PlayerEvent implements Cancellable {
	@NotNull
    public final World world;
	@NotNull
    public final ItemStack itemstack;

    public PlayerItemUseEvent
		(
			@NotNull final Player player,
			@NotNull final World world,
			@NotNull final ItemStack itemstack
		)
	{
		super(player);
        this.world = world;
        this.itemstack = itemstack;
    }

	private boolean cancelled = false;
	@Override
	public boolean isCancelled() {
		return cancelled;
	}
	@Override
	public void setCancelled(boolean cancel) {
		cancelled = cancel;
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
