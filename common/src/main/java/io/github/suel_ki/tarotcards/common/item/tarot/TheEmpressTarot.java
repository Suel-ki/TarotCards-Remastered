package io.github.suel_ki.tarotcards.common.item.tarot;

import io.github.suel_ki.tarotcards.TarotCards;
import io.github.suel_ki.tarotcards.common.item.TarotItem;
import io.github.suel_ki.tarotcards.core.init.ItemInit;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;

import java.util.List;

public class TheEmpressTarot extends TarotItem {

    public TheEmpressTarot(Properties properties) {
        super(properties);
    }

    @Override
    protected void handleExtraLogic(Player player, boolean hasCard) {
        if (hasCard) {
            if (player.level() instanceof ServerLevel level) {
                double range = TarotCards.CONFIG.cards.the_empress_range;
                boolean breeding = TarotCards.CONFIG.cards.the_empress_breeding_enabled;

                List<Animal> nearbyAnimals = level.getNearbyEntities(Animal.class, TargetingConditions.forNonCombat(), player, player.getBoundingBox().inflate(range));
                for (Animal e : nearbyAnimals) {
                    if (e.isBaby()) {

                        TarotCards.LOGGER.debug("{} - Feed baby", ItemInit.the_empress.get());
                        TarotCards.LOGGER.debug("Animal: {}", e);
                        TarotCards.LOGGER.debug("{} - Feed baby animal: {}", ItemInit.the_empress.get(), e);

                        e.ageUp(AgeableMob.getSpeedUpSecondsWhenFeeding(-e.getAge()), true);
                    } else if (breeding && e.canFallInLove()) {

                        TarotCards.LOGGER.debug("{} - Set animal in love: {}", ItemInit.the_empress.get(), e);

                        e.setInLove(player);
                    }
                }
            }
        }
    }

}
