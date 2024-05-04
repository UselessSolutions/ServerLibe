package org.useless.serverlibe.mixin.inventory;

import net.minecraft.core.net.packet.Packet102WindowClick;
import net.minecraft.core.player.inventory.ContainerChest;
import net.minecraft.server.entity.player.EntityPlayerMP;
import net.minecraft.server.net.handler.NetServerHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.useless.serverlibe.api.event.player.inventory.InventoryClickEvent;
import org.useless.serverlibe.api.gui.GuiHelper;
import org.useless.serverlibe.api.gui.ServerGuiBase;

@Mixin(value = NetServerHandler.class, remap = false)
public class NetServerHandlerMixinHandleInventoryClick {
	@Shadow
	private EntityPlayerMP playerEntity;

	@Inject
		(
			method = "handleWindowClick",
			at = @At("HEAD"),
			cancellable = true)
	public void serverlibe$onInventoryClick(Packet102WindowClick packet, CallbackInfo ci){
		InventoryClickEvent clickEvent = new InventoryClickEvent(
			playerEntity,
			packet.window_Id,
			packet.action,
			packet.args,
			packet.actionId,
			packet.itemStack
		);
		if (playerEntity.craftingInventory instanceof ContainerChest && playerEntity.craftingInventory instanceof ServerGuiBase){
			ServerGuiBase guiBase = (ServerGuiBase)playerEntity.craftingInventory;
			guiBase.onInventoryAction(clickEvent);
		} else {
			InventoryClickEvent.getEventContainer().runMethods(clickEvent);
		}
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
