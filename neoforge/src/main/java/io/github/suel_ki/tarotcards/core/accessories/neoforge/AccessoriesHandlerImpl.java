package io.github.suel_ki.tarotcards.core.accessories.neoforge;

import io.github.suel_ki.tarotcards.core.compat.accessories.AccessoriesCompat;
import io.github.suel_ki.tarotcards.core.compat.neoforge.CuriosCompat;
import io.github.suel_ki.tarotcards.core.platform.TarotUtilPlatform;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class AccessoriesHandlerImpl {
    private static final boolean CURIOS_LOADED = TarotUtilPlatform.isLoaded("curios");
    private static final boolean ACCESSORIES_LOADED = TarotUtilPlatform.isLoaded("accessories");

    public static ItemStack getAccessoryStack(Player player, Item item) {
        if (CURIOS_LOADED) {
            return CuriosCompat.getTarotCardCurio(player, item);
        }
//        if (ACCESSORIES_LOADED) {
//            return AccessoriesCompat.getAccessoriesStack(player, item);
//        }
        return ItemStack.EMPTY;
    }

    public static ItemStack getDeck(Player player) {
        if (CURIOS_LOADED) {
            return CuriosCompat.getTarotDeckCurio(player);
        }
//        if (ACCESSORIES_LOADED) {
//            return AccessoriesCompat.getTarotDeckAccessory(player);
//        }
        return ItemStack.EMPTY;
    }
}
