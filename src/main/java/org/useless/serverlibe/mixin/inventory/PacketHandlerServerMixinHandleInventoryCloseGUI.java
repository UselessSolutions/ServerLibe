package org.useless.serverlibe.mixin.inventory;

import net.minecraft.core.net.packet.PacketContainerClose;
import net.minecraft.server.entity.player.PlayerServer;
import net.minecraft.server.net.handler.PacketHandlerServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.useless.serverlibe.api.event.player.inventory.InventoryCloseEvent;

@Mixin(value = PacketHandlerServer.class, remap = false)
public class PacketHandlerServerMixinHandleInventoryCloseGUI {
	@Shadow
	private PlayerServer playerEntity;

	@Inject(method = "handleCloseWindow", at = @At("TAIL"))
	private void serverlibe$onGUIClose(PacketContainerClose containerClosePacket, CallbackInfo ci){
		InventoryCloseEvent.getEventContainer().runMethods(new InventoryCloseEvent(playerEntity, containerClosePacket.windowId));
	}
}
