package io.github.suel_ki.tarotcards.core.compat.forge;

import io.github.suel_ki.tarotcards.core.init.ItemInit;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.CuriosApi;

public class CuriosCompat {

    public static ItemStack getCurioStack(LivingEntity entity, Item item) {
        return CuriosApi.getCuriosInventory(entity).map(handler -> {
            var result = handler.findFirstCurio(item);
            return result.isPresent() ? result.get().stack() : ItemStack.EMPTY;
        }).orElse(ItemStack.EMPTY);
    }

    public static ItemStack getTarotDeckCurio(LivingEntity entity) {
        return getCurioStack(entity, ItemInit.tarot_deck.get());
    }

    public static ItemStack getTarotCardCurio(LivingEntity entity, Item tarotItem) {
        return getCurioStack(entity, tarotItem);
    }
}
