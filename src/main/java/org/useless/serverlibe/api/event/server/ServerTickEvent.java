package org.useless.serverlibe.api.event.server;

import net.minecraft.server.MinecraftServer;
import org.jetbrains.annotations.NotNull;

abstract class ServerTickEvent {
	@NotNull
	public final MinecraftServer server;
	public ServerTickEvent
		(
			@NotNull final MinecraftServer server
		)
	{
		this.server = server;
	}
}
