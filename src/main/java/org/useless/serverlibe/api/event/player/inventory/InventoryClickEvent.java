package org.useless.serverlibe.api.event.player.inventory;

import net.minecraft.core.InventoryAction;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.useless.serverlibe.api.event.ICancellable;
import org.useless.serverlibe.internal.EventContainer;

import java.util.Objects;

public class InventoryClickEvent extends InventoryEvent implements ICancellable {
	@NotNull
	public InventoryAction action;
	public int[] args;
	public short actionId;
	@Nullable
	public ItemStack itemStack;
	public InventoryClickEvent(
		@NotNull final EntityPlayer player,
		final int windowID,
		@NotNull final InventoryAction action,
		final int @NotNull [] args,
		final short actionId,
		@Nullable ItemStack itemStack

	)
	{
		super(player, windowID);
        this.action = Objects.requireNonNull(action);
        this.args = args;
        this.actionId = actionId;
        this.itemStack = itemStack;
    }

	private boolean cancelled = false;
	@Override
	public boolean isCancelled() {
		return cancelled;
	}

	@Override
	public void setCancelled(boolean cancel) {
		this.cancelled = cancel;
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
