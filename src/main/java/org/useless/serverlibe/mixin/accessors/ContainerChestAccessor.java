package org.useless.serverlibe.mixin.accessors;

import net.minecraft.core.player.inventory.ContainerChest;
import net.minecraft.core.player.inventory.IInventory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ContainerChest.class)
public interface ContainerChestAccessor {
	@Accessor("field_20125_a")
	IInventory getContainerInventory();
}
