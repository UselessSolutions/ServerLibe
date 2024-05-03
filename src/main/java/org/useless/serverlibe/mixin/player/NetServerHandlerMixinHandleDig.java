package org.useless.serverlibe.mixin.player;

import net.minecraft.core.net.packet.Packet53BlockChange;
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
import org.useless.serverlibe.callbacks.player.PlayerDigEvent;

import java.util.concurrent.atomic.AtomicBoolean;

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
		final PlayerDigEvent digEvent = new PlayerDigEvent(playerEntity, playerEntity.world, x, y, z, side, PlayerDigEvent.START_MINING);
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
		final PlayerDigEvent digEvent = new PlayerDigEvent(playerEntity, playerEntity.world, x, y, z, side, PlayerDigEvent.HIT_BLOCK);
		runEvents(digEvent, () -> instance.hitBlock(x, y, z, side));
	}
	@Redirect
		(
			method = "handleBlockDig(Lnet/minecraft/core/net/packet/Packet14BlockDig;)V",
			at = @At
				(
					value = "INVOKE",
					target = "Lnet/minecraft/server/world/ServerPlayerController;destroyBlock(III)Z"
				)

		)
	public boolean serverlibe$onBlockDestroy
		(
			@NotNull final ServerPlayerController instance,
			final int x,
			final int y,
			final int z
		)
	{
		AtomicBoolean blockBroken = new AtomicBoolean(false);
		final PlayerDigEvent digEvent = new PlayerDigEvent(playerEntity, playerEntity.world, x, y, z, Side.NONE, PlayerDigEvent.DESTROY_BLOCK);
		runEvents(digEvent, () -> blockBroken.set(blockBroken.get() | instance.destroyBlock(x, y, z)));

        return blockBroken.get();
    }

	@Unique
	private void runEvents
		(
		@NotNull final PlayerDigEvent playerDigEvent,
		@NotNull final Runnable defaultAction
	){
		PlayerDigEvent.getEventContainer().runMethods(playerDigEvent);

		if (playerDigEvent.isCancelled()) {
			// Restores the block on break if cancelled, this fixed the vanilla bug where instantly broken blocks don't reset
			this.playerEntity.playerNetServerHandler.sendPacket(new Packet53BlockChange(playerDigEvent.x, playerDigEvent.y, playerDigEvent.z, playerDigEvent.world));
		} else {
			defaultAction.run();
		}
	}
}
