package org.useless.serverlibe.mixin.player;

import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.World;
import net.minecraft.server.net.handler.NetServerHandler;
import net.minecraft.server.world.ServerPlayerController;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.useless.serverlibe.callbacks.player.IPlayerPlace;
import org.useless.serverlibe.callbacks.player.PlayerPlaceEvent;
import org.useless.serverlibe.data.EventId;
import org.useless.serverlibe.data.Order;
import org.useless.serverlibe.data.Priority;
import org.useless.serverlibe.internal.EventContainer;
import org.useless.serverlibe.internal.InternalStorageClass;

import java.util.List;

@Mixin(value = NetServerHandler.class, remap = false)
public class NetServerHandlerMixinHandlePlace {

    @Redirect
		(
		method = "handlePlace(Lnet/minecraft/core/net/packet/Packet15Place;)V",
		at = @At
			(
			value = "INVOKE",
			target = "Lnet/minecraft/server/world/ServerPlayerController;activateBlockOrUseItem(Lnet/minecraft/core/entity/player/EntityPlayer;Lnet/minecraft/core/world/World;Lnet/minecraft/core/item/ItemStack;IIILnet/minecraft/core/util/helper/Side;DD)Z"
			)
		)
	public boolean serverlibe$onBlockPlaced
		(
		@NotNull final ServerPlayerController controller,
		@NotNull final EntityPlayer playerEntity,
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
		final PlayerPlaceEvent playerPlaceEvent = new PlayerPlaceEvent(playerEntity, world, itemstack, x, y, z, side, xPlaced, yPlaced);
		final EventContainer<IPlayerPlace> placeContainer = InternalStorageClass.getEventContainer(EventId.PLAYER_PLACE_EVENT_ID);

		boolean cancelDefaultPlace = false;
		for (final Priority priority : Priority.values()){
			final List<IPlayerPlace> placeEvents = placeContainer.getEvents(priority, Order.BEFORE);
			for (final IPlayerPlace placeEvent : placeEvents){
				cancelDefaultPlace |= placeEvent.onPlaceEvent(playerPlaceEvent);
			}
		}

		final boolean returnVal;
		if (!cancelDefaultPlace){
			returnVal = controller.activateBlockOrUseItem(playerEntity, world, itemstack, x, y, z, side, xPlaced, yPlaced);
		} else {
			returnVal = false;
		}

		for (final Priority priority : Priority.values()){
			final List<IPlayerPlace> placeEvents = placeContainer.getEvents(priority, Order.AFTER);
			for (final IPlayerPlace placeEvent : placeEvents){
				placeEvent.onPlaceEvent(playerPlaceEvent);
			}
		}

		return returnVal;
	}
}
