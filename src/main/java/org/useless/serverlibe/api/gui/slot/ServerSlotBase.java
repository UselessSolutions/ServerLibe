package org.useless.serverlibe.api.gui.slot;

import net.minecraft.core.item.ItemStack;
import net.minecraft.core.player.inventory.container.Inventory;
import net.minecraft.core.player.inventory.slot.Slot;
import org.useless.serverlibe.api.event.player.inventory.InventoryClickEvent;

/**
 * Simplified version of the vanilla {@link Slot} class which for goes the position values since the server has no control over where slots will be placed.
 * New {@link org.useless.serverlibe.ServerLibe ServerLibe} specific methods are also to be defined here.
 *
 * @author Useless
 * @since beta.1
 */
public abstract class ServerSlotBase extends Slot {
	/**
	 * Foundational slot for all {@link org.useless.serverlibe.api.gui.ServerGuiBase ServerGuiBase} slots.
	 *
	 * @param inventory Inventory slot is attached to.
	 * @param id Index of stack array slot is attached to.
	 */
	public ServerSlotBase(Inventory inventory, int id) {
		super(inventory, id, 0, 0); // Position doesn't matter since this is server only
	}


	/**
	 * This method is called when ever a client sends a {@link org.useless.serverlibe.api.event.player.inventory.InventoryClickEvent InventoryClickEvent} on this slot.
	 *
	 * @param clickEvent Click event from client.
	 *
	 * @author Useless
	 * @since beta.1
	 */
	public void onInteract(InventoryClickEvent clickEvent){

	}

	public abstract boolean canPutStackInSlot(ItemStack itemstack);
}
