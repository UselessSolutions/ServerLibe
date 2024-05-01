package org.useless.serverlibe.callbacks.player;

import org.jetbrains.annotations.NotNull;
import org.useless.serverlibe.callbacks.IEvent;
@FunctionalInterface
public interface IPlayerPlace extends IEvent {
	boolean onPlaceEvent(@NotNull final PlayerPlaceEvent event);
}
