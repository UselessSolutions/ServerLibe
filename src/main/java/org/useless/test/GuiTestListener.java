package org.useless.test;

import net.minecraft.core.block.Block;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.player.gamemode.Gamemode;
import net.minecraft.server.entity.player.ServerPlayer;
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
		if (useEvent.itemstack.getItem() == Item.book && useEvent.itemstack.hasCustomName() && useEvent.itemstack.getCustomName().equalsIgnoreCase("gamemode book")){
			GuiHelper.openCustomServerGui((ServerPlayer) useEvent.player,
				new ServerGuiBuilder()
					.setContainerSlot(0, (inventory -> {
						ItemStack survivalIcon = Item.toolAxeStone.getDefaultStack();
						survivalIcon.setCustomName("Survival Mode");
						return new ServerSlotButton(survivalIcon, inventory, 0, () -> useEvent.player.setGamemode(Gamemode.survival));
					}))
					.setContainerSlot(1, (inventory -> {
						ItemStack creativeIcon = Block.bedrock.getDefaultStack();
						creativeIcon.setCustomName("Creative Mode");
						return new ServerSlotButton(creativeIcon, inventory, 1, () -> useEvent.player.setGamemode(Gamemode.creative));
					}))
					.setContainerSlot(8, (i) -> {
						ItemStack nextMenuIcon = Item.book.getDefaultStack();
						nextMenuIcon.setCustomName("Sub Gui");
						return new ServerSlotButton(nextMenuIcon,i, 8, () -> {
							GuiHelper.openCustomServerGui((ServerPlayer) useEvent.player, new ServerGuiBase(useEvent.player, "Test", 2));
						});
					})
					.build(useEvent.player, "Gamemode Menu"));
			useEvent.setCancelled(true);
		}
    }
}
