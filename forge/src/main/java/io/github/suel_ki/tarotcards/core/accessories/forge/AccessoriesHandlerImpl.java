package io.github.suel_ki.tarotcards.core.accessories.forge;

import io.github.suel_ki.tarotcards.core.compat.forge.CuriosCompat;
import io.github.suel_ki.tarotcards.core.platform.TarotUtilPlatform;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import static io.github.suel_ki.tarotcards.common.item.TarotItem.isActivated;

public class AccessoriesHandlerImpl {
    private static final boolean LOADED = TarotUtilPlatform.isLoaded("curios");

    public static boolean hasAccessoryActivated(LivingEntity entity, Item tarot) {
        if (LOADED) {
            ItemStack singlecard = CuriosCompat.getTarotCardCurio(entity, tarot);
            return !singlecard.isEmpty() && isActivated(singlecard);
        }
        return false;
    }

    public static ItemStack getDeck(LivingEntity entity) {
        if (LOADED) {
            return CuriosCompat.getTarotDeckCurio(entity);
        }
        return ItemStack.EMPTY;
    }
}
