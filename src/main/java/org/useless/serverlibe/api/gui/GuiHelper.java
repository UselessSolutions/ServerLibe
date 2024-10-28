package org.useless.serverlibe.api.gui;

import net.minecraft.core.item.ItemStack;
import net.minecraft.core.net.packet.ContainerOpenPacket;
import net.minecraft.server.entity.player.ServerPlayer;
import org.useless.serverlibe.mixin.accessors.ServerPlayerAccessor;

import java.util.ArrayList;

public class GuiHelper {
	/**
	 * Sends a {@link ContainerOpenPacket} to open the generic container GUI on the client.
	 * While this GUI is its interaction rules will be governed by the {@link ServerGuiBase} on the server.
	 *
	 * @param player {@link net.minecraft.server.entity.player.ServerPlayer; Player} to make open the GUI.
	 * @param serverGui Custom {@link ServerGuiBase server gui} to make the player open.
	 *
	 * @since beta.1
	 * @author Useless
	 */
	public static void openCustomServerGui(ServerPlayer player, ServerGuiBase serverGui){
		ServerPlayerAccessor accessor = (ServerPlayerAccessor)player;
		accessor.serverlibe$getNextWindowId();
		player.playerNetServerHandler.sendPacket(new ContainerOpenPacket(accessor.serverlibe$getCurrentWindowId(), 0, serverGui.inventoryTitle, serverGui.slotsCount));
		player.craftingInventory = serverGui;
		player.craftingInventory.containerId = accessor.serverlibe$getCurrentWindowId();
		player.craftingInventory.addSlotListener(player);
	}

	/**
	 * Sends the appropriate BTA packets to tell a client what the state of
	 * their inventory is. Some cancel actions, especially GUI related ones, can cause
	 * the server and client's view of the inventory to de-sync, calling this re-establishes
	 * sync and should hopefully prevent any confusion on the client's end.
	 *
	 * @param player The player to re-sync the inventory of.
	 *
	 * @since beta.1
	 * @author Useless
	 */
	public static void syncInventory(ServerPlayer player){
		ArrayList<ItemStack> arraylist = new ArrayList<>();
		for (int i = 0; i < player.craftingInventory.slots.size(); ++i) {
			arraylist.add(player.craftingInventory.slots.get(i).getItem());
		}
		player.updateCraftingInventory(player.craftingInventory, arraylist);
	}
}
