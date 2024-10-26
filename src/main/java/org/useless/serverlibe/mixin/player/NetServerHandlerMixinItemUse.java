package org.useless.serverlibe.mixin.player;

import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;
import net.minecraft.server.net.handler.ServerPacketHandler;
import net.minecraft.server.world.ServerPlayerController;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.useless.serverlibe.api.event.player.PlayerItemUseEvent;

@Mixin(value = ServerPacketHandler.class, remap = false)
public class ServerPacketHandlerMixinItemUse {
	@Redirect
		(
			method = "handlePlace",
			at = @At
				(
					value = "INVOKE",
					target = "Lnet/minecraft/server/world/ServerPlayerController;func_6154_a(Lnet/minecraft/core/entity/player/Player;Lnet/minecraft/core/world/World;Lnet/minecraft/core/item/ItemStack;)Z"
				)
		)
	public boolean serverlibe$onItemRightClick(ServerPlayerController instance, Player entityplayer, World world, ItemStack itemstack){
		PlayerItemUseEvent itemUseEvent = PlayerItemUseEvent.getEventContainer().runMethods(new PlayerItemUseEvent(entityplayer, world, itemstack));

		final boolean returnVal;
		if (!itemUseEvent.isCancelled()){
			returnVal = instance.func_6154_a(entityplayer, world, itemstack);
		} else {
			returnVal = false;
		}

		return returnVal;
	}

}
