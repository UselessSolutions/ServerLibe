package org.useless.serverlibe.api.event.player;

import net.minecraft.core.entity.player.Player;
import net.minecraft.core.world.World;
import org.jetbrains.annotations.NotNull;
import org.useless.serverlibe.api.event.Cancellable;
import org.useless.serverlibe.internal.EventContainer;

import java.util.Objects;

public class PlayerMovementEvent extends PlayerEvent implements Cancellable {
	@NotNull
	public final World world;
	public final double xPosition;
	public final double yPosition;
	public final double zPosition;
	public final double distanceMoved;
	public final float yaw;
	public final float pitch;
	public final boolean onGround;
	public final boolean hasPosition;
	public final boolean hasRotation;

    public PlayerMovementEvent
	(
		@NotNull final Player player,
		@NotNull final World world,
		final double xPos,
		final double yPos,
		final double zPos,
		final double distanceMoved,
		final float yaw,
		final float pitch,
		final boolean onGround,
		final boolean hasPosition,
		final boolean hasRotation
	)
	{
		super(player);
		this.world = Objects.requireNonNull(world);
        this.xPosition = xPos;
        this.yPosition = yPos;
        this.zPosition = zPos;
		this.distanceMoved = distanceMoved;
        this.yaw = yaw;
        this.pitch = pitch;
        this.onGround = onGround;
        this.hasPosition = hasPosition;
        this.hasRotation = hasRotation;
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
