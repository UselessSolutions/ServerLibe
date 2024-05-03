package org.useless.serverlibe.mixin.server;

import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.useless.serverlibe.api.event.server.ServerTickBeginEvent;
import org.useless.serverlibe.api.event.server.ServerTickEndEvent;

@Mixin(value = MinecraftServer.class, remap = false)
public class MinecraftServerMixinHandleTick {
	@Shadow
	private static MinecraftServer instance;

	@Inject(method = "doTick", at = @At("HEAD"))
	private void serverlibe$beginTick(CallbackInfo ci){
		ServerTickBeginEvent.getEventContainer().runMethods(new ServerTickBeginEvent(instance));
	}
	@Inject(method = "doTick", at = @At("TAIL"))
	private void serverlibe$endTick(CallbackInfo ci){
		ServerTickEndEvent.getEventContainer().runMethods(new ServerTickEndEvent(instance));
	}
}
