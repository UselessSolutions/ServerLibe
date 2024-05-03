package org.useless.serverlibe.api.event.world;

import net.minecraft.core.world.World;
import org.jetbrains.annotations.NotNull;

abstract class WorldTickEvent {
	@NotNull
    public final World world;

    public WorldTickEvent(@NotNull final World world){
        this.world = world;
    }
}
