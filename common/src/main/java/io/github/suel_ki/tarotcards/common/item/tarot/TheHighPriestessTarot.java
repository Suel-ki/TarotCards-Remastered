package io.github.suel_ki.tarotcards.common.item.tarot;

import io.github.suel_ki.tarotcards.TarotCards;
import io.github.suel_ki.tarotcards.common.item.TarotItem;
import io.github.suel_ki.tarotcards.core.init.ItemInit;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.EnchantmentTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.ItemEnchantments;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class TheHighPriestessTarot extends TarotItem {

    private static final TagKey<Item> upgradable_enchantment = TagKey.create(Registries.ITEM, TarotCards.id("upgradable_enchantment"));

    public TheHighPriestessTarot(Properties properties) {
        super(properties);
    }

    // TODO: Reversed card logic - Downgrade enchantment? (Unsure if needed)
    @Override
    protected void handleExtraLogic(Player player, boolean hasCard) {
        if (hasCard) {

            // Get the held item to upgrade
            ItemStack upgradableItem = player.getMainHandItem().is(upgradable_enchantment) ? player.getMainHandItem() : (player.getOffhandItem().is(upgradable_enchantment) ? player.getOffhandItem() : null);
            if (upgradableItem == null) {
                return;
            }

            ItemEnchantments enchantments = EnchantmentHelper.getEnchantmentsForCrafting(upgradableItem);

            if (enchantments.isEmpty()) {
                return;
            }

            int baseCost = TarotCards.CONFIG.cards.the_highpriestess_upgradecost;
            int extraLevels = TarotCards.CONFIG.cards.the_highpriestess_extra_levels;
            boolean capEnchs = TarotCards.CONFIG.cards.the_highpriestess_capenchants;
            boolean isTaxFree = TarotCards.CONFIG.cards.the_highpriestess_taxfree;
            boolean skipMaxed = TarotCards.CONFIG.cards.the_highpriestess_skip_maxed;

            List<Holder<Enchantment>> orderedList = getVisualOrderEnchantments(player.level().registryAccess(), enchantments);
            Holder<Enchantment> firstVisible = orderedList.isEmpty() ? null : orderedList.getFirst();
            Holder<Enchantment> targetEnch = null;
            int currentLvl = 0;


            for (Holder<Enchantment> holder : orderedList) {
                int lvl = enchantments.getLevel(holder);
                int max = capEnchs ? (holder.value().getMaxLevel() + extraLevels) : holder.value().getMaxLevel();

                if (lvl < max) {
                    targetEnch = holder;
                    currentLvl = lvl;
                    if (skipMaxed) {
                        break;
                    } else {
                        if (!holder.equals(firstVisible)) {
                            targetEnch = null;
                        }
                        break;
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
                    TarotCards.LOGGER.debug("{} - Upgraded (SkipMaxed: {}): {} ({} -> {})",
                            ItemInit.the_high_priestess.get(), skipMaxed, targetEnch.getRegisteredName(), currentLvl, currentLvl + 1);

                    if (isTaxFree) {
                        player.giveExperiencePoints(-xpPoints);
                    } else {
                        player.giveExperienceLevels(-cost);
                    }

                    applyUpgradeEffects(player, upgradableItem, targetEnch, currentLvl);
                }
            }
        }
    }

    private void applyUpgradeEffects(Player player, ItemStack item, Holder<Enchantment> ench, int currentLvl) {
        player.level().playSound(null, player.getX(), player.getY(), player.getZ(),
                SoundEvents.PLAYER_LEVELUP, SoundSource.PLAYERS, 0.5F, 1.5F);
        EnchantmentHelper.updateEnchantments(item, mutable -> {
            mutable.set(ench, currentLvl + 1);
        });
    }

    private List<Holder<Enchantment>> getVisualOrderEnchantments(HolderLookup.Provider registries, ItemEnchantments enchantments) {
        List<Holder<Enchantment>> list = new ArrayList<>();

        Optional<HolderSet.Named<Enchantment>> tooltipOrder = registries.lookupOrThrow(Registries.ENCHANTMENT)
                .get(EnchantmentTags.TOOLTIP_ORDER);

        tooltipOrder.ifPresent(tag -> {
            for (Holder<Enchantment> holder : tag) {
                if (enchantments.getLevel(holder) > 0) {
                    list.add(holder);
                }
            }
        });

        for (Holder<Enchantment> holder : enchantments.keySet()) {
            if (!list.contains(holder)) {
                list.add(holder);
            }
        }
        return list;
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
