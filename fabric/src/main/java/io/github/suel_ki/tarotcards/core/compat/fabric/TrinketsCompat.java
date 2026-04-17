package io.github.suel_ki.tarotcards.core.compat.fabric;

import dev.emi.trinkets.api.TrinketsApi;
import io.github.suel_ki.tarotcards.core.init.ItemInit;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.concurrent.atomic.AtomicReference;

public class TrinketsCompat {
    public static ItemStack getTrinketStack(LivingEntity entity, Item item) {
        AtomicReference<ItemStack> found = new AtomicReference<>(ItemStack.EMPTY);

        TrinketsApi.getTrinketComponent(entity).ifPresent(component -> {
            component.forEach((slotReference, stack) -> {
                if (found.get().isEmpty() && !stack.isEmpty() && stack.is(item)) {
                    found.set(stack);
                }
            });
        });

        return found.get();
    }

    public static ItemStack getTarotDeckTrinket(LivingEntity entity) {
        return getTrinketStack(entity, ItemInit.tarot_deck.get());
    }

    public static ItemStack getTarotCardTrinket(LivingEntity entity, Item tarotItem) {
        return getTrinketStack(entity, tarotItem);
    }
}
