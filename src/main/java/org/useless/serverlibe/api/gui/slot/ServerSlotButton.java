package org.useless.serverlibe.api.gui.slot;

import net.minecraft.core.player.inventory.IInventory;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class ServerSlotButton extends ServerSlotDisplay{
	@NotNull
	public final Runnable action;
	public ServerSlotButton(IInventory inventory, int id, @NotNull Runnable action) {
		super(inventory, id);
		this.action = Objects.requireNonNull(action);
	}
	@Override
	public void onInteract(){
		action.run();
	}
}
