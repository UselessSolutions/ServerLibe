package org.useless.serverlibe.api.event.player;

import net.minecraft.core.entity.player.Player;
import org.jetbrains.annotations.NotNull;
import org.useless.serverlibe.api.event.Cancellable;
import org.useless.serverlibe.internal.EventContainer;

import java.util.Objects;

public class PlayerChatEvent extends PlayerEvent implements Cancellable {
	@NotNull
    public final String originalMessage;
	@NotNull
	private String currentMessage;

    public PlayerChatEvent
		(
			@NotNull final Player player,
			@NotNull final String message
		)
	{
		super(player);
        this.originalMessage = Objects.requireNonNull(message);
		this.currentMessage = this.originalMessage;
    }
	public void setMessage(@NotNull final String newMessage){
		this.currentMessage = Objects.requireNonNull(newMessage);
	}
	public String getMessage(){
		return this.currentMessage;
	}
	private boolean cancelled = false;
	@Override
	public boolean isCancelled() {
		return cancelled;
	}
	@Override
	public void setCancelled(boolean cancel) {
		cancelled = cancel;
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
