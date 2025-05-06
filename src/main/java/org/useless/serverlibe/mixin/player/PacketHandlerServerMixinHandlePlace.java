package org.useless.serverlibe.mixin.player;

import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.World;
import net.minecraft.server.net.handler.PacketHandlerServer;
import net.minecraft.server.world.ServerPlayerController;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.useless.serverlibe.api.event.player.PlayerItemPlaceEvent;

@Mixin(value = PacketHandlerServer.class, remap = false)
public class PacketHandlerServerMixinHandlePlace {

    @Redirect
		(
		method = "Lnet/minecraft/server/net/handler/PacketHandlerServer;handlePlace(Lnet/minecraft/core/net/packet/PacketUseItem;)V",
		at = @At
			(
			value = "INVOKE",
			target = "Lnet/minecraft/server/world/ServerPlayerController;useItemOn(Lnet/minecraft/core/entity/player/Player;Lnet/minecraft/core/world/World;Lnet/minecraft/core/item/ItemStack;IIILnet/minecraft/core/util/helper/Side;DD)Z"
			)
		)
	public boolean serverlibe$onBlockPlaced
		(
		@NotNull final ServerPlayerController controller,
		@NotNull final Player playerEntity,
		@NotNull final World world,
		@Nullable final ItemStack itemstack,
		final int x,
		final int y,
		final int z,
		@NotNull final Side side,
		final double xPlaced,
		final double yPlaced
		)
	{
		final PlayerItemPlaceEvent playerItemPlaceEvent = PlayerItemPlaceEvent.getEventContainer().runMethods(new PlayerItemPlaceEvent(playerEntity, world, itemstack, x, y, z, side, xPlaced, yPlaced));


		final boolean returnVal;
		if (!playerItemPlaceEvent.isCancelled()){
			returnVal = controller.useItemOn(playerEntity, world, itemstack, x, y, z, side, xPlaced, yPlaced);
		} else {
			returnVal = false;
		}

		return returnVal;
	}
}
