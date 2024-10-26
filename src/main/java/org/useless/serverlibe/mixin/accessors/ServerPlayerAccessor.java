package org.useless.serverlibe.mixin.accessors;

import net.minecraft.server.entity.player.ServerPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(value = ServerPlayer.class, remap = false)
public interface ServerPlayerAccessor {
	@Accessor("currentWindowId")
	int serverlibe$getCurrentWindowId();
	@Invoker("getNextWindowId")
	void serverlibe$getNextWindowId();
}
