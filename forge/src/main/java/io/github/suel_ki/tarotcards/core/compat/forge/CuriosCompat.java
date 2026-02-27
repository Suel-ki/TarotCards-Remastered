package io.github.suel_ki.tarotcards.core.compat.forge;

import io.github.suel_ki.tarotcards.core.init.ItemInit;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.CuriosApi;

public class CuriosCompat {

    public static ItemStack getCurioStack(Player player, Item item) {
        return CuriosApi.getCuriosInventory(player).map(handler -> {
            var result = handler.findFirstCurio(item);
            return result.isPresent() ? result.get().stack() : ItemStack.EMPTY;
        }).orElse(ItemStack.EMPTY);
    }

    public static ItemStack getTarotDeckCurio(Player player) {
        return getCurioStack(player, ItemInit.tarot_deck.get());
    }

    public static ItemStack getTarotCardCurio(Player player, Item tarotItem) {
        return getCurioStack(player, tarotItem);
    }
}
