package org.useless.test.gui;

import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.player.inventory.IInventory;
import org.jetbrains.annotations.NotNull;
import org.useless.serverlibe.api.gui.ServerGuiBase;
import org.useless.serverlibe.api.gui.slot.ServerSlotBase;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public final class ServerGuiBuilder {
	private final Map<Integer, Function<IInventory, ServerSlotBase>> playerSlotMap = new HashMap<>();
	private final Map<Integer, Function<IInventory, ServerSlotBase>> containerSlotMap = new HashMap<>();
	private int highestID = 0;
	public ServerGuiBuilder setSize(int rows){
		highestID = Math.max(highestID, rows*9);
		return this;
	}
	@NotNull
	public ServerGuiBuilder setContainerSlot(int id, Function<IInventory, ServerSlotBase> buttonFunction){
		containerSlotMap.put(id, buttonFunction);
		if (id > highestID) {
			highestID = id;
		}
		return this;
	}
	@NotNull
	public ServerGuiBuilder setPlayerSlot(int id, Function<IInventory, ServerSlotBase> buttonFunction){
		playerSlotMap.put(id, buttonFunction);
		return this;
	}
	public ServerGuiBase build(EntityPlayer player, String title){
		int rows = (int) Math.ceil(highestID/9f);
		ServerGuiBase guiBase = new ServerGuiBase(player, title, rows){
			public ServerSlotBase getSlotForContainerInv(IInventory containerInventory, int id){
				if (containerSlotMap.get(id) != null){
					return containerSlotMap.get(id).apply(containerInventory);
				}
				return super.getSlotForContainerInv(containerInventory, id);
			}
			public ServerSlotBase getSlotForPlayerInv(IInventory playerInventory, int id){
				if (playerSlotMap.get(id) != null){
					return playerSlotMap.get(id).apply(playerInventory);
				}
				return super.getSlotForPlayerInv(playerInventory, id);
			}
		};
		return guiBase;
	}
}
