package io.github.suel_ki.tarotcards.core.helper;

import io.github.suel_ki.tarotcards.TarotCards;
import io.github.suel_ki.tarotcards.core.compat.FTBTeamCompat;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;

import java.util.UUID;
import java.util.function.Predicate;

public class TargetingHelper {
    public enum EffectType {
        POSITIVE, NEGATIVE
    }

    public static Predicate<LivingEntity> getFilter(LivingEntity source, EffectType type) {
        boolean basedTargeting = TarotCards.CONFIG.effect_based_targeting;

        return target -> {
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
            UUID ownerUUID = ownable.getOwnerUUID();
            if (ownerUUID != null) {
                if (ownerUUID.equals(source.getUUID())) {
                    return true;
                }

                LivingEntity owner = ownable.getOwner();
                if (owner != null && source.isAlliedTo(owner)) {
                    return true;
                }

                if (source instanceof Player player && FTBTeamCompat.isSameTeamByUUID(player.getUUID(), ownerUUID)) {
                    return true;
                }
            }
        }

        if (target instanceof Player tPlayer && source instanceof Player sPlayer) {
            if (!sPlayer.canHarmPlayer(tPlayer)) {
                return true;
            }
            if (FTBTeamCompat.isSameTeamSafe(sPlayer, tPlayer)) {
                return true;
            }
        }

        return false;
    }
}
