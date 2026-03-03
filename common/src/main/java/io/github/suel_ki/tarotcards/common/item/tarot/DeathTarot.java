package io.github.suel_ki.tarotcards.common.item.tarot;

import io.github.suel_ki.tarotcards.TarotCards;
import io.github.suel_ki.tarotcards.common.item.TarotItem;
import io.github.suel_ki.tarotcards.core.init.ItemInit;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class DeathTarot extends TarotItem {

    public float onAttack(Player attacker, LivingEntity victim, DamageSource source, float amount) {
        if (victim.getType().is(EntityTypeTags.UNDEAD)) {
            return amount;
        }

        float new_damage = (float) (amount * (1 + TarotCards.CONFIG.cards.death_damagebonus));

        TarotCards.LOGGER.debug("{} - Damage to non-undead", ItemInit.death.get());
        TarotCards.LOGGER.debug("From: {}, To: {}", attacker, victim);
        TarotCards.LOGGER.debug("Amount: {} to {}", amount, new_damage);

        return new_damage;
    }

	@Override
	public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.translatable(this.getDescriptionId() + ".desc", TarotCards.CONFIG.cards.death_damagebonus * 100).withStyle(ChatFormatting.BLUE));
    }

}
