package io.github.suel_ki.tarotcards.core.mixin.access;

import net.minecraft.core.NonNullList;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(SimpleContainer.class)
public interface SimpleContainerAccessor {
    @Accessor
    NonNullList<ItemStack> getItems();
}
