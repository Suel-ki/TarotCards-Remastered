package io.github.suel_ki.tarotcards.common.item.tarot;

import io.github.suel_ki.tarotcards.TarotCards;
import io.github.suel_ki.tarotcards.common.item.TarotItem;
import io.github.suel_ki.tarotcards.core.init.ItemInit;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class JudgementTarot extends TarotItem {

    public static float handleOnDamage(LivingEntity entity, DamageSource source, float amount)  {
        if (source.getEntity() instanceof Player attacker) {
            if (hasTarot(attacker, ItemInit.judgement.get())) {
                if (attacker.getRandom().nextDouble() < TarotCards.CONFIG.cards.judgement_damagechance) {

                    float new_damage = amount * 2;

                    TarotCards.LOGGER.debug("{} - Chance to double damage", ItemInit.judgement.get());
                    TarotCards.LOGGER.debug("From: {}, To: {}", attacker, entity);
                    TarotCards.LOGGER.debug("Amount: {} to {}", amount, new_damage);

                    attacker.level().playSound(null, attacker.blockPosition(), SoundEvents.SMALL_AMETHYST_BUD_BREAK, SoundSource.PLAYERS, 1f, attacker.getRandom().nextFloat() * 0.2f + 0.5f);
                    return new_damage;
                }
            }
        }
        return amount;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.translatable(this.getDescriptionId() + ".desc", TarotCards.CONFIG.cards.judgement_damagechance * 100).withStyle(ChatFormatting.BLUE));
    }

}
