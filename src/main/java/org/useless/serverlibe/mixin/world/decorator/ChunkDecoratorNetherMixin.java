package org.useless.serverlibe.mixin.world.decorator;

import net.minecraft.core.world.World;
import net.minecraft.core.world.biome.Biome;
import net.minecraft.core.world.chunk.Chunk;
import net.minecraft.core.world.generate.chunk.ChunkDecorator;
import net.minecraft.core.world.generate.chunk.perlin.nether.ChunkDecoratorNether;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import org.useless.serverlibe.api.event.world.ChunkDecorateEvent;

import java.util.Random;

@Mixin(value = ChunkDecoratorNether.class, remap = false)
public abstract class ChunkDecoratorNetherMixin implements ChunkDecorator {
	@Shadow
	@Final
	private World world;

	@Inject
		(
			method = "decorate",
			at = @At
				(
					value = "FIELD",
					target = "Lnet/minecraft/core/block/BlockLogicSand;fallInstantly:Z",
					shift = At.Shift.AFTER,
					ordinal = 0
				),
			locals = LocalCapture.CAPTURE_FAILHARD
		)
	public void serverlibe$onDecorate(Chunk chunk, CallbackInfo ci, int chunkX, int chunkZ, int minY, int maxY, int rangeY, Random rand){
		final int x = chunkX * 16;
		final int z = chunkZ * 16;
		final int y = this.world.getHeightValue(x + 16, z + 16);
		final Biome biome = this.world.getBlockBiome(x + 16, y, z + 16);
		ChunkDecorateEvent.getEventContainer().runMethods(new ChunkDecorateEvent(this, world, chunk, x, y, z, biome, rand));
	}
}
