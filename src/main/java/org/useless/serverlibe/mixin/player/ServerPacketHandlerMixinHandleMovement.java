package org.useless.serverlibe.mixin.player;

import net.minecraft.core.net.packet.MovePlayerPacket;
import net.minecraft.server.entity.player.ServerPlayer;
import net.minecraft.server.net.handler.ServerPacketHandler;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.useless.serverlibe.api.event.player.PlayerMovementEvent;

@Mixin(value = ServerPacketHandler.class, remap = false)
public class ServerPacketHandlerMixinHandleMovement {
	@Shadow
	private ServerPlayer playerEntity;

    @Shadow
	private double lastPosX;

	@Shadow
	private double lastPosY;

	@Shadow
	private double lastPosZ;

	@Inject
		(
			method = "Lnet/minecraft/server/net/handler/ServerPacketHandler;handleFlying(Lnet/minecraft/core/net/packet/MovePlayerPacket;)V",
			at = @At(
				"HEAD"
			),
			cancellable = true
		)
	public void serverlibe$handleMovementBefore(@NotNull final MovePlayerPacket packet, @NotNull final CallbackInfo ci){
		final double distanceMoved = Math.sqrt(
			Math.pow(
				packet.x - lastPosX, 2) +
				Math.pow(packet.y - lastPosY, 2) +
				Math.pow(packet.z - lastPosZ, 2));

		final PlayerMovementEvent playerMovementEvent = PlayerMovementEvent.getEventContainer().runMethods(new PlayerMovementEvent(
			playerEntity,
			playerEntity.world,
			packet.x,
			packet.y,
			packet.z,
			distanceMoved,
			packet.yaw,
			packet.pitch,
			packet.onGround,
			packet.hasPosition,
			packet.hasRotation));

		if (playerMovementEvent.isCancelled()) ci.cancel();
	}
}
