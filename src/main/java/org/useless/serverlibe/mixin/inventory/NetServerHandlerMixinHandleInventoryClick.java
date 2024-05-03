package org.useless.serverlibe.mixin.inventory;

import net.minecraft.core.item.ItemStack;
import net.minecraft.core.net.packet.Packet102WindowClick;
import net.minecraft.server.entity.player.EntityPlayerMP;
import net.minecraft.server.net.handler.NetServerHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.useless.serverlibe.api.event.player.inventory.InventoryClickEvent;

import java.util.ArrayList;

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
		InventoryClickEvent clickEvent = InventoryClickEvent.getEventContainer().runMethods(
		new InventoryClickEvent(
				playerEntity,
				packet.window_Id,
				packet.action,
				packet.args,
				packet.actionId,
				packet.itemStack
			)
		);
		if (clickEvent.isCancelled()) {
			resyncInventory();
			ci.cancel();
		}
	}
	@Unique
	private void resyncInventory(){
		ArrayList<ItemStack> arraylist = new ArrayList<>();
		for (int i = 0; i < this.playerEntity.craftingInventory.inventorySlots.size(); ++i) {
			arraylist.add(this.playerEntity.craftingInventory.inventorySlots.get(i).getStack());
		}
		this.playerEntity.updateCraftingInventory(this.playerEntity.craftingInventory, arraylist);
	}
}
