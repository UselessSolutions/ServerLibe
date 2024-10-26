package org.useless.serverlibe.mixin.inventory;

import net.minecraft.core.net.packet.Packet101CloseWindow;
import net.minecraft.server.entity.player.ServerPlayer;
import net.minecraft.server.net.handler.NetServerHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.useless.serverlibe.api.event.player.inventory.InventoryCloseEvent;

@Mixin(value = NetServerHandler.class, remap = false)
public class NetServerHandlerMixinHandleInventoryCloseGUI {
	@Shadow
	private ServerPlayer playerEntity;

	@Inject(method = "handleCloseWindow", at = @At("TAIL"))
	private void serverlibe$onGUIClose(Packet101CloseWindow packet, CallbackInfo ci){
		InventoryCloseEvent.getEventContainer().runMethods(new InventoryCloseEvent(playerEntity, packet.windowId));
	}
}
