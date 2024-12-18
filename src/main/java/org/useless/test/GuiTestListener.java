package org.useless.test;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.Items;
import net.minecraft.core.player.gamemode.Gamemode;
import net.minecraft.server.entity.player.PlayerServer;
import org.useless.serverlibe.api.Listener;
import org.useless.serverlibe.api.annotations.EventListener;
import org.useless.serverlibe.api.event.player.PlayerItemUseEvent;
import org.useless.serverlibe.api.gui.GuiHelper;
import org.useless.serverlibe.api.gui.ServerGuiBase;
import org.useless.serverlibe.api.gui.slot.ServerSlotButton;
import org.useless.serverlibe.api.gui.ServerGuiBuilder;

public class GuiTestListener implements Listener {
	@EventListener
	public void openCustomGui(PlayerItemUseEvent useEvent){
		if (useEvent.itemstack.getItem().equals(Items.BOOK) && useEvent.itemstack.hasCustomName() && useEvent.itemstack.getCustomName().equalsIgnoreCase("gamemode book")){
			GuiHelper.openCustomServerGui((PlayerServer) useEvent.player,
				new ServerGuiBuilder()
					.setContainerSlot(0, (inventory -> {
						ItemStack survivalIcon = Items.TOOL_AXE_STONE.getDefaultStack();
						survivalIcon.setCustomName("Survival Mode");
						return new ServerSlotButton(survivalIcon, inventory, 0, () -> useEvent.player.setGamemode(Gamemode.survival));
					}))
					.setContainerSlot(1, (inventory -> {
						ItemStack creativeIcon = Blocks.BEDROCK.getDefaultStack();
						creativeIcon.setCustomName("Creative Mode");
						return new ServerSlotButton(creativeIcon, inventory, 1, () -> useEvent.player.setGamemode(Gamemode.creative));
					}))
					.setContainerSlot(8, (i) -> {
						ItemStack nextMenuIcon = Items.BOOK.getDefaultStack();
						nextMenuIcon.setCustomName("Sub Gui");
						return new ServerSlotButton(nextMenuIcon,i, 8, () -> {
							GuiHelper.openCustomServerGui((PlayerServer) useEvent.player, new ServerGuiBase(useEvent.player, "Test", 2));
						});
					})
					.build(useEvent.player, "Gamemode Menu"));
			useEvent.setCancelled(true);
		}
    }
}
