package org.useless.serverlibe.mixin.player;

import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;
import net.minecraft.server.net.handler.PacketHandlerServer;
import net.minecraft.server.world.ServerPlayerController;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.useless.serverlibe.api.event.player.PlayerItemUseEvent;

@Mixin(value = PacketHandlerServer.class, remap = false)
public class PacketHandlerServerMixinItemUse {
	@Redirect
		(
			method = "handlePlace",
			at = @At
				(
					value = "INVOKE",
					target = "Lnet/minecraft/server/world/ServerPlayerController;useItemOnNothing(Lnet/minecraft/core/entity/player/Player;Lnet/minecraft/core/world/World;Lnet/minecraft/core/item/ItemStack;)Z"
				)
		)
	public boolean serverlibe$onItemRightClick(ServerPlayerController instance, Player entityplayer, World world, ItemStack itemstack){
		PlayerItemUseEvent itemUseEvent = PlayerItemUseEvent.getEventContainer().runMethods(new PlayerItemUseEvent(entityplayer, world, itemstack));

		final boolean returnVal;
		if (!itemUseEvent.isCancelled()){
			returnVal = instance.useItemOnNothing(entityplayer, world, itemstack);
		} else {
			returnVal = false;
		}

		return returnVal;
	}

}
