package org.useless.serverlibe.api.gui.slot;

import net.minecraft.core.item.ItemStack;
import net.minecraft.core.player.inventory.IInventory;

public class ServerSlotDisplay extends ServerSlotBase{


	public ServerSlotDisplay(IInventory inventory, int id) {
		super(inventory, id);
	}
	@Override
	public boolean allowItemInteraction() {
		return false;
	}
	@Override
	public boolean canPutStackInSlot(ItemStack itemstack) {
		return false;
	}
}
