package org.useless.serverlibe.api.event.player;

import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.useless.serverlibe.api.event.Cancellable;
import org.useless.serverlibe.internal.EventContainer;

import java.util.Objects;

public class PlayerItemPlaceEvent extends PlayerEvent implements Cancellable {
	@NotNull
	public final World world;
	@Nullable
	public final ItemStack itemstack;
	public final int x;
	public final int y;
	public final int z;
	@NotNull
	public final Side side;
	public final double xPlaced;
	public final double yPlaced;
	public PlayerItemPlaceEvent(
		@NotNull final EntityPlayer entityplayer,
		@NotNull final World world,
		@Nullable final ItemStack itemstack,
		final int x,
		final int y,
		final int z,
		@NotNull final Side side,
		final double xPlaced,
		final double yPlaced)
	{
		super(entityplayer);
        this.world = Objects.requireNonNull(world);
        this.itemstack = itemstack;
        this.x = x;
        this.y = y;
        this.z = z;
        this.side = Objects.requireNonNull(side);
        this.xPlaced = xPlaced;
        this.yPlaced = yPlaced;
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
