package org.useless.serverlibe.api.gui;

import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.player.inventory.IInventory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.useless.serverlibe.api.gui.slot.ServerSlotBase;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;

public final class ServerGuiBuilder {
	@NotNull
	private final Map<@NotNull Integer, Function<@NotNull IInventory, @NotNull ServerSlotBase>> playerSlotMap = new HashMap<>();
	@NotNull
	private final Map<@NotNull Integer, Function<@NotNull IInventory, @NotNull ServerSlotBase>> containerSlotMap = new HashMap<>();
	@Nullable
	private BiFunction<@NotNull IInventory, @NotNull Integer, @NotNull ServerSlotBase> defaultPlayerSlot = null;
	@Nullable
	private BiFunction<@NotNull IInventory, @NotNull Integer, @NotNull ServerSlotBase> defaultContainerSlot = null;
	private int highestContainerID = 0;
	@NotNull
	public ServerGuiBuilder setSize(int rows){
		highestContainerID = Math.max(highestContainerID, rows*9);
		return this;
	}
	@NotNull
	public ServerGuiBuilder setDefaultContainerSlot(@Nullable BiFunction<@NotNull IInventory, @NotNull Integer, @NotNull ServerSlotBase> defaultContainerSlot){
		this.defaultContainerSlot = defaultContainerSlot;
		return this;
	}
	@NotNull
	public ServerGuiBuilder setDefaultPlayerSlot(@Nullable BiFunction<@NotNull IInventory, @NotNull Integer, @NotNull ServerSlotBase> defaultPlayerSlot){
		this.defaultPlayerSlot = defaultPlayerSlot;
		return this;
	}
	@NotNull
	public ServerGuiBuilder setContainerSlot(int id, @NotNull Function<@NotNull IInventory, @NotNull ServerSlotBase> buttonFunction){
		containerSlotMap.put(id, Objects.requireNonNull(buttonFunction));
		if (id > highestContainerID) {
			highestContainerID = id;
		}
		return this;
	}
	@NotNull
	public ServerGuiBuilder setPlayerSlot(int id, @NotNull Function<@NotNull IInventory, @NotNull ServerSlotBase> buttonFunction){
		playerSlotMap.put(id, Objects.requireNonNull(buttonFunction));
		return this;
	}
	@NotNull
	public ServerGuiBase build(@NotNull EntityPlayer player, @NotNull String title){
		final int rows = (int) Math.ceil(highestContainerID /9f);
        return new ServerGuiBase(player, title, rows){
			public ServerSlotBase getSlotForContainerInv(IInventory containerInventory, int id){
				if (containerSlotMap.get(id) != null){
					return containerSlotMap.get(id).apply(containerInventory);
				}
				if (defaultContainerSlot != null){
					return defaultContainerSlot.apply(containerInventory, id);
				}
				return super.getSlotForContainerInv(containerInventory, id);
			}
			public ServerSlotBase getSlotForPlayerInv(IInventory playerInventory, int id){
				if (playerSlotMap.get(id) != null){
					return playerSlotMap.get(id).apply(playerInventory);
				}
				if (defaultPlayerSlot != null){
					return defaultPlayerSlot.apply(playerInventory, id);
				}
				return super.getSlotForPlayerInv(playerInventory, id);
			}
		};
	}
}
