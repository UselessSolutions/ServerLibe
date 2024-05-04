package org.useless.test;

import net.minecraft.core.item.Item;
import net.minecraft.server.entity.player.EntityPlayerMP;
import org.useless.serverlibe.api.Listener;
import org.useless.serverlibe.api.annotations.EventListener;
import org.useless.serverlibe.api.event.player.PlayerItemUseEvent;
import org.useless.serverlibe.api.gui.GuiHelper;
import org.useless.test.gui.GuiMenu;

public class GuiTestListener implements Listener {
	@EventListener
	public void openCustomGui(PlayerItemUseEvent useEvent){
		if (useEvent.itemstack.getItem() == Item.book && useEvent.itemstack.hasCustomName() && useEvent.itemstack.getCustomName().equalsIgnoreCase("gamemode book")){
			GuiHelper.openCustomServerGui((EntityPlayerMP) useEvent.player, new GuiMenu(useEvent.player, "Gamemode Select"));
			useEvent.setCancelled(true);
		}
    }
}
