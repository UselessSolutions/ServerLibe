package org.useless.serverlibe.mixin.player;

import net.minecraft.server.entity.player.EntityPlayerMP;
import net.minecraft.server.net.PlayerList;
import net.minecraft.server.net.handler.NetServerHandler;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.useless.serverlibe.api.event.player.PlayerChatEvent;

@Mixin(value = NetServerHandler.class, remap = false)
public class NetServerHandlerMixinHandleChat {
	@Shadow
	private EntityPlayerMP playerEntity;

	@Redirect
		(
			method = "handleChat(Lnet/minecraft/core/net/packet/Packet3Chat;)V",
			at = @At
				(
					value = "INVOKE",
					target = "Lnet/minecraft/server/net/PlayerList;sendEncryptedChatToAllPlayers(Ljava/lang/String;)V"
				)
		)
	private void onPlayerChat
		(
			@NotNull final PlayerList instance,
			@NotNull final String message
		)
	{
		final PlayerChatEvent playerChatEvent = PlayerChatEvent.getEventContainer().runMethods(new PlayerChatEvent(playerEntity, message));

		if (!playerChatEvent.isCancelled()){
			instance.sendEncryptedChatToAllPlayers(playerChatEvent.getMessage());
		}
	}
}
