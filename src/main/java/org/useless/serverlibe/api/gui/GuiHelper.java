package org.useless.serverlibe.api.gui;

import net.minecraft.core.item.ItemStack;
import net.minecraft.core.net.packet.Packet100OpenWindow;
import net.minecraft.server.entity.player.EntityPlayerMP;
import org.useless.serverlibe.mixin.accessors.EntityPlayerMPAccessor;

import java.util.ArrayList;

public class GuiHelper {
	public static void openCustomServerGui(EntityPlayerMP player, ServerGuiBase guiBase){
		EntityPlayerMPAccessor accessor = (EntityPlayerMPAccessor)player;
		accessor.serverlibe$getNextWindowId();
		player.playerNetServerHandler.sendPacket(new Packet100OpenWindow(accessor.serverlibe$getCurrentWindowId(), 0, guiBase.inventoryTitle, guiBase.slotsCount));
		player.craftingInventory = guiBase;
		player.craftingInventory.windowId = accessor.serverlibe$getCurrentWindowId();
		player.craftingInventory.onContainerInit(player);
	}
	public static void resyncInventory(EntityPlayerMP player){
		ArrayList<ItemStack> arraylist = new ArrayList<>();
		for (int i = 0; i < player.craftingInventory.inventorySlots.size(); ++i) {
			arraylist.add(player.craftingInventory.inventorySlots.get(i).getStack());
		}
		player.updateCraftingInventory(player.craftingInventory, arraylist);
	}
}
