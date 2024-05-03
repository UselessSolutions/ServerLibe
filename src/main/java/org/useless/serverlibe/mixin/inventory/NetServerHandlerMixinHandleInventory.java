package org.useless.serverlibe.mixin.inventory;

import net.minecraft.core.net.packet.Packet;
import net.minecraft.core.net.packet.Packet100OpenWindow;
import net.minecraft.core.net.packet.Packet101CloseWindow;
import net.minecraft.server.entity.player.EntityPlayerMP;
import net.minecraft.server.net.handler.NetServerHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.useless.serverlibe.api.event.player.inventory.InventoryCloseEvent;
import org.useless.serverlibe.api.event.player.inventory.InventoryServerOpenEvent;

@Mixin(value = NetServerHandler.class, remap = false)
public class NetServerHandlerMixinHandleInventory {
	@Shadow
	private EntityPlayerMP playerEntity;

	@Inject(method = "handleCloseWindow", at = @At("TAIL"))
	private void serverlibe$onGUIClose(Packet101CloseWindow packet, CallbackInfo ci){
		InventoryCloseEvent.getEventContainer().runMethods(new InventoryCloseEvent(playerEntity, packet.windowId));
	}
	@Inject(method = "sendPacket(Lnet/minecraft/core/net/packet/Packet;)V", at = @At("HEAD"), cancellable = true)
	private void serveribe$onGUIOpen(Packet packet, CallbackInfo ci){
		if (packet instanceof Packet100OpenWindow){
			Packet100OpenWindow openWindow = (Packet100OpenWindow)packet;
			InventoryServerOpenEvent openEvent = InventoryServerOpenEvent.getEventContainer().runMethods(new InventoryServerOpenEvent
				(
					playerEntity,
					openWindow.windowId,
					openWindow.inventoryType,
					openWindow.windowTitle,
					openWindow.slotsCount
				)
			);
			if (openEvent.isCancelled()) ci.cancel();
		}
	}
}
