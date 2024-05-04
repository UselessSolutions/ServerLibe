package org.useless.test.gui;

import net.minecraft.core.block.Block;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.player.gamemode.Gamemode;
import net.minecraft.core.player.inventory.IInventory;
import org.jetbrains.annotations.NotNull;
import org.useless.serverlibe.api.event.player.inventory.InventoryClickEvent;
import org.useless.serverlibe.api.gui.ServerGuiBase;
import org.useless.serverlibe.api.gui.slot.ServerSlotBase;
import org.useless.serverlibe.api.gui.slot.ServerSlotButton;
import org.useless.serverlibe.api.gui.slot.ServerSlotDisplay;

import java.util.Objects;

public class GuiMenu extends ServerGuiBase {
	@NotNull
	public final EntityPlayer currentPlayer;
	public GuiMenu(@NotNull EntityPlayer player, String title) {
		super(player, title, 1);
		currentPlayer = Objects.requireNonNull(player);
	}
	@Override
	public ServerSlotBase getSlotForContainerInv(IInventory containerInventory, int id){
		if (id == 0){
			ItemStack survivalIcon = Item.toolAxeStone.getDefaultStack();
			survivalIcon.setCustomName("Survival Mode");
			return new ServerSlotButton(survivalIcon, containerInventory, id, () -> currentPlayer.setGamemode(Gamemode.survival));
		}
		if (id == 1){
			ItemStack creativeIcon = Block.bedrock.getDefaultStack();
			creativeIcon.setCustomName("Creative Mode");
			return new ServerSlotButton(creativeIcon, containerInventory, id, () -> currentPlayer.setGamemode(Gamemode.creative));
		}
		return new ServerSlotDisplay(containerInventory, id);
	}

	@Override
	public void onInventoryAction(InventoryClickEvent clickEvent){
		super.onInventoryAction(clickEvent);
	}
}
