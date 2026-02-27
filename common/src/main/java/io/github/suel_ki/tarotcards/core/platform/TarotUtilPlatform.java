package io.github.suel_ki.tarotcards.core.platform;

import dev.architectury.injectables.annotations.ExpectPlatform;
import io.github.suel_ki.tarotcards.TarotCards;
import io.github.suel_ki.tarotcards.common.item.TarotItem;
import io.github.suel_ki.tarotcards.core.accessories.AccessoriesHandler;
import io.github.suel_ki.tarotcards.core.init.ItemInit;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;

import static io.github.suel_ki.tarotcards.common.item.TarotItem.isActivated;

public class TarotUtilPlatform {

    @ExpectPlatform
    public static boolean isLoaded(String id) {
        return FabricLoader.getInstance().isModLoaded(id);
    }

    @ExpectPlatform
    public static void openTarotMenu(ServerPlayer player, ItemStack stack) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static Supplier<Attribute> BLOCK_REACH() {
        throw new AssertionError();
    }

    /**
     * Scans player inventory and the tarot deck (nbt) in one go.
     * @return A Set of all active Tarot Items currently possessed by the player.
     */
    public static Set<Item> getActiveTarots(Player player) {
        Set<Item> activeCards = new HashSet<>();
        if (player == null) return activeCards;

        for (int i = 0; i < TarotItem.ITEMS_CARDS.size(); i++) {
            TarotItem tarot = TarotItem.ITEMS_CARDS.get(i);
            if (AccessoriesHandler.hasAccessoryActivated(player, tarot)) {
                activeCards.add(tarot);
            }
        }

        // If they have the deck in a curio slot, save it for checking later
        ItemStack deckStack = AccessoriesHandler.getDeck(player);

        // Only search the inventory if config allows it
        if (!TarotCards.CONFIG.require_card_in_curio) {
            Inventory inv = player.getInventory();
            // Check player for card and deck
            for (int i = 0; i < inv.getContainerSize(); i++) {
                ItemStack stack = inv.getItem(i);
                if (stack.isEmpty()) continue;
                // If we find the card, return it
                // if we find the deck, remember it
                Item item = stack.getItem();

                if (item instanceof TarotItem && isActivated(stack)) {
                    activeCards.add(item);
                } else if (stack.is(ItemInit.tarot_deck.get())) { // This will choose player inventory decks over curios
                    deckStack = stack;
                }
            }
        }

        // Check tarot deck for card if config allows it
        if (deckStack != null && TarotCards.CONFIG.tarot_deck_applies_effects) {
            CompoundTag nbt = deckStack.getTag();
            // Check if the NBT contains the inventory data
            if (nbt != null && nbt.contains("TarotDeckInventory")) {
                ListTag listTag = nbt.getCompound("TarotDeckInventory").getList("Items", Tag.TAG_COMPOUND);

                for (int i = 0; i < listTag.size(); i++) {
                    CompoundTag itemTag = listTag.getCompound(i);
                    // Light check to see if it's a tarot card before full parsing
                    if (itemTag.getString("id").contains("tarot")) {
                        ItemStack innerStack = ItemStack.of(itemTag);
                        if (innerStack.getItem() instanceof TarotItem && isActivated(innerStack)) {
                            activeCards.add(innerStack.getItem());
                        }
                    }
                }
            }
        }

        return activeCards;
    }

    public static float handleOnHurt(@Nullable LivingEntity attacker, Player player, DamageSource source, float amount) {
        if (amount <= 0) return 0;

        Set<Item> activeCards = getActiveTarots(player);

        if (activeCards.isEmpty()) {
            return amount;
        }

        for (Item item : activeCards) {
            if (item instanceof TarotItem tarot) {
               amount = tarot.onHurt(attacker, player, source, amount);
            }
        }
        return amount;
    }

    public static float handleOnAttack(Player player, LivingEntity victim, DamageSource source, float amount) {
        if (amount <= 0) return 0;

        Set<Item> activeCards = getActiveTarots(player);

        if (activeCards.isEmpty()) {
            return amount;
        }

        for (Item item : activeCards) {
            if (item instanceof TarotItem tarot) {
                amount = tarot.onAttack(player, victim, source, amount);
            }
        }
        return amount;
    }

//    public static float handleOnDamage(LivingEntity victim, DamageSource source, float amount) {
//        if (amount <= 0) return amount;
//
//        float currentAmount = amount;
//        Entity attacker = source.getEntity();
//
//        if (attacker instanceof Player playerAttacker) {
//            Set<Item> attackerCards = getActiveTarots(playerAttacker);
//            for (Item item : attackerCards) {
//                if (item instanceof TarotItem tarot) {
//                    currentAmount = tarot.onDamage(victim, source, currentAmount);
//                }
//            }
//        }
//
//        if (victim instanceof Player playerVictim) {
//            Set<Item> victimCards = getActiveTarots(playerVictim);
//            for (Item item : victimCards) {
//                if (item instanceof TarotItem tarot) {
//                    currentAmount = tarot.onDamage(playerVictim, source, currentAmount);
//                }
//            }
//        }
//
//        return currentAmount;
//    }
}
