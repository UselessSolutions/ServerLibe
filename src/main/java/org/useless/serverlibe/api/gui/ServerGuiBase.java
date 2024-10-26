package org.useless.serverlibe.api.gui;

import net.minecraft.core.InventoryAction;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.player.inventory.ContainerChest;
import net.minecraft.core.player.inventory.IInventory;
import net.minecraft.core.player.inventory.InventoryBasic;
import net.minecraft.core.player.inventory.container.Inventory;
import net.minecraft.core.player.inventory.slot.Slot;
import net.minecraft.server.entity.player.ServerPlayer;
import org.jetbrains.annotations.NotNull;
import org.useless.serverlibe.api.event.player.inventory.InventoryClickEvent;
import org.useless.serverlibe.api.gui.slot.ServerSlotBase;
import org.useless.serverlibe.mixin.accessors.ContainerChestAccessor;

import java.awt.*;
import java.util.List;

public class ServerGuiBase extends Container {
	public final String inventoryTitle;
	public final int slotsCount;
	public final Inventory inventory;

	public ServerGuiBase(Player player, String inventoryTitle, int rowCount) {
		super(player.inventory, new InventoryBasic(inventoryTitle, rowCount * 9));
		this.inventory = ((ContainerChestAccessor)this).getContainerInventory();
		this.inventoryTitle = inventoryTitle;
		this.slotsCount = rowCount * 9;

        inventorySlots.clear();
        for (int row = 0; row < rowCount; ++row) {
			for (int col = 0; col < 9; ++col) {
				this.addSlot(getSlotForContainerInv(inventory, col + row * 9));
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
	public ServerSlotBase getSlotForContainerInv(IInventory containerInventory, int id){
		return new ServerSlotBase(containerInventory, id);
	}
	public ServerSlotBase getSlotForPlayerInv(IInventory playerInventory, int id){
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
			if (!getSlot(i).canPutStackInSlot(slot.getStack())){ // Make it so you can't target non interaction slots
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
		if (player instanceof ServerPlayer){
			GuiHelper.syncInventory((ServerPlayer) player);
		}
	}

}
