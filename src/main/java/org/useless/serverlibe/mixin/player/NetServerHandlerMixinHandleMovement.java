package org.useless.serverlibe.mixin.player;

import net.minecraft.core.net.packet.Packet10Flying;
import net.minecraft.server.entity.player.EntityPlayerMP;
import net.minecraft.server.net.handler.NetServerHandler;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.useless.serverlibe.callbacks.player.IPlayerMovement;
import org.useless.serverlibe.callbacks.player.PlayerMovementEvent;
import org.useless.serverlibe.data.EventId;
import org.useless.serverlibe.data.Order;
import org.useless.serverlibe.data.Priority;
import org.useless.serverlibe.internal.EventContainer;
import org.useless.serverlibe.internal.InternalStorageClass;

import java.util.List;

@Mixin(value = NetServerHandler.class, remap = false)
public class NetServerHandlerMixinHandleMovement {
	@Shadow
	private EntityPlayerMP playerEntity;

	@Inject
		(
			method = "handleFlying(Lnet/minecraft/core/net/packet/Packet10Flying;)V",
			at = @At(
				value = "INVOKE",
				target = "Lnet/minecraft/core/world/IVehicle;positionRider()V",
				shift = At.Shift.BEFORE
			),
			cancellable = true
		)
	public void serverlibe$handleMovementBefore(@NotNull final Packet10Flying packet, @NotNull final CallbackInfo ci){
		final PlayerMovementEvent playerMovementEvent = new PlayerMovementEvent(
			playerEntity,
			playerEntity.world,
			packet.xPosition,
			packet.yPosition,
			packet.zPosition,
			packet.stance,
			packet.yaw,
			packet.pitch,
			packet.onGround,
			packet.moving,
			packet.rotating);

		final EventContainer<IPlayerMovement> moveEventContainer = InternalStorageClass.getEventContainer(EventId.PLAYER_MOVE_EVENT_ID);

		boolean cancelDefaultAction = false;
		for (final Priority priority : Priority.values()){
			final List<IPlayerMovement> moveEvents = moveEventContainer.getEvents(priority, Order.BEFORE);
			for (final IPlayerMovement moveEvent : moveEvents){
				cancelDefaultAction |= moveEvent.onPlayerMoved(playerMovementEvent);
			}
		}

		if (cancelDefaultAction) ci.cancel();
	}
	@Inject
		(
			method = "handleFlying(Lnet/minecraft/core/net/packet/Packet10Flying;)V",
			at = @At(
				value = "INVOKE",
				target = "Lnet/minecraft/server/entity/player/EntityPlayerMP;handleFalling(DZ)V",
				shift = At.Shift.AFTER
			)
		)
	public void serverlibe$handleMovementAfter(@NotNull final Packet10Flying packet, @NotNull final CallbackInfo ci){
		final PlayerMovementEvent playerMovementEvent = new PlayerMovementEvent(
			playerEntity,
			playerEntity.world,
			packet.xPosition,
			packet.yPosition,
			packet.zPosition,
			packet.stance,
			packet.yaw,
			packet.pitch,
			packet.onGround,
			packet.moving,
			packet.rotating);

		final EventContainer<IPlayerMovement> moveEventContainer = InternalStorageClass.getEventContainer(EventId.PLAYER_MOVE_EVENT_ID);

		for (final Priority priority : Priority.values()){
			final List<IPlayerMovement> moveEvents = moveEventContainer.getEvents(priority, Order.AFTER);
			for (final IPlayerMovement moveEvent : moveEvents){
				moveEvent.onPlayerMoved(playerMovementEvent);
			}
		}

	}
}
