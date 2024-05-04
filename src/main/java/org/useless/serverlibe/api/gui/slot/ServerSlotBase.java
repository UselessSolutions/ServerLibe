package org.useless.serverlibe.api.gui.slot;

import net.minecraft.core.player.inventory.IInventory;
import net.minecraft.core.player.inventory.slot.Slot;

public class ServerSlotBase extends Slot {
	public ServerSlotBase(IInventory inventory, int id) {
		super(inventory, id, 0, 0); // Position doesn't matter since this is server only
	}
	public void onInteract(){

	}
}
