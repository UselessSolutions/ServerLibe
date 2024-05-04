package org.useless.serverlibe.mixin.accessors;

import net.minecraft.server.entity.player.EntityPlayerMP;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(value = EntityPlayerMP.class, remap = false)
public interface EntityPlayerMPAccessor {
	@Accessor("currentWindowId")
	int serverlibe$getCurrentWindowId();
	@Invoker("getNextWindowId")
	void serverlibe$getNextWindowId();
}
