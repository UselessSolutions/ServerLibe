package org.useless.serverlibe.api.event.world;

import net.minecraft.core.world.World;
import net.minecraft.core.world.biome.Biome;
import net.minecraft.core.world.biome.BiomeNether;
import net.minecraft.core.world.biome.Biomes;
import net.minecraft.core.world.chunk.Chunk;
import net.minecraft.core.world.generate.chunk.ChunkDecorator;
import org.jetbrains.annotations.NotNull;
import org.useless.serverlibe.api.event.Event;
import org.useless.serverlibe.internal.EventContainer;

import java.util.Objects;
import java.util.Random;

public class ChunkDecorateEvent extends Event {
	@NotNull
    public final ChunkDecorator decorator;
	@NotNull
	public final World world;
	@NotNull
	public final Chunk chunk;
	public final int x;
	public final int y;
	public final int z;
	@NotNull
	public final Biome biome;
	@NotNull
	public final Random random;

    public ChunkDecorateEvent
		(
			@NotNull final ChunkDecorator decorator,
			@NotNull final World world,
			@NotNull final Chunk chunk,
			final int x,
			final int y,
			final int z,
			@NotNull Biome biome,
			@NotNull Random random
		)
	{

        this.decorator = Objects.requireNonNull(decorator);
        this.world = Objects.requireNonNull(world);
        this.chunk = Objects.requireNonNull(chunk);
        this.x = x;
        this.y = y;
        this.z = z;
        this.biome = Objects.requireNonNull(biome);
        this.random = Objects.requireNonNull(random);
    }

	private static final EventContainer eventContainer = new EventContainer();
	@NotNull
	public EventContainer getEvents() {
		return eventContainer;
	}

	@NotNull
	public static EventContainer getEventContainer() {
		return eventContainer;
	}
}
