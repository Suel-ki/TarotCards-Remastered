package io.github.suel_ki.tarotcards.core.accessories;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import static io.github.suel_ki.tarotcards.common.item.TarotItem.isActivated;

public class AccessoriesHandler {

    public static boolean hasAccessoryActivated(Player player, Item tarot) {
        ItemStack stack = getAccessoryStack(player, tarot);
        return !stack.isEmpty() && isActivated(stack);
    }

    @ExpectPlatform
    public static ItemStack getAccessoryStack(Player player, Item item) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static ItemStack getDeck(Player player) {
        throw new AssertionError();
    }
}
