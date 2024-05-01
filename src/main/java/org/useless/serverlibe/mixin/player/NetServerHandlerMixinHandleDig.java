package org.useless.serverlibe.mixin.player;

import net.minecraft.core.util.helper.Side;
import net.minecraft.server.entity.player.EntityPlayerMP;
import net.minecraft.server.net.handler.NetServerHandler;
import net.minecraft.server.world.ServerPlayerController;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.useless.serverlibe.callbacks.player.IPlayerDig;
import org.useless.serverlibe.callbacks.player.PlayerDigEvent;
import org.useless.serverlibe.data.EventId;
import org.useless.serverlibe.data.Order;
import org.useless.serverlibe.data.Priority;
import org.useless.serverlibe.internal.EventContainer;
import org.useless.serverlibe.internal.InternalStorageClass;

import java.util.List;

@Mixin(value = NetServerHandler.class, remap = false)
public class NetServerHandlerMixinHandleDig {
	@Shadow
	private EntityPlayerMP playerEntity;

	@Redirect
		(
			method = "handleBlockDig(Lnet/minecraft/core/net/packet/Packet14BlockDig;)V",
			at = @At
				(
					value = "INVOKE",
					target = "Lnet/minecraft/server/world/ServerPlayerController;startMining(IIILnet/minecraft/core/util/helper/Side;)V"
				)
		)
	public void serverlibe$onBlockStartMining
		(
			@NotNull final ServerPlayerController instance,
			final int x,
			final int y,
			final int z,
			@NotNull final Side side)
	{
		final PlayerDigEvent digEvent = new PlayerDigEvent(playerEntity, playerEntity.world, x, y, z, side);
		runEvents(digEvent, () -> instance.startMining(x, y, z, side));
	}

	@Redirect
		(
			method = "handleBlockDig(Lnet/minecraft/core/net/packet/Packet14BlockDig;)V",
			at = @At
				(
					value = "INVOKE",
					target = "Lnet/minecraft/server/world/ServerPlayerController;hitBlock(IIILnet/minecraft/core/util/helper/Side;)V"
				)
		)
	public void serverlibe$onBlockHit
		(
			@NotNull final ServerPlayerController instance,
			final int x,
			final int y,
			final int z,
			@NotNull final Side side)
	{
		final PlayerDigEvent digEvent = new PlayerDigEvent(playerEntity, playerEntity.world, x, y, z, side);
		runEvents(digEvent, () -> instance.hitBlock(x, y, z, side));
	}

	@Unique
	private void runEvents
		(
		@NotNull final PlayerDigEvent playerDigEvent,
		@NotNull final Runnable defaultAction
	){
		final EventContainer<IPlayerDig> digEventContainer = InternalStorageClass.getEventContainer(EventId.PLAYER_DIG_EVENT_ID);

		boolean cancelDefaultPlace = false;
		for (final Priority priority : Priority.values()){
			final List<IPlayerDig> digEvents = digEventContainer.getEvents(priority, Order.BEFORE);
			for (final IPlayerDig digEvent : digEvents){
				cancelDefaultPlace |= digEvent.onDigEvent(playerDigEvent);
			}
		}

        if (!cancelDefaultPlace){
			defaultAction.run();
		}

		for (final Priority priority : Priority.values()){
			final List<IPlayerDig> digEvents = digEventContainer.getEvents(priority, Order.AFTER);
			for (final IPlayerDig placeEvent : digEvents){
				placeEvent.onDigEvent(playerDigEvent);
			}
		}
	}
}
