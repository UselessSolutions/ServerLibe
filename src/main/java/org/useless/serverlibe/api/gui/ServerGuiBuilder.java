package org.useless.serverlibe.api.gui;

import net.minecraft.core.entity.player.Player;
import net.minecraft.core.player.inventory.container.Container;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.useless.serverlibe.api.gui.slot.ServerSlotBase;
import org.useless.serverlibe.api.gui.slot.ServerSlotDisplay;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 *
 * @author Useless
 * @since beta.1
 */
public final class ServerGuiBuilder {
	@NotNull
	private final Map<@NotNull Integer, Function<@NotNull Container, @NotNull ServerSlotBase>> playerSlotMap = new HashMap<>();
	@NotNull
	private final Map<@NotNull Integer, Function<@NotNull Container, @NotNull ServerSlotBase>> containerSlotMap = new HashMap<>();
	@Nullable
	private BiFunction<@NotNull Container, @NotNull Integer, @NotNull ServerSlotBase> defaultPlayerSlot = null;
	@Nullable
	private BiFunction<@NotNull Container, @NotNull Integer, @NotNull ServerSlotBase> defaultContainerSlot = null;
	private int highestContainerID = 0;

	/**
	 * Sets the minimum number of rows the GUI can be.
	 * This value may be ignored if a container slot id is set higher
	 * than the slot amount allocated here.
	 *
	 * @param rows The number of rows for the GUI. (each row being 9 slots)
	 * @return A reference to this object.
	 *
	 * @since beta.1
	 * @author Useless
	 */
	@NotNull
	public ServerGuiBuilder setSize(int rows){
		highestContainerID = Math.max(highestContainerID, rows*9);
		return this;
	}

	/**
	 * Sets a provider that creates the default slot objects for the container inventory section of the GUI.
	 * If set to null it'll default to the container slots as defined in {@link ServerGuiBase#getSlotForContainerInv(Container, int)}
	 *
	 * @param defaultContainerSlot Provides a {@link ServerSlotBase} when called.
	 * @return A reference to this object.
	 *
	 * @since beta.1
	 * @author Useless
	 */
	@NotNull
	public ServerGuiBuilder setDefaultContainerSlot(@Nullable BiFunction<@NotNull Container, @NotNull Integer, @NotNull ServerSlotBase> defaultContainerSlot){
		this.defaultContainerSlot = defaultContainerSlot;
		return this;
	}
	/**
	 * Sets a provider that creates the default slot objects for the player inventory section of the GUI.
	 * If set to null it'll default to the container slots as defined in {@link ServerGuiBase#getSlotForPlayerInv(Container, int)}
	 *
	 * @param defaultPlayerSlot Provides a {@link ServerSlotBase} when called.
	 * @return A reference to this object.
	 *
	 * @since beta.1
	 * @author Useless
	 */
	@NotNull
	public ServerGuiBuilder setDefaultPlayerSlot(@Nullable BiFunction<@NotNull Container, @NotNull Integer, @NotNull ServerSlotBase> defaultPlayerSlot){
		this.defaultPlayerSlot = defaultPlayerSlot;
		return this;
	}

	/**
	 * Sets a {@link ServerSlotBase} provider mapped to a specific container id.
	 * If provided ID is greater than the max allocated slots, the allocated slot amount
	 * will automatically increase.
	 *
	 * @param id Slot Id value.
	 * @param buttonFunction Provides a {@link ServerSlotBase} when called.
	 * @return A reference to this object.
	 *
	 * @since beta.1
	 * @author Useless
	 */
	@NotNull
	public ServerGuiBuilder setContainerSlot(int id, @NotNull Function<@NotNull Container, @NotNull ServerSlotBase> buttonFunction){
		containerSlotMap.put(id, Objects.requireNonNull(buttonFunction));
		if (id > highestContainerID) {
			highestContainerID = id;
		}
		return this;
	}
	/**
	 * Sets a {@link ServerSlotBase} provider mapped to a specific player container id.
	 *
	 * @param id Slot Id value.
	 * @param buttonFunction Provides a {@link ServerSlotBase} when called.
	 * @return A reference to this object.
	 *
	 * @since beta.1
	 * @author Useless
	 */
	@NotNull
	public ServerGuiBuilder setPlayerSlot(int id, @NotNull Function<@NotNull Container, @NotNull ServerSlotBase> buttonFunction){
		playerSlotMap.put(id, Objects.requireNonNull(buttonFunction));
		return this;
	}

	/**
	 * Creates a new, preconfigured, {@link ServerGuiBase} from the provided build instructions.
	 *
	 * @param player {@link Player} to show GUI to.
	 * @param title Title shown to the client at the top of the GUI.
	 * @return Built {@link ServerGuiBase} from the builder's configuration.
	 */
	@NotNull
	public ServerGuiBase build(@NotNull Player player, @NotNull String title){
		final int rows = (int) Math.ceil((highestContainerID + 1) /9f);
		return new ServerGuiBase(player, title, rows){
			public ServerSlotBase getSlotForContainerInv(Container container, int id){
				if (containerSlotMap.get(id) != null){
					return containerSlotMap.get(id).apply(container);
				}
				if (defaultContainerSlot != null){
					return defaultContainerSlot.apply(container, id);
				}
				return new ServerSlotDisplay(container, id);
			}
			public ServerSlotBase getSlotForPlayerInv(Container playerInventory, int id){
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
