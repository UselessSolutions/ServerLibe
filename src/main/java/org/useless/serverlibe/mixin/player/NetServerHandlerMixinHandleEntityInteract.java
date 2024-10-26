package org.useless.serverlibe.mixin.player;

import net.minecraft.core.entity.Entity;
import net.minecraft.core.net.packet.Packet7UseEntity;
import net.minecraft.server.entity.player.ServerPlayer;
import net.minecraft.server.net.handler.ServerPacketHandler;
import net.minecraft.server.world.WorldServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import org.useless.serverlibe.api.event.player.PlayerEntityInteractEvent;

@Mixin(value = ServerPacketHandler.class, remap = false)
public class ServerPacketHandlerMixinHandleEntityInteract {
	@Shadow
	private ServerPlayer playerEntity;

	@Inject
		(
			method = "handleUseEntity",
			at = @At
				(
					value = "INVOKE",
					target = "Lnet/minecraft/server/world/WorldServer;getEntityFromId(I)Lnet/minecraft/core/entity/Entity;",
					shift = At.Shift.BEFORE
				),
			locals = LocalCapture.CAPTURE_FAILHARD,
			cancellable = true)
	public void serverlibe$EntityInteract(Packet7UseEntity packet, CallbackInfo ci, WorldServer worldserver){
		Entity target = worldserver.getEntityFromId(packet.targetEntity);
		if (target == null) {
			return;
		}
		PlayerEntityInteractEvent interactEvent = PlayerEntityInteractEvent.getEventContainer().runMethods(new PlayerEntityInteractEvent(playerEntity, worldserver, playerEntity.getHeldItem(), target, packet.isLeftClick));

		if (interactEvent.isCancelled()) ci.cancel();
	}
}
