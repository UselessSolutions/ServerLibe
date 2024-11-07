package org.useless.serverlibe.api.gui;

import net.minecraft.core.InventoryAction;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.player.inventory.container.Container;
import net.minecraft.core.player.inventory.container.ContainerSimple;
import net.minecraft.core.player.inventory.menu.MenuContainer;
import net.minecraft.core.player.inventory.slot.Slot;
import net.minecraft.server.entity.player.PlayerServer;
import org.jetbrains.annotations.NotNull;
import org.useless.serverlibe.api.event.player.inventory.InventoryClickEvent;
import org.useless.serverlibe.api.gui.slot.ServerSlotBase;
import org.useless.serverlibe.mixin.accessors.MenuContainerAccessor;

import java.util.List;

public class ServerGuiBase extends MenuContainer {
	public final String inventoryTitle;
	public final int slotsCount;
	public final Container container;

	public ServerGuiBase(Player player, String inventoryTitle, int rowCount) {
		super(player.inventory, new ContainerSimple(inventoryTitle, rowCount * 9));
		this.container = ((MenuContainerAccessor)this).getContainerInventory();
		this.inventoryTitle = inventoryTitle;
		this.slotsCount = rowCount * 9;

        slots.clear();
        for (int row = 0; row < rowCount; ++row) {
			for (int col = 0; col < 9; ++col) {
				this.addSlot(getSlotForContainerInv(container, col + row * 9));
			}
		}

		for (int row = 0; row < 3; ++row) {
			for (int col = 0; col < 9; ++col) {
				this.addSlot(getSlotForPlayerInv(player.inventory, col + row * 9 + 9));
			}
		}
		for (int col = 0; col < 9; ++col) {
			this.addSlot(getSlotForPlayerInv(player.inventory, col));
		}
	}

	public ServerSlotBase getSlotForContainerInv(Container containerInventory, int id) {
		return new ServerSlotBase(containerInventory, id);
	}

	public ServerSlotBase getSlotForPlayerInv(Container playerInventory, int id) {
		return new ServerSlotBase(playerInventory, id);
	}

	public void onInventoryAction(@NotNull InventoryClickEvent clickEvent){
		if (clickEvent.args.length > 0){
			int slotId = clickEvent.args[0];
			Slot slot = getSlot(slotId);
			if (slot instanceof ServerSlotBase){
				((ServerSlotBase)slot).onInteract(clickEvent);
			}
			if (!slot.allowItemInteraction()) {
				clickEvent.setCancelled(true);
			}
		}
	}
	@Override
	public List<Integer> getTargetSlots(InventoryAction action, Slot slot, int target, Player player) {
		List<Integer> targets = super.getTargetSlots(action, slot, target, player);
		Integer[] nums = targets.toArray(new Integer[0]);
		boolean needResync = false;
		for (int i : nums){
			if (!getSlot(i).mayPlace(slot.getItem())){ // Make it so you can't target non interaction slots
				targets.remove((Integer) i);
				needResync = true;
			}
		}
		if (needResync){
			resyncGUI(player);
		}
		return targets;
	}
	public void resyncGUI(Player player){
		if (player instanceof PlayerServer){
			GuiHelper.syncInventory((PlayerServer) player);
		}
	}

}
