package io.github.suel_ki.tarotcards.core.accessories.fabric;

import io.github.suel_ki.tarotcards.core.compat.fabric.TrinketsCompat;
import io.github.suel_ki.tarotcards.core.platform.TarotUtilPlatform;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import static io.github.suel_ki.tarotcards.common.item.TarotItem.isActivated;

public class AccessoriesHandlerImpl {
    private static final boolean LOADED = TarotUtilPlatform.isLoaded("trinkets");

    public static boolean hasAccessoryActivated(Player player, Item tarot) {
        if (LOADED) {
            ItemStack singlecard = TrinketsCompat.getTrinketStack(player, tarot);
            return !singlecard.isEmpty() && isActivated(singlecard);
        }
        return false;
    }

    public static ItemStack getDeck(Player player) {
        if (LOADED) {
            return TrinketsCompat.getTarotDeckTrinket(player);
        }
        return ItemStack.EMPTY;
    }
}
