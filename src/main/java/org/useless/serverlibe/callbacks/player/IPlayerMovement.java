package org.useless.serverlibe.callbacks.player;

import org.jetbrains.annotations.NotNull;
import org.useless.serverlibe.callbacks.IEvent;
@FunctionalInterface
public interface IPlayerMovement extends IEvent {
	boolean onPlayerMoved(@NotNull final PlayerMovementEvent movementEvent);
}
