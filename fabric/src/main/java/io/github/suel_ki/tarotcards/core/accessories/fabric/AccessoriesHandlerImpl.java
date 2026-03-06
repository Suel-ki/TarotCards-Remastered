package io.github.suel_ki.tarotcards.core.accessories.fabric;

import io.github.suel_ki.tarotcards.core.compat.accessories.AccessoriesCompat;
import io.github.suel_ki.tarotcards.core.compat.fabric.TrinketsCompat;
import io.github.suel_ki.tarotcards.core.platform.TarotUtilPlatform;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class AccessoriesHandlerImpl {
    private static final boolean TRINKETS_LOADED = TarotUtilPlatform.isLoaded("trinkets");
    private static final boolean ACCESSORIES_LOADED = TarotUtilPlatform.isLoaded("accessories");

    public static ItemStack getAccessoryStack(Player player, Item item) {
        if (TRINKETS_LOADED) {
            return TrinketsCompat.getTarotCardTrinket(player, item);
        }
//        if (ACCESSORIES_LOADED) {
//            return AccessoriesCompat.getAccessoriesStack(player, item);
//        }
        return ItemStack.EMPTY;
    }

    public static ItemStack getDeck(Player player) {
        if (TRINKETS_LOADED) {
            return TrinketsCompat.getTarotDeckTrinket(player);
        }
//        if (ACCESSORIES_LOADED) {
//            return AccessoriesCompat.getTarotDeckAccessory(player);
//        }
        return ItemStack.EMPTY;
    }
}
