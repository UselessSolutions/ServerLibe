package org.useless.serverlibe.api.gui.slot;

import net.minecraft.core.item.ItemStack;
import net.minecraft.core.player.inventory.IInventory;
import org.jetbrains.annotations.Nullable;

/**
 * Simple slot that forbids client interaction.
 *
 * @author Useless
 * @since beta.1
 */
public class ServerSlotDisplay extends ServerSlotBase{
	public ServerSlotDisplay(IInventory inventory, int id){
		this(null, inventory, id);
	}

	/**
	 * @param icon Itemstack to display to the client, shows nothing when {@code null}
	 * @param inventory Inventory slot is attached to.
	 * @param id Index of stack array slot is attached to.
	 *
	 * @author Useless
	 * @since beta.1
	 */
	public ServerSlotDisplay(@Nullable ItemStack icon, IInventory inventory, int id) {
		super(inventory, id);
		inventory.setInventorySlotContents(id, icon);
	}

	/**
	 * Defines if item interaction are allowed. This controls if you can move the stack in the slot out of the slot.
	 */
	@Override
	public boolean allowItemInteraction() {
		return false;
	}

	/**
	 * Defines slot behavior for attempting to insert items into a slot.
	 *
	 * @param itemstack Itemstack to attempt to insert.
	 * @return True if stack is allowed, false if not allowed.
	 *
	 * @author Useless
	 * @since beta.1
	 */
	@Override
	public boolean canPutStackInSlot(ItemStack itemstack) {
		return false;
	}
}
