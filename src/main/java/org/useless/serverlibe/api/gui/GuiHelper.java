package org.useless.serverlibe.api.gui;

import net.minecraft.core.item.ItemStack;
import net.minecraft.core.net.packet.PacketContainerOpen;
import net.minecraft.core.net.packet.PacketContainerSetSlot;
import net.minecraft.server.entity.player.PlayerServer;
import org.useless.serverlibe.mixin.accessors.PlayerServerAccessor;

import java.util.ArrayList;

public class GuiHelper {
	/**
	 * Sends a {@link PacketContainerOpen} to open the generic container GUI on the client.
	 * While this GUI is its interaction rules will be governed by the {@link ServerGuiBase} on the server.
	 *
	 * @param playerServer {@link net.minecraft.server.entity.player.PlayerServer; Player} to make open the GUI.
	 * @param serverGui Custom {@link ServerGuiBase server gui} to make the playerServer open.
	 *
	 * @since beta.1
	 * @author Useless
	 */
	public static void openCustomServerGui(PlayerServer playerServer, ServerGuiBase serverGui){
		PlayerServerAccessor accessor = (PlayerServerAccessor)playerServer;
		accessor.serverlibe$getNextWindowId();
		playerServer.playerNetServerHandler.sendPacket(new PacketContainerOpen(accessor.serverlibe$getCurrentWindowId(), PacketContainerOpen.TYPE_GENERIC_CONTAINER, serverGui.inventoryTitle, serverGui.slotsCount));
		playerServer.craftingInventory = serverGui;
		playerServer.craftingInventory.containerId = accessor.serverlibe$getCurrentWindowId();
		playerServer.craftingInventory.addSlotListener(playerServer);
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
	public static void syncInventory(PlayerServer player){
		ArrayList<ItemStack> arraylist = new ArrayList<>();
		for (int i = 0; i < player.craftingInventory.slots.size(); ++i) {
			arraylist.add(player.craftingInventory.slots.get(i).getItemStack());
		}
		player.updateCraftingInventory(player.craftingInventory, arraylist);
		player.playerNetServerHandler.sendPacket(new PacketContainerSetSlot(-1, 0, player.inventory.getHeldItemStack()));
	}
}
