package io.github.suel_ki.tarotcards.common.item.tarot;

import io.github.suel_ki.tarotcards.TarotCards;
import io.github.suel_ki.tarotcards.common.item.TarotItem;
import io.github.suel_ki.tarotcards.core.init.ItemInit;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;

import java.util.List;

public class TheEmpressTarot extends TarotItem {

    @Override
    protected void handleExtraLogic(LivingEntity entity, boolean hasCard) {
        if (hasCard && !entity.level().isClientSide()) {
            double range = TarotCards.CONFIG.cards.the_empress_range;
            boolean breeding = TarotCards.CONFIG.cards.the_empress_breeding_enabled;

            List<Animal> nearbyAnimals = entity.level().getNearbyEntities(Animal.class, TargetingConditions.forNonCombat(), entity, entity.getBoundingBox().inflate(range));

            for (Animal e : nearbyAnimals) {
                if (e.isBaby()) {

                    TarotCards.LOGGER.debug("{} - Feed baby animal: {}", ItemInit.the_empress.get(), e);

                    e.ageUp(AgeableMob.getSpeedUpSecondsWhenFeeding(-e.getAge()), true);
                } else if (breeding && entity instanceof Player player && e.canFallInLove()) {

                    TarotCards.LOGGER.debug("{} - Set animal in love: {}", ItemInit.the_empress.get(), e);

                    e.setInLove(player);
                }
            }
        }
    }

}
