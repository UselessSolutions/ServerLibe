package org.useless.serverlibe.mixin.player;

import net.minecraft.core.net.packet.PacketDisconnect;
import net.minecraft.server.entity.player.PlayerServer;
import net.minecraft.server.net.handler.PacketHandlerServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.useless.serverlibe.api.event.player.PlayerDisconnectEvent;

@Mixin(value = PacketHandlerServer.class, remap = false)
public class PacketHandlerServerMixinHandleDisconnect {
	@Shadow
	private PlayerServer playerEntity;


	@Inject(
		method = "handleErrorMessage",
		at = @At("HEAD")
	)
	private void betterpermissions$onPlayerDisconnect(String message, Object[] objects, CallbackInfo ci) {
		PlayerDisconnectEvent event = new PlayerDisconnectEvent(this.playerEntity);
		PlayerDisconnectEvent.getEventContainer().runMethods(event);
	}
}
