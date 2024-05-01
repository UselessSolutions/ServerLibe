package org.useless.serverlibe.debug;

import net.minecraft.core.net.packet.Packet63SpawnParticleEffect;
import net.minecraft.server.entity.player.EntityPlayerMP;
import org.useless.serverlibe.api.ServerLibeEntrypoint;
import org.useless.serverlibe.data.EventId;
import org.useless.serverlibe.data.Order;
import org.useless.serverlibe.data.Priority;
import org.useless.serverlibe.internal.InternalStorageClass;

public class TestMain implements ServerLibeEntrypoint {
	@Override
	public void serverlibeEntry() {
		InternalStorageClass.getEventContainer(EventId.PLAYER_MOVE_EVENT_ID).addEvent(movementEvent -> {
			if (movementEvent.distanceMoved < 0.05) return false;
			final EntityPlayerMP playerMP = (EntityPlayerMP)movementEvent.player;
			playerMP.playerNetServerHandler.sendPacket(new Packet63SpawnParticleEffect("flame", playerMP.x, playerMP.y, playerMP.z, 0, 0, 0));

			return false;
		}, Priority.NORMAL, Order.BEFORE);
		InternalStorageClass.getEventContainer(EventId.PLAYER_DIG_EVENT_ID).addEvent(digEvent -> {
			final EntityPlayerMP playerMP = (EntityPlayerMP)digEvent.player;
			playerMP.playerNetServerHandler.sendPacket(new Packet63SpawnParticleEffect("flame", digEvent.x + 0.5f + digEvent.side.getOffsetX()/2f, digEvent.y + 0.5f + digEvent.side.getOffsetY()/2f, digEvent.z + 0.5f + digEvent.side.getOffsetZ()/2f, 0, 0, 0));
			return false;
		}, Priority.NORMAL, Order.BEFORE);
		InternalStorageClass.getEventContainer(EventId.PLAYER_DIG_EVENT_ID).addEvent(digEvent -> {
			if (digEvent.player.getHeldItem() != null && digEvent.player.getHeldItem().hasCustomName()){
				return true;
			}
			return false;
		}, Priority.NORMAL, Order.BEFORE);
	}
}
