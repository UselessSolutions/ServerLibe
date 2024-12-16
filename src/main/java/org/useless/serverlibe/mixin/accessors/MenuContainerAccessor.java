package org.useless.serverlibe.mixin.accessors;

import net.minecraft.core.player.inventory.container.Container;
import net.minecraft.core.player.inventory.menu.MenuContainer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = MenuContainer.class, remap = false)
public interface MenuContainerAccessor {
	@Accessor("container")
	Container getContainerInventory();
}
