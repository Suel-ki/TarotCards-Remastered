package io.github.suel_ki.tarotcards.common.item.tarot;

import io.github.suel_ki.tarotcards.TarotCards;
import io.github.suel_ki.tarotcards.common.item.TarotItem;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;

import java.util.function.Supplier;

public class TheMoonTarot extends TarotItem {

    private static final Supplier<MobEffectInstance> night_vision = () -> new MobEffectInstance(MobEffects.NIGHT_VISION, 311 + TarotCards.CONFIG.tick_rate, 0, true, false);

    @Override
    protected void handleExtraLogic(LivingEntity entity, boolean hasCard) {
        if (hasCard) {
            entity.addEffect(night_vision.get());
        }
    }
}
