package org.useless.serverlibe.api.event.player.inventory;

import net.minecraft.core.entity.player.EntityPlayer;
import org.jetbrains.annotations.NotNull;
import org.useless.serverlibe.api.event.Cancellable;
import org.useless.serverlibe.internal.EventContainer;

import java.util.Objects;

public class InventoryServerOpenEvent extends InventoryEvent implements Cancellable {
    public final int inventoryType;
	@NotNull
    public final String windowTitle;
    public final int slotsCount;

    public InventoryServerOpenEvent
		(
			@NotNull EntityPlayer player,
			final int windowID,
			final int inventoryType,
			@NotNull final String windowTitle,
			final int slotsCount
		)
	{
		super(player, windowID);
        this.inventoryType = inventoryType;
        this.windowTitle = Objects.requireNonNull(windowTitle);
        this.slotsCount = slotsCount;
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

	private boolean cancelled;
	@Override
	public boolean isCancelled() {
		return cancelled;
	}

	@Override
	public void setCancelled(boolean cancel) {
		this.cancelled = cancel;
	}
}
