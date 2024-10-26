package org.useless.serverlibe.api.event.player;

import net.minecraft.core.entity.player.Player;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.World;
import org.jetbrains.annotations.NotNull;
import org.useless.serverlibe.api.event.Cancellable;
import org.useless.serverlibe.internal.EventContainer;

import java.util.Objects;

public class PlayerDigEvent extends PlayerEvent implements Cancellable {
	public static final int START_MINING = 0;
	public static final int HIT_BLOCK = 1;
	public static final int DESTROY_BLOCK = 2;
	@NotNull
	public final World world;
	public final int x;
	public final int y;
	public final int z;
	@NotNull
	public final Side side;
	public final int status;
	public PlayerDigEvent
		(
			@NotNull final Player player,
			@NotNull final World world,
			final int x,
			final int y,
			final int z,
			@NotNull final Side side,
			final int status
		)
	{
		super(player);
        this.world = Objects.requireNonNull(world);
        this.x = x;
        this.y = y;
        this.z = z;
        this.side = Objects.requireNonNull(side);
		this.status = status;
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
