package org.useless.serverlibe.api.event.player;

import net.minecraft.core.entity.player.Player;
import org.jetbrains.annotations.NotNull;
import org.useless.serverlibe.api.event.Event;

import java.util.Objects;

class PlayerEvent extends Event {
	@NotNull
    public final Player player;
    public PlayerEvent(@NotNull final Player player){

        this.player = Objects.requireNonNull(player);
    }
}
