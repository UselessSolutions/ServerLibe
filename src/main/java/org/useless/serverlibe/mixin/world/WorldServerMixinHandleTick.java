package org.useless.serverlibe.mixin.world;

import net.minecraft.core.world.World;
import net.minecraft.server.world.WorldServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.useless.serverlibe.api.event.world.WorldTickBeginEvent;
import org.useless.serverlibe.api.event.world.WorldTickEndEvent;

@Mixin(value = WorldServer.class, remap = false)
public class WorldServerMixinHandleTick {
	@Inject(method = "tick", at = @At("HEAD"))
	private void serverlibe$beginTick(CallbackInfo ci){
		WorldTickBeginEvent.getEventContainer().runMethods(new WorldTickBeginEvent((World)(Object)this));
	}
	@Inject(method = "tick", at = @At("TAIL"))
	private void serverlibe$endTick(CallbackInfo ci){
		WorldTickEndEvent.getEventContainer().runMethods(new WorldTickEndEvent((World)(Object)this));
	}
}
