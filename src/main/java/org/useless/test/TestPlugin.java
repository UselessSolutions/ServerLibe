package org.useless.test;

import net.minecraft.core.net.command.TextFormatting;
import net.minecraft.core.net.packet.Packet63SpawnParticleEffect;
import net.minecraft.server.entity.player.EntityPlayerMP;
import org.useless.serverlibe.api.Listener;
import org.useless.serverlibe.api.annotations.EventListener;
import org.useless.serverlibe.api.enums.Priority;
import org.useless.serverlibe.api.event.player.PlayerChatEvent;
import org.useless.serverlibe.api.event.player.PlayerDigEvent;
import org.useless.serverlibe.api.event.player.PlayerMovementEvent;
import org.useless.serverlibe.api.event.player.inventory.InventoryClickEvent;
import org.useless.serverlibe.api.event.player.inventory.InventoryCloseEvent;
import org.useless.serverlibe.api.event.player.inventory.InventoryServerOpenEvent;

import java.util.Arrays;

public class TestPlugin implements Listener {
	@EventListener
	public void playerTrail(PlayerMovementEvent movementEvent){
		if (movementEvent.distanceMoved < 0.05) return;
		final EntityPlayerMP playerMP = (EntityPlayerMP)movementEvent.player;
		final boolean movingQuick = movementEvent.distanceMoved > 0.7;
		final String particleKey = movingQuick ? "blueflame" : "flame";
		playerMP.playerNetServerHandler.sendPacket(new Packet63SpawnParticleEffect(particleKey, playerMP.x, playerMP.y, playerMP.z, 0, 0, 0));
	}
	@EventListener(priority = Priority.HIGH)
	public void disableBreak(PlayerDigEvent digEvent){
		if (digEvent.player.getHeldItem() != null && digEvent.player.getHeldItem().hasCustomName()) {
			digEvent.setCancelled(true);
		}
	}
	@EventListener(priority = Priority.LOW, ignoreCancelled = true)
	public void blockBreakEffect(PlayerDigEvent digEvent){
		final EntityPlayerMP playerMP = (EntityPlayerMP)digEvent.player;
		playerMP.playerNetServerHandler.sendPacket(new Packet63SpawnParticleEffect
			(
				"explode",
				digEvent.x + 0.5, digEvent.y + 0.5, digEvent.z + 0.5,
				0, 0, 0, 16, (byte) 8,
				0, 0, 0,
				0, 0, 0)
		);
	}
	@EventListener
	public void modifyChat(PlayerChatEvent chatEvent){
		final String seperator = "> " + TextFormatting.WHITE;
		final float greenPercent = 0.5f;
		String[] vals = chatEvent.getMessage().split(seperator);
		if (vals.length < 2) return;
		final int greenChars = (int) (vals[1].length() * greenPercent);
		String payload = TextFormatting.LIME + vals[1].substring(0, greenChars/2) + TextFormatting.RED + vals[1].substring(greenChars/2, vals[1].length() - (greenChars/2)) + TextFormatting.LIME + vals[1].substring(vals[1].length() - (greenChars/2));
		chatEvent.setMessage(vals[0] + "> " + payload);
	}
//	@EventListener(priority = Priority.HIGH, ignoreCancelled = true)
//	public void useSaddleSpecial(PlayerPlaceEvent placeEvent){
//		ItemStack heldItem = placeEvent.itemstack;
//		if (heldItem != null && heldItem.hasCustomName() && heldItem.getCustomName().equals("Wrangler")){
//			placeEvent.
//			placeEvent.setCancelled(true);
//		}
//	}
	@EventListener
	public void onGuiClose(InventoryCloseEvent closeEvent){
		closeEvent.player.addChatMessage("[ServerLibe] GUI closed");
	}
	@EventListener
	public void onGuiOpen(InventoryServerOpenEvent openEvent){
		if (openEvent.windowTitle.equals("Crafting")){
			openEvent.player.addChatMessage("[ServerLibe] you are not allowed to open workbenches on this server");
			openEvent.setCancelled(true);
		}
		if (!openEvent.isCancelled()){
			openEvent.player.addChatMessage("[ServerLibe] GUI " + openEvent.windowTitle + " opened");
		}
	}
	@EventListener
	public void onGuiClick(InventoryClickEvent clickEvent){
		clickEvent.player.addChatMessage(String.format("[ServerLibe] action: %s, args: %s, actionID: %s, Itemstack: %s", clickEvent.action, Arrays.toString(clickEvent.args), clickEvent.actionId, clickEvent.itemStack));
		clickEvent.setCancelled(true);
	}
}
