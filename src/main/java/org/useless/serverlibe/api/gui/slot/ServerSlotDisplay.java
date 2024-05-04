package org.useless.serverlibe.api.gui.slot;

import net.minecraft.core.item.ItemStack;
import net.minecraft.core.player.inventory.IInventory;
import org.jetbrains.annotations.Nullable;

public class ServerSlotDisplay extends ServerSlotBase{
	public ServerSlotDisplay(IInventory inventory, int id){
		this(null, inventory, id);
	}

	public ServerSlotDisplay(@Nullable ItemStack icon, IInventory inventory, int id) {
		super(inventory, id);
		inventory.setInventorySlotContents(id, icon);
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
