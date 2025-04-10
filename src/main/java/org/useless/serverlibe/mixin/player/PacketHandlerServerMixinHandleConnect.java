package org.useless.serverlibe.mixin.player;

import net.minecraft.server.entity.player.PlayerServer;
import net.minecraft.server.net.handler.PacketHandlerServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.useless.serverlibe.api.event.player.PlayerConnectEvent;

@Mixin(value = PacketHandlerServer.class, remap = false)
public class PacketHandlerServerMixinHandleConnect {
	@Shadow
	private PlayerServer playerEntity;

	@Inject(
		method = "handleSendInitialPlayerList",
		at = @At("TAIL")
	)
	private void betterpermissions$onPlayerConnect(CallbackInfo ci) {
		PlayerConnectEvent event = new PlayerConnectEvent(this.playerEntity);
		PlayerConnectEvent.getEventContainer().runMethods(event);
	}
}
