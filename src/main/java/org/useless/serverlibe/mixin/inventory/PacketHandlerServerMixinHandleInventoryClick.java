package org.useless.serverlibe.mixin.inventory;

import net.minecraft.core.net.packet.PacketContainerClick;
import net.minecraft.server.entity.player.PlayerServer;
import net.minecraft.server.net.handler.PacketHandlerServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.useless.serverlibe.api.event.player.inventory.InventoryClickEvent;
import org.useless.serverlibe.api.gui.GuiHelper;

@Mixin(value = PacketHandlerServer.class, remap = false)
public class PacketHandlerServerMixinHandleInventoryClick {
	@Shadow
	private PlayerServer playerEntity;

	@Inject
		(
			method = "handleWindowClick",
			at = @At("HEAD"),
			cancellable = true)
	public void serverlibe$onInventoryClick(PacketContainerClick packet, CallbackInfo ci){
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
