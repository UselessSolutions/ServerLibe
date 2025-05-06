package org.useless.serverlibe.mixin.world.decorator;

import net.minecraft.core.world.World;
import net.minecraft.core.world.biome.Biome;
import net.minecraft.core.world.chunk.Chunk;
import net.minecraft.core.world.generate.chunk.ChunkDecorator;
import net.minecraft.core.world.generate.chunk.perlin.overworld.ChunkDecoratorOverworld;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import org.useless.serverlibe.api.event.world.ChunkDecorateEvent;

import java.util.Random;

@Mixin(value = ChunkDecoratorOverworld.class, remap = false)
public abstract class ChunkDecoratorOverworldMixin implements ChunkDecorator {
	@Shadow
	@Final
	private World world;

	@Inject
		(
			method = "decorate",
			at = @At
				(
					value = "INVOKE",
					target = "Ljava/util/Random;setSeed(J)V",
					shift = At.Shift.AFTER
				),
			locals = LocalCapture.CAPTURE_FAILHARD
		)
	public void serverlibe$onDecorate(Chunk chunk, CallbackInfo ci, int chunkX, int chunkZ, int minY, int maxY, int rangeY, float oreHeightModifier, int x, int z, int y, Biome biome, Random rand){
		ChunkDecorateEvent.getEventContainer().runMethods(new ChunkDecorateEvent(this, world, chunk, x, y, z, biome, rand));
	}
}
