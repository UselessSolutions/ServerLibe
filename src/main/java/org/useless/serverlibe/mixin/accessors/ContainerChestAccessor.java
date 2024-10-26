package org.useless.serverlibe.mixin.accessors;

import net.minecraft.core.player.inventory.ContainerChest;
import net.minecraft.core.player.inventory.Inventory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = ContainerChest.class, remap = false)
public interface ContainerChestAccessor {
	@Accessor("field_20125_a")
	Inventory getContainerInventory();
}
