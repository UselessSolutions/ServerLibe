package org.useless.serverlibe.mixin.player;

import net.minecraft.server.entity.player.ServerPlayer;
import net.minecraft.server.net.PlayerList;
import net.minecraft.server.net.handler.ServerPacketHandler;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.useless.serverlibe.api.event.player.PlayerChatEvent;

@Mixin(value = ServerPacketHandler.class, remap = false)
public class ServerPacketHandlerMixinHandleChat {
	@Shadow
	private ServerPlayer playerEntity;

	@Redirect
		(
			method = "handleChat(Lnet/minecraft/core/net/packet/Packet3Chat;)V",
			at = @At
				(
					value = "INVOKE",
					target = "Lnet/minecraft/server/net/PlayerList;sendEncryptedChatToAllPlayers(Ljava/lang/String;)V"
				)
		)
	private void serverlibe$onPlayerChat
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
