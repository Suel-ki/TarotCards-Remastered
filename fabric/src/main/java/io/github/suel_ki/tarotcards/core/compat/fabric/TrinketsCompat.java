package io.github.suel_ki.tarotcards.core.compat.fabric;

import dev.emi.trinkets.api.TrinketsApi;
import io.github.suel_ki.tarotcards.core.init.ItemInit;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.concurrent.atomic.AtomicReference;

public class TrinketsCompat {
    public static ItemStack getTrinketStack(Player player, Item item) {
        AtomicReference<ItemStack> found = new AtomicReference<>(ItemStack.EMPTY);

        TrinketsApi.getTrinketComponent(player).ifPresent(component -> {
            component.forEach((slotReference, stack) -> {
                if (found.get().isEmpty() && !stack.isEmpty() && stack.is(item)) {
                    found.set(stack);
                }
            });
        });

        return found.get();
    }

    public static ItemStack getTarotDeckTrinket(Player player) {
        return getTrinketStack(player, ItemInit.tarot_deck.get());
    }

    public static ItemStack getTarotCardTrinket(Player player, Item tarotItem) {
        return getTrinketStack(player, tarotItem);
    }
}
