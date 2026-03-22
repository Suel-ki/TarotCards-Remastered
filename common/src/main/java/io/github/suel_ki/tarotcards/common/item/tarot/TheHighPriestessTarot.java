package io.github.suel_ki.tarotcards.common.item.tarot;

import io.github.suel_ki.tarotcards.TarotCards;
import io.github.suel_ki.tarotcards.common.item.TarotItem;
import io.github.suel_ki.tarotcards.core.init.ItemInit;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;

import java.util.Map;
import java.util.Optional;

public class TheHighPriestessTarot extends TarotItem {

    private static final TagKey<Item> upgradable_enchantment = TagKey.create(Registries.ITEM, TarotCards.id("upgradable_enchantment"));

    // TODO: Reversed card logic - Downgrade enchantment? (Unsure if needed)
    @Override
    protected void handleExtraLogic(Player player, boolean hasCard) {
        if (hasCard) {

            //Get the held item to upgrade
            ItemStack upgradableItem = player.getMainHandItem().is(upgradable_enchantment) ? player.getMainHandItem() : (player.getOffhandItem().is(upgradable_enchantment) ? player.getOffhandItem() : null);
            if (upgradableItem == null) {
                return;
            }

            Map<Enchantment, Integer> enchantments = EnchantmentHelper.getEnchantments(upgradableItem);

            if (enchantments.isEmpty()) {
                return;
            }

            int baseCost = TarotCards.CONFIG.cards.the_highpriestess_upgradecost;
            int extraLevels = TarotCards.CONFIG.cards.the_highpriestess_extra_levels;
            boolean capEnchs = TarotCards.CONFIG.cards.the_highpriestess_capenchants;
            boolean isTaxFree = TarotCards.CONFIG.cards.the_highpriestess_taxfree;
            boolean skipMaxed = TarotCards.CONFIG.cards.the_highpriestess_skip_maxed;

            Enchantment targetEnch = null;
            int currentLvl = 0;

            if (skipMaxed) {
                // Find the first enchantment that hasn't reached its level cap
                for (Map.Entry<Enchantment, Integer> entry : enchantments.entrySet()) {
                    int lvl = entry.getValue();
                    int max = capEnchs ? (entry.getKey().getMaxLevel() + extraLevels) : entry.getKey().getMaxLevel();
                    if (lvl < max) {
                        targetEnch = entry.getKey();
                        currentLvl = lvl;
                        break; // Target found, exit search
                    }
                }
            } else {
                // Get the first valid enchantment to level up
                Optional<Map.Entry<Enchantment, Integer>> first = enchantments.entrySet().stream().findFirst();
                if (first.isPresent()) {
                    int lvl = first.get().getValue();
                    int max = capEnchs ? (first.get().getKey().getMaxLevel() + extraLevels) : first.get().getKey().getMaxLevel();
                    if (lvl < max) {
                        targetEnch = first.get().getKey();
                        currentLvl = lvl;
                    }
                }
            }

            if (targetEnch != null) {
                int cost = baseCost * currentLvl;
                if (cost <= 0) {
                    return;
                }

                // We don't trust 'player.totalExperience' because it often goes out of sync
                // when levels are set via commands (/xp set) or other mods.
                int currentTotalXp = getTotalXpFromZero(player.experienceLevel) +
                        Math.round(player.experienceProgress * player.getXpNeededForNextLevel());

                int xpPoints;
                if (isTaxFree) {
                    xpPoints = getTotalXpFromZero(cost);
                } else {
                    xpPoints = 0;
                }
                boolean canAfford = isTaxFree ? (currentTotalXp >= xpPoints) : (player.experienceLevel >= cost);

                if (canAfford) {
                    TarotCards.LOGGER.debug("{} - Upgraded (SkipMaxed: {}, TaxFree: {}): {} ({} -> {}) | Cost: {} {}",
                            ItemInit.the_high_priestess.get().getDescriptionId(),
                            skipMaxed,
                            isTaxFree,
                            targetEnch.getDescriptionId(),
                            currentLvl,
                            currentLvl + 1,
                            isTaxFree ? xpPoints : cost,
                            isTaxFree ? "XP Points" : "Levels"
                    );

                    if (isTaxFree) {
                        player.giveExperiencePoints(-xpPoints);
                    } else {
                        player.giveExperienceLevels(-cost);
                    }

                    applyUpgradeEffects(player, upgradableItem, enchantments, targetEnch, currentLvl);
                }
            }
        }
    }

    private void applyUpgradeEffects(Player player, ItemStack item, Map<Enchantment, Integer> enchantments, Enchantment ench, int currentLvl) {
        player.level().playSound(null, player.getX(), player.getY(), player.getZ(),
                SoundEvents.PLAYER_LEVELUP, SoundSource.PLAYERS, 0.5F, 1.5F);
        enchantments.put(ench, currentLvl + 1);
        EnchantmentHelper.setEnchantments(enchantments, item);
    }

    /**
     * Calculates the total cumulative XP required to reach a specific level starting from zero
     * This logic mirrors the cumulative sum of {@link Player#getXpNeededForNextLevel()}
     *
     * @param targetLevel The target experience level to calculate total XP for
     * @return The total cumulative XP points needed to reach the given target level from level 0
     */
    private int getTotalXpFromZero(int targetLevel) {
        int totalXp = 0;
        for (int i = 0; i < targetLevel; i++) {
            if (i >= 30) {
                totalXp += 112 + (i - 30) * 9;
            } else if (i >= 15) {
                totalXp += 37 + (i - 15) * 5;
            } else {
                totalXp += 7 + i * 2;
            }
        }
        return totalXp;
    }

}
