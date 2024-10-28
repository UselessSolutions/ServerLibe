package org.useless.serverlibe.mixin.accessors;

import net.minecraft.core.player.inventory.container.Container;
import net.minecraft.core.player.inventory.menu.ContainerMenu;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = ContainerMenu.class, remap = false)
public interface ContainerMenuAccessor {
	@Accessor("container")
	Container getContainerInventory();
}
