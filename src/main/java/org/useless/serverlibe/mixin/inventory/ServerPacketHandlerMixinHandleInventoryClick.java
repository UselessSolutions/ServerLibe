package org.useless.serverlibe.mixin.inventory;

import net.minecraft.core.net.packet.ContainerClickPacket;
import net.minecraft.server.entity.player.ServerPlayer;
import net.minecraft.server.net.handler.ServerPacketHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.useless.serverlibe.api.event.player.inventory.InventoryClickEvent;
import org.useless.serverlibe.api.gui.GuiHelper;

@Mixin(value = ServerPacketHandler.class, remap = false)
public class ServerPacketHandlerMixinHandleInventoryClick {
	@Shadow
	private ServerPlayer playerEntity;

	@Inject
		(
			method = "handleWindowClick",
			at = @At("HEAD"),
			cancellable = true)
	public void serverlibe$onInventoryClick(ContainerClickPacket packet, CallbackInfo ci){
		InventoryClickEvent clickEvent = new InventoryClickEvent(
			playerEntity,
			packet.window_Id,
			packet.action,
			packet.args,
			packet.actionId,
			packet.itemStack
		);
		InventoryClickEvent.getEventContainer().runMethods(clickEvent);
		if (clickEvent.isCancelled()) {
			resyncInventory();
			ci.cancel();
		}
	}
	@Unique
	private void resyncInventory(){
		GuiHelper.syncInventory(playerEntity);
	}
}
