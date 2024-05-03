package org.useless.serverlibe.api.event.server;

import net.minecraft.server.MinecraftServer;
import org.jetbrains.annotations.NotNull;
import org.useless.serverlibe.internal.EventContainer;

public class ServerTickBeginEvent extends ServerTickEvent {
	public ServerTickBeginEvent
		(
			@NotNull final MinecraftServer server
		)
	{
        super(server);
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
