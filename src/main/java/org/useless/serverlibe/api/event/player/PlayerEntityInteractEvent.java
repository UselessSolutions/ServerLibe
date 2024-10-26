package org.useless.serverlibe.api.event.player;

import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.useless.serverlibe.api.event.Cancellable;
import org.useless.serverlibe.internal.EventContainer;

import java.util.Objects;

public class PlayerEntityInteractEvent extends PlayerEvent implements Cancellable {
	public static final int LEFT_MOUSE_BUTTON = 1;
	public static final int RIGHT_MOUSE_BUTTON = 1;
	@NotNull
	public final World world;
	@Nullable
	public final ItemStack itemstack;
	@NotNull
    public final Entity targetEntity;
    public final int mouseButton;

    public PlayerEntityInteractEvent
		(
			@NotNull final Player player,
			@NotNull final World world,
			@Nullable final ItemStack itemstack,
			@NotNull final Entity targetEntity,
			final int mouseButton) {
		super(player);
		this.world = Objects.requireNonNull(world);
		this.itemstack = itemstack;
        this.targetEntity = Objects.requireNonNull(targetEntity);
        this.mouseButton = mouseButton;
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
