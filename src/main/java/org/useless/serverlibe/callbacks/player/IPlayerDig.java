package org.useless.serverlibe.callbacks.player;

import org.jetbrains.annotations.NotNull;
import org.useless.serverlibe.callbacks.IEvent;

public interface IPlayerDig extends IEvent {
	boolean onDigEvent(@NotNull final PlayerDigEvent placeEvent);
}
