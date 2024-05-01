package org.useless.serverlibe.callbacks.player;

import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.world.World;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public final class PlayerMovementEvent {
	@NotNull
	public final EntityPlayer player;
	@NotNull
	public final World world;
	public final double xPosition;
	public final double yPosition;
	public final double zPosition;
	public final double distanceMoved;
	public final double stance;
	public final float yaw;
	public final float pitch;
	public final boolean onGround;
	public final boolean moving;
	public final boolean rotating;

    public PlayerMovementEvent
	(
		@NotNull final EntityPlayer player,
		@NotNull final World world,
		final double xPos,
		final double yPos,
		final double zPos,
		final double distanceMoved,
		final double stance,
		final float yaw,
		final float pitch,
		final boolean onGround,
		final boolean moving,
		final boolean rotating
	)
	{
		this.player = Objects.requireNonNull(player);
		this.world = Objects.requireNonNull(world);
        this.xPosition = xPos;
        this.yPosition = yPos;
        this.zPosition = zPos;
		this.distanceMoved = distanceMoved;
        this.stance = stance;
        this.yaw = yaw;
        this.pitch = pitch;
        this.onGround = onGround;
        this.moving = moving;
        this.rotating = rotating;
    }
}
