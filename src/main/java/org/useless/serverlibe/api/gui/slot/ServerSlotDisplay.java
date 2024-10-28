package org.useless.serverlibe.api.gui.slot;

import net.minecraft.core.item.ItemStack;
import net.minecraft.core.player.inventory.container.Container;
import org.jetbrains.annotations.Nullable;

/**
 * Simple slot that forbids client interaction.
 *
 * @author Useless
 * @since beta.1
 */
public class ServerSlotDisplay extends ServerSlotBase{
	public ServerSlotDisplay(Container container, int id){
		this(null, container, id);
	}

	/**
	 * @param icon Itemstack to display to the client, shows nothing when {@code null}
	 * @param container Container slot is attached to.
	 * @param id Index of stack array slot is attached to.
	 *
	 * @author Useless
	 * @since beta.1
	 */
	public ServerSlotDisplay(@Nullable ItemStack icon, Container container, int id) {
		super(container, id);
		container.setItem(id, icon);
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
	public boolean canPutStackInSlot(ItemStack itemstack) {
		return false;
	}
}
