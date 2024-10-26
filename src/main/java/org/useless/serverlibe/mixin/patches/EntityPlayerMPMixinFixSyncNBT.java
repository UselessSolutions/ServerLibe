package org.useless.serverlibe.mixin.patches;

import net.minecraft.core.item.ItemStack;
import net.minecraft.core.net.packet.Packet103SetSlot;
import net.minecraft.core.player.inventory.Container;
import net.minecraft.server.entity.player.ServerPlayer;
import net.minecraft.server.net.handler.NetServerHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(value = ServerPlayer.class, remap = false)
public class ServerPlayerMixinFixSyncNBT {
	@Shadow
	public NetServerHandler playerNetServerHandler;

	@Inject
		(
			method = "updateCraftingInventory(Lnet/minecraft/core/player/inventory/Container;Ljava/util/List;)V",
			at = @At
				(
					value = "INVOKE",
					target = "Lnet/minecraft/server/net/handler/NetServerHandler;sendPacket(Lnet/minecraft/core/net/packet/Packet;)V",
					shift = At.Shift.AFTER
				)
		)
	public void serverlibe$syncNBTData(Container container, List<ItemStack> list, CallbackInfo ci){
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i) == null) continue;
			if (!list.get(i).getData().getValues().isEmpty()){
				playerNetServerHandler.sendPacket(new Packet103SetSlot(container.windowId, i, list.get(i)));
			}
		}
	}
}
