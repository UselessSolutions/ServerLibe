package org.useless.serverlibe.api.event.player;

import net.minecraft.core.entity.player.EntityPlayer;
import org.jetbrains.annotations.NotNull;
import org.useless.serverlibe.api.event.Event;

import java.util.Objects;

public class PlayerEvent extends Event {
	@NotNull
    public final EntityPlayer player;
    public PlayerEvent(@NotNull final EntityPlayer player){

        this.player = Objects.requireNonNull(player);
    }
}
