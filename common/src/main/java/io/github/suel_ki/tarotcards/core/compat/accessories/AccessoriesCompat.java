package io.github.suel_ki.tarotcards.core.compat.accessories;

import io.github.suel_ki.tarotcards.core.init.ItemInit;
import io.wispforest.accessories.api.AccessoriesCapability;
import io.wispforest.accessories.api.slot.SlotEntryReference;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.Optional;

public class AccessoriesCompat {

    public static ItemStack getAccessoriesStack(Player player, Item item) {
        return AccessoriesCapability.getOptionally(player)
                .flatMap(cap -> Optional.ofNullable(cap.getFirstEquipped(item)))
                .map(SlotEntryReference::stack)
                .filter(stack -> !stack.isEmpty())
                .orElse(ItemStack.EMPTY);
    }

    public static ItemStack getTarotDeckAccessory(Player player) {
        return getAccessoriesStack(player, ItemInit.tarot_deck.get());
    }

    public static ItemStack getTarotCardAccessories(Player player, Item tarotItem) {
        return getAccessoriesStack(player, tarotItem);
    }
}
