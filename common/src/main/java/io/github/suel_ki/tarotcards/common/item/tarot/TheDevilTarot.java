package io.github.suel_ki.tarotcards.common.item.tarot;

import io.github.suel_ki.tarotcards.TarotCards;
import io.github.suel_ki.tarotcards.common.item.TarotItem;
import io.github.suel_ki.tarotcards.core.init.ItemInit;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class TheDevilTarot extends TarotItem {

    public TheDevilTarot(Properties properties) {
        super(properties);
    }

    @Override
    public float onAttack(Player attacker, LivingEntity victim, DamageSource source, float amount) {
        if (attacker!= null) {

            TarotCards.LOGGER.debug("{} - Inflict weakness", ItemInit.the_devil.get());
            TarotCards.LOGGER.debug("From: {}, To: {}", attacker, victim);

            victim.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 60, TarotCards.CONFIG.cards.the_devil_weaknessamplifier));
        }
        return amount;
    }

}
