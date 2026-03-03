package io.github.suel_ki.tarotcards.common.item.tarot;

import io.github.suel_ki.tarotcards.TarotCards;
import io.github.suel_ki.tarotcards.common.item.TarotItem;
import io.github.suel_ki.tarotcards.core.init.ItemInit;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.ItemEnchantments;

public class TheHighPriestessTarot extends TarotItem {

    private static final TagKey<Item> upgradable_enchantment = TagKey.create(Registries.ITEM, TarotCards.id("upgradable_enchantment"));

    @Override
    protected void handleExtraLogic(Player player, boolean hasCard) {
        if (hasCard) {

            //Get the held item to upgrade
            ItemStack upgradable_item = player.getMainHandItem().is(upgradable_enchantment) ? player.getMainHandItem() : (player.getOffhandItem().is(upgradable_enchantment) ? player.getOffhandItem() : null);
            if (upgradable_item == null) {
                return;
            }

            ItemEnchantments enchantments = EnchantmentHelper.getEnchantmentsForCrafting(upgradable_item);

            if (enchantments.isEmpty()) return;

            int baseCost = TarotCards.CONFIG.cards.the_highpriestess_upgradecost;
            int extraLevels = TarotCards.CONFIG.cards.the_highpriestess_extra_levels;
            boolean capEnchs = TarotCards.CONFIG.cards.the_highpriestess_capenchants;

            ItemEnchantments.Mutable mutableEnchants = new ItemEnchantments.Mutable(enchantments);

            for (Holder<Enchantment> enchHolder : enchantments.keySet()) {
                Enchantment ench = enchHolder.value();
                int currentLvl = enchantments.getLevel(enchHolder);

                int finalMaxLevel = capEnchs ? (ench.getMaxLevel() + extraLevels) : ench.getMaxLevel();

                if (currentLvl < finalMaxLevel) {

                    int cost = baseCost * currentLvl;

                    if (player.experienceLevel >= cost && cost > 0) {
                        TarotCards.LOGGER.debug("{} - Enchantment upgrade", ItemInit.the_high_priestess.get());
                        TarotCards.LOGGER.debug("From: {}, To: {}, Cost: {}", enchHolder.getRegisteredName(), currentLvl + 1, cost);

                        player.giveExperienceLevels(-cost);
                        player.level().playSound(null, player.getX(), player.getY(), player.getZ(),
                                SoundEvents.PLAYER_LEVELUP, SoundSource.PLAYERS, 0.5F, 1.5F);
                        mutableEnchants.set(enchHolder, currentLvl + 1);
                        EnchantmentHelper.setEnchantments(upgradable_item, mutableEnchants.toImmutable());
                        break;  //Get the first valid enchantment to level up
                    }
                }
            }
        }
    }

}
