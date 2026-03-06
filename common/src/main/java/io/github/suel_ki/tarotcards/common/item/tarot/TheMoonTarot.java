package io.github.suel_ki.tarotcards.common.item.tarot;

import io.github.suel_ki.tarotcards.TarotCards;
import io.github.suel_ki.tarotcards.common.item.TarotItem;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;

import java.util.function.Supplier;

public class TheMoonTarot extends TarotItem {

    private static final Supplier<MobEffectInstance> night_vision = () -> new MobEffectInstance(MobEffects.NIGHT_VISION, 311 + TarotCards.CONFIG.tick_rate, 0, true, false);

    public TheMoonTarot(Properties properties) {
        super(properties);
    }

    @Override
    protected void handleExtraLogic(Player player, boolean hasCard) {
        if (hasCard) {
            player.addEffect(night_vision.get());
        }
    }
}
