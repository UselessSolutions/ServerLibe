package org.useless.serverlibe.mixin.player;

import net.minecraft.core.net.packet.BlockUpdatePacket;
import net.minecraft.core.util.helper.Side;
import net.minecraft.server.entity.player.ServerPlayer;
import net.minecraft.server.net.handler.ServerPacketHandler;
import net.minecraft.server.world.ServerPlayerController;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.useless.serverlibe.api.event.player.PlayerDigEvent;

import java.util.concurrent.atomic.AtomicBoolean;

@Mixin(value = ServerPacketHandler.class, remap = false)
public class ServerPacketHandlerMixinHandleDig {
	@Shadow
	private ServerPlayer playerEntity;

	@Redirect
		(
			method = "Lnet/minecraft/server/net/handler/ServerPacketHandler;handleBlockDig(Lnet/minecraft/core/net/packet/PlayerActionPacket;)V",
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
			method = "Lnet/minecraft/server/net/handler/ServerPacketHandler;handleBlockDig(Lnet/minecraft/core/net/packet/PlayerActionPacket;)V",
			at = @At
				(
					value = "INVOKE",
					target = "Lnet/minecraft/server/world/ServerPlayerController;hitBlock(IIILnet/minecraft/core/util/helper/Side;DD)V"
				)
		)
	public void serverlibe$onBlockHit
		(
			ServerPlayerController instance, int x, int y, int z, Side side, double xHit, double yHit)
	{
		final PlayerDigEvent digEvent = new PlayerDigEvent(playerEntity, playerEntity.world, x, y, z, side, PlayerDigEvent.HIT_BLOCK);
		runEvents(digEvent, () -> instance.hitBlock(x, y, z, side, xHit, yHit));
	}
	@Redirect
		(
			method = "Lnet/minecraft/server/net/handler/ServerPacketHandler;handleBlockDig(Lnet/minecraft/core/net/packet/PlayerActionPacket;)V",
			at = @At
				(
					value = "INVOKE",
					target = "Lnet/minecraft/server/world/ServerPlayerController;destroyBlock(IIILnet/minecraft/core/util/helper/Side;)Z"
				)

		)
	public boolean serverlibe$onBlockDestroy
		(
			ServerPlayerController instance, int x, int y, int z, Side side
		)
	{
		AtomicBoolean blockBroken = new AtomicBoolean(false);
		final PlayerDigEvent digEvent = new PlayerDigEvent(playerEntity, playerEntity.world, x, y, z, Side.NONE, PlayerDigEvent.DESTROY_BLOCK);
		runEvents(digEvent, () -> blockBroken.set(blockBroken.get() | instance.destroyBlock(x, y, z, side)));

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
			this.playerEntity.playerNetServerHandler.sendPacket(new BlockUpdatePacket(playerDigEvent.x, playerDigEvent.y, playerDigEvent.z, playerDigEvent.world));
		} else {
			defaultAction.run();
		}
	}
}
