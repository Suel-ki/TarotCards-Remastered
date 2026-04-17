package io.github.suel_ki.tarotcards.common.item.tarot;

import io.github.suel_ki.tarotcards.TarotCards;
import io.github.suel_ki.tarotcards.common.item.TarotItem;
import io.github.suel_ki.tarotcards.core.init.ItemInit;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;

public class TheEmpressTarot extends TarotItem {

    @Override
    protected void handleExtraLogic(LivingEntity entity, boolean hasCard) {
        if (hasCard) {
            entity.level().getNearbyEntities(Animal.class, TargetingConditions.forNonCombat(), entity, entity.getBoundingBox().inflate(TarotCards.CONFIG.cards.the_empress_range)).forEach(e -> {
                if (entity instanceof Player player && e.canFallInLove() && e.getAge() == 0) {

                    TarotCards.LOGGER.debug("{} - Set in love", ItemInit.the_empress.get());
                    TarotCards.LOGGER.debug("Animal: {}", e);

                    e.setInLove(player);
                }
                if (e.isBaby()) {

                    TarotCards.LOGGER.debug("{} - Feed baby", ItemInit.the_empress.get());
                    TarotCards.LOGGER.debug("Animal: {}", e);

                    e.ageUp(AgeableMob.getSpeedUpSecondsWhenFeeding(-e.getAge()), true);
                }
            });
        }
    }

}
