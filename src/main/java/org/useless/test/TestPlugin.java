package org.useless.test;

import net.minecraft.core.net.packet.Packet63SpawnParticleEffect;
import net.minecraft.server.entity.player.EntityPlayerMP;
import org.useless.serverlibe.api.Listener;
import org.useless.serverlibe.api.annotations.EventListener;
import org.useless.serverlibe.callbacks.player.PlayerDigEvent;
import org.useless.serverlibe.callbacks.player.PlayerMovementEvent;
import org.useless.serverlibe.data.Priority;

public class TestPlugin implements Listener {
	@EventListener
	public void playerTrail(PlayerMovementEvent movementEvent){
		if (movementEvent.distanceMoved < 0.05) return;
		final EntityPlayerMP playerMP = (EntityPlayerMP)movementEvent.player;
		final boolean movingQuick = movementEvent.distanceMoved > 0.7;
		final String particleKey = movingQuick ? "blueflame" : "flame";
		playerMP.playerNetServerHandler.sendPacket(new Packet63SpawnParticleEffect(particleKey, playerMP.x, playerMP.y, playerMP.z, 0, 0, 0));
	}
	@EventListener(priority = Priority.HIGH)
	public void disableBreak(PlayerDigEvent digEvent){
		if (digEvent.player.getHeldItem() != null && digEvent.player.getHeldItem().hasCustomName()) {
			digEvent.setCancelled(true);
		}
	}
	@EventListener(priority = Priority.LOW, ignoreCancelled = true)
	public void blockBreakEffect(PlayerDigEvent digEvent){
		final EntityPlayerMP playerMP = (EntityPlayerMP)digEvent.player;
		playerMP.playerNetServerHandler.sendPacket(new Packet63SpawnParticleEffect
			(
				"explode",
				digEvent.x + 0.5, digEvent.y + 0.5, digEvent.z + 0.5,
				0, 0, 0, 16, (byte) 8,
				0, 0, 0,
				0, 0, 0)
		);
	}
}
