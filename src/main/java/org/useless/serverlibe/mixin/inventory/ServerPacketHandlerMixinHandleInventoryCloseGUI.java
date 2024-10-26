package org.useless.serverlibe.mixin.inventory;

import net.minecraft.core.net.packet.ContainerClosePacket;
import net.minecraft.server.entity.player.ServerPlayer;
import net.minecraft.server.net.handler.ServerPacketHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.useless.serverlibe.api.event.player.inventory.InventoryCloseEvent;

@Mixin(value = ServerPacketHandler.class, remap = false)
public class ServerPacketHandlerMixinHandleInventoryCloseGUI {
	@Shadow
	private ServerPlayer playerEntity;

	@Inject(method = "handleCloseWindow", at = @At("TAIL"))
	private void serverlibe$onGUIClose(ContainerClosePacket packet, CallbackInfo ci){
		InventoryCloseEvent.getEventContainer().runMethods(new InventoryCloseEvent(playerEntity, packet.windowId));
	}
}
