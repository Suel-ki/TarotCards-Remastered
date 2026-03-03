package io.github.suel_ki.tarotcards.common.menu;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;

@FunctionalInterface
public interface MenuFactory<T extends AbstractContainerMenu> {
    T create(int syncId, Inventory inv, ItemStack stack);
}
