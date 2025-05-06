package org.useless.test;

import org.useless.serverlibe.api.Listener;
import org.useless.serverlibe.api.annotations.EventListener;
import org.useless.serverlibe.api.enums.Priority;
import org.useless.serverlibe.api.event.player.PlayerEntityInteractEvent;
import org.useless.serverlibe.api.event.player.PlayerItemUseEvent;
import org.useless.serverlibe.api.event.player.inventory.InventoryClickEvent;
import org.useless.serverlibe.api.event.player.inventory.InventoryCloseEvent;
import org.useless.serverlibe.api.event.player.inventory.InventoryServerOpenEvent;

import java.util.Arrays;

public class DebugInfoListener implements Listener {
	@EventListener(priority = Priority.HIGH)
	public void onItemUsed(PlayerItemUseEvent useEvent){
		useEvent.player.sendMessage("[ServerLibe] item right click");
	}
	@EventListener(priority = Priority.HIGH)
	public void onGuiClose(InventoryCloseEvent closeEvent){
		closeEvent.player.sendMessage("[ServerLibe] GUI closed");
	}
	@EventListener(priority = Priority.HIGH)
	public void onGuiOpen(InventoryServerOpenEvent openEvent){
		openEvent.player.sendMessage("[ServerLibe] GUI " + openEvent.windowTitle + " opened");
	}
	@EventListener(priority = Priority.HIGH)
	public void onGuiClick(InventoryClickEvent clickEvent){
		clickEvent.player.sendMessage(String.format("[ServerLibe] action: %s, args: %s, actionID: %s, Itemstack: %s", clickEvent.action, Arrays.toString(clickEvent.args), clickEvent.actionId, clickEvent.itemStack));
//		clickEvent.setCancelled(true);
	}
	@EventListener(priority = Priority.HIGH)
	public void onAttack(PlayerEntityInteractEvent interactEvent){
		interactEvent.player.sendMessage(String.format("[ServerLibe] target: %s, mouseButton: %d", interactEvent.targetEntity, interactEvent.mouseButton));
	}
}
