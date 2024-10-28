package org.useless.serverlibe.api.gui.slot;

import net.minecraft.core.InventoryAction;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.player.inventory.container.Container;
import org.jetbrains.annotations.NotNull;
import org.useless.serverlibe.api.event.player.inventory.InventoryClickEvent;

import java.util.Objects;

/**
 * Simple slot that runs an action on client clicking.
 *
 * @author Useless
 * @since beta.1
 */
public class ServerSlotButton extends ServerSlotDisplay{
	@NotNull
	public final Runnable action;

	/**
	 * Simple slot that runs an action on client clicking.
	 *
	 * @param icon Itemstack to display to the client, shows nothing when {@code null}
	 * @param container Container slot is attached to.
	 * @param id Index of stack array slot is attached to.
	 * @param action {@link Runnable} to call when clicked by client.
	 *
	 * @author Useless
	 * @since beta.1
	 */
	public ServerSlotButton(ItemStack icon, Container container, int id, @NotNull Runnable action) {
		super(icon, container, id);
		this.action = Objects.requireNonNull(action);
	}
	@Override
	public void onInteract(InventoryClickEvent clickEvent){
		if (clickEvent.action == InventoryAction.CLICK_LEFT){
			action.run();
		}
	}
}
