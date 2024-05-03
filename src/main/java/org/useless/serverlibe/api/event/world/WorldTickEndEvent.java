package org.useless.serverlibe.api.event.world;

import net.minecraft.core.world.World;
import org.jetbrains.annotations.NotNull;
import org.useless.serverlibe.internal.EventContainer;

public class WorldTickEndEvent extends WorldTickEvent{
	public WorldTickEndEvent(@NotNull World world) {
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
