package io.github.suel_ki.tarotcards.core.platform;

import dev.architectury.injectables.annotations.ExpectPlatform;
import io.github.suel_ki.tarotcards.TarotCards;
import io.github.suel_ki.tarotcards.common.item.TarotItem;
import io.github.suel_ki.tarotcards.core.accessories.AccessoriesHandler;
import io.github.suel_ki.tarotcards.core.compat.TouhouLittleMaidCompat;
import io.github.suel_ki.tarotcards.core.init.ItemInit;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemContainerContents;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Set;

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

    public static boolean isValidTarget(LivingEntity entity) {
        return entity instanceof Player || TouhouLittleMaidCompat.isMaid(entity);
    }

    /**
     * Scans player inventory and the tarot deck (nbt) in one go.
     * @return A Set of all active Tarot Items currently possessed by the player.
     */
    public static Set<Item> getActiveTarots(LivingEntity entity) {
        Set<Item> activeCards = new HashSet<>();

        if (!isValidTarget(entity)) {
            return activeCards;
        }

        for (int i = 0; i < TarotItem.ITEMS_CARDS.size(); i++) {
            TarotItem tarot = TarotItem.ITEMS_CARDS.get(i);
            if (AccessoriesHandler.hasAccessoryActivated(entity, tarot)) {
                activeCards.add(tarot);
            }
        }

        // If they have the deck in a curio slot, save it for checking later
        ItemStack deckStack = AccessoriesHandler.getDeck(entity);

        // Only search the inventory if config allows it
        if (entity instanceof Player player) {
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
        }

        // Check tarot deck for card if config allows it
        if (deckStack != null && TarotCards.CONFIG.tarot_deck_applies_effects && deckStack.has(DataComponents.CONTAINER)) {
            ItemContainerContents contents = deckStack.getOrDefault(DataComponents.CONTAINER, ItemContainerContents.EMPTY);
            for (ItemStack stack : contents.nonEmptyItems()) {
                if (stack.getItem() instanceof TarotItem && isActivated(stack)) {
                   activeCards.add(stack.getItem());
                }
            }
        }

        return activeCards;
    }

    private static float processDamage(LivingEntity entity, @Nullable LivingEntity other, DamageSource source, float amount, TarotEffectTrigger trigger) {
        if (amount <= 0 || !isValidTarget(entity)) return amount;

        Set<Item> activeCards = getActiveTarots(entity);
        if (activeCards.isEmpty()) return amount;

        float finalAmount = amount;
        for (Item item : activeCards) {
            if (item instanceof TarotItem tarot && tarot.checkExtraConditions(entity)) {
                finalAmount = trigger.apply(tarot, other, entity, source, finalAmount);
            }
        }

        return finalAmount;
    }

    public static float handleOnHurt(@Nullable LivingEntity attacker, LivingEntity victim, DamageSource source, float amount) {
        return processDamage(victim, attacker, source, amount,
                (tarot, att, vic, src, amt) -> tarot.onHurt(att, vic, src, amt));
    }

    public static float handleOnAttack(LivingEntity attacker, LivingEntity victim, DamageSource source, float amount) {
        return processDamage(attacker, victim, source, amount,
                (tarot, vic, att, src, amt) -> tarot.onAttack(att, vic, src, amt));
    }

    @FunctionalInterface
    interface TarotEffectTrigger {
        float apply(TarotItem tarot, @Nullable LivingEntity attacker, LivingEntity victim, DamageSource source, float amount);
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
