package org.useless.serverlibe.mixin.inventory;

import net.minecraft.core.net.packet.Packet;
import net.minecraft.core.net.packet.Packet100OpenWindow;
import net.minecraft.server.entity.player.ServerPlayer;
import net.minecraft.server.net.handler.ServerPacketHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.useless.serverlibe.api.event.player.inventory.InventoryServerOpenEvent;

@Mixin(value = ServerPacketHandler.class, remap = false)
public class ServerPacketHandlerMixinHandleInventoryOpenGUI {
	@Shadow
	private ServerPlayer playerEntity;

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
