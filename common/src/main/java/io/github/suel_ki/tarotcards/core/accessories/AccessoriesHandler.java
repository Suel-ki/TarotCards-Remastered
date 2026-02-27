package io.github.suel_ki.tarotcards.core.accessories;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class AccessoriesHandler {

    @ExpectPlatform
    public static boolean hasAccessoryActivated(Player player, Item tarot) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static ItemStack getDeck(Player player) {
        throw new AssertionError();
    }
}
