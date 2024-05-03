package org.useless.serverlibe.api.event.world;

import net.minecraft.core.world.World;
import org.jetbrains.annotations.NotNull;
import org.useless.serverlibe.internal.EventContainer;

public class WorldTickBeginEvent extends WorldTickEvent{
	public WorldTickBeginEvent(@NotNull World world) {
		super(world);
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
