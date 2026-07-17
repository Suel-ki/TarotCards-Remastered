package io.github.suel_ki.tarotcards.core.helper;

import io.github.suel_ki.tarotcards.TarotCards;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;

public class TargetingHelper {
    public enum EffectType {
        POSITIVE, NEGATIVE
    }

    public static TargetingConditions.Selector getFilter(LivingEntity source, EffectType type) {
        boolean basedTargeting = TarotCards.CONFIG.effect_based_targeting;

        return (target, level) -> {
            if (target == source) return false;

            if (!target.isAlive()) return false;

            if (basedTargeting) {
                if (type == EffectType.POSITIVE) {
                    return target instanceof Player || isAlly(source, target);
                } else {
                    return target instanceof Enemy || (target instanceof Mob mob && mob.getTarget() == source);
                }
            }

            boolean isAllied = isAlly(source, target);
            return type == EffectType.POSITIVE ? isAllied : !isAllied;
        };
    }

    public static boolean isAlly(LivingEntity source, LivingEntity target) {
        if (target.isAlliedTo(source) || source.isAlliedTo(target)) {
            return true;
        }

        if (target instanceof OwnableEntity ownable) {
            var owner = ownable.getOwner();
            if (owner != null) {
                if (owner.equals(source)) {
                    return true;
                }

                if (source.isAlliedTo(owner)) {
                    return true;
                }
            }
        }

        if (target instanceof Player tPlayer && source instanceof Player sPlayer) {
            if (!sPlayer.canHarmPlayer(tPlayer)) {
                return true;
            }
        }

        return false;
    }
}