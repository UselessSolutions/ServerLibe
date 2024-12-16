package org.useless.test;

import net.minecraft.core.block.Blocks;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.EntityLightning;
import net.minecraft.core.entity.projectile.ProjectileFireball;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.Items;
import net.minecraft.core.net.command.TextFormatting;
import net.minecraft.core.net.packet.PacketAddParticle;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.util.phys.Vec3;
import net.minecraft.core.world.generate.feature.WorldFeatureLake;
import net.minecraft.server.entity.player.PlayerServer;
import org.useless.serverlibe.api.Listener;
import org.useless.serverlibe.api.annotations.EventListener;
import org.useless.serverlibe.api.enums.Priority;
import org.useless.serverlibe.api.event.player.PlayerChatEvent;
import org.useless.serverlibe.api.event.player.PlayerDigEvent;
import org.useless.serverlibe.api.event.player.PlayerEntityInteractEvent;
import org.useless.serverlibe.api.event.player.PlayerItemUseEvent;
import org.useless.serverlibe.api.event.player.PlayerMovementEvent;
import org.useless.serverlibe.api.event.world.ChunkDecorateEvent;

import java.util.List;

public class TestFeatureListener implements Listener {
	@EventListener
	public void playerTrail(PlayerMovementEvent movementEvent){
		if (movementEvent.distanceMoved < 0.05) return;
		final PlayerServer serverPlayer = (PlayerServer)movementEvent.player;
		final boolean movingQuick = movementEvent.distanceMoved > 0.7;
		final String particleKey = movingQuick ? "blueflame" : "flame";
		serverPlayer.playerNetServerHandler.sendPacket(new PacketAddParticle(particleKey, serverPlayer.x, serverPlayer.y, serverPlayer.z, 0, 0, 0, 0));
	}
	@EventListener(priority = Priority.HIGH)
	public void disableBreak(PlayerDigEvent digEvent){
		if (digEvent.player.getHeldItem() != null && digEvent.player.getHeldItem().hasCustomName()) {
			digEvent.setCancelled(true);
		}
	}
	@EventListener(priority = Priority.LOW, ignoreCancelled = true)
	public void blockBreakEffect(PlayerDigEvent digEvent){
		final PlayerServer playerMP = (PlayerServer)digEvent.player;
		playerMP.playerNetServerHandler.sendPacket(new PacketAddParticle
			(
				"explode",
				digEvent.x + 0.5, digEvent.y + 0.5, digEvent.z + 0.5,
				0, 0, 0, 0, 16, (byte) 8,
				0, 0, 0,
				0, 0, 0)
		);
	}
	@EventListener
	public void modifyChat(PlayerChatEvent chatEvent){
		final String seperator = "> " + TextFormatting.WHITE;
		final float greenPercent = 0.5f;
		String[] vals = chatEvent.getMessage().split(seperator);
		if (vals.length < 2) return;
		final int greenChars = (int) (vals[1].length() * greenPercent);
		String payload = TextFormatting.LIME + vals[1].substring(0, greenChars/2) + TextFormatting.RED + vals[1].substring(greenChars/2, vals[1].length() - (greenChars/2)) + TextFormatting.LIME + vals[1].substring(vals[1].length() - (greenChars/2));
		chatEvent.setMessage(vals[0] + "> " + payload);
	}
	@EventListener(priority = Priority.HIGH, ignoreCancelled = true)
	public void useSaddleSpecial(PlayerItemUseEvent useEvent){
		ItemStack heldItem = useEvent.itemstack;
		if (heldItem.hasCustomName() && heldItem.getCustomName().equalsIgnoreCase("wrangler")){
			List<Entity> entities = useEvent.world.getEntitiesWithinAABBExcludingEntity(useEvent.player, useEvent.player.bb.expand(4, 2, 4));
			if (entities.isEmpty()) return;
			useEvent.player.startRiding(entities.get(0));
			useEvent.setCancelled(true);
		}
	}
	@EventListener(priority = Priority.HIGH, ignoreCancelled = true)
	public void useStickSpecial(PlayerItemUseEvent useEvent){
		if (useEvent.itemstack.getItem() == Items.STICK){
			Vec3 look = useEvent.player.getLookAngle();
			double vX = look.x;
			double vY = look.y;
			double vZ = look.z;
			ProjectileFireball fireball = new ProjectileFireball(useEvent.world, useEvent.player, vX, vY, vZ);
			fireball.y += 0.9f;
			double velocity = MathHelper.sqrt(vX * vX + vY * vY + vZ * vZ);
			if (velocity != 0.0) {
				fireball.xd = vX / velocity * 0.1;
				fireball.yd = vY / velocity * 0.1;
				fireball.zd = vZ / velocity * 0.1;
			} else {
				fireball.xd = 0.0;
				fireball.yd = 0.0;
				fireball.zd = 0.0;
			}
			useEvent.world.entityJoinedWorld(fireball);
			useEvent.setCancelled(true);
		}
	}
	@EventListener
	public void onAttack(PlayerEntityInteractEvent interactEvent){
		if (interactEvent.itemstack != null && interactEvent.itemstack.getItem() == Items.STICK){
			EntityLightning lightningBolt = new EntityLightning(interactEvent.world, interactEvent.targetEntity.x, interactEvent.targetEntity.y, interactEvent.targetEntity.z);
			interactEvent.world.addWeatherEffect(lightningBolt);
			interactEvent.setCancelled(true);
		}
	}
	@EventListener
	public void decorate(ChunkDecorateEvent decorateEvent){
		for (int i = 0; i < 4; i++) {
			new WorldFeatureLake(Blocks.BLOCK_DIAMOND.id()).place(decorateEvent.world, decorateEvent.random, decorateEvent.x + decorateEvent.random.nextInt(15), decorateEvent.random.nextInt(decorateEvent.world.getHeightBlocks()), decorateEvent.z + decorateEvent.random.nextInt(15));
		}
	}
}
