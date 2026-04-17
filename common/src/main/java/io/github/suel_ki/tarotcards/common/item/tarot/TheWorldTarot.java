package io.github.suel_ki.tarotcards.common.item.tarot;

import io.github.suel_ki.tarotcards.TarotCards;
import io.github.suel_ki.tarotcards.common.item.TarotItem;
import io.github.suel_ki.tarotcards.core.compat.FTBTeamCompat;
import io.github.suel_ki.tarotcards.core.init.ItemInit;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.phys.AABB;

import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;

public class TheWorldTarot extends TarotItem {

    public static final Supplier<MobEffectInstance> effect = () -> new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 60 + TarotCards.CONFIG.tick_rate, TarotCards.CONFIG.cards.the_world_slownessamplifier, true, false, false);

    @Override
    protected void handleExtraLogic(LivingEntity entity, boolean hasCard) {
        if (hasCard) {
            double range = TarotCards.CONFIG.cards.the_world_range;
            AABB area = entity.getBoundingBox().inflate(range);
            List<LivingEntity> entities = entity.level().getNearbyEntities(LivingEntity.class, TargetingConditions.DEFAULT, entity, area);

            for (LivingEntity e : entities) {
                if (shouldAffect(entity, e)) {
                    TarotCards.LOGGER.debug("{} - Slow nearby", ItemInit.the_world.get());
                    TarotCards.LOGGER.debug("Entity: {}", e);

                    e.addEffect(effect.get());
                }
            }
        }
    }

    private boolean shouldAffect(LivingEntity entity, LivingEntity e) {
        if (e.isAlliedTo(entity) || entity.isAlliedTo(e)) return false;

        if (e instanceof OwnableEntity ownable) {
            UUID ownerUUID = ownable.getOwnerUUID();
            if (ownerUUID != null) {
                if (ownerUUID.equals(entity.getUUID())) return false;

                LivingEntity owner = ownable.getOwner();
                if (owner != null && entity.isAlliedTo(owner)) return false;

                if (entity instanceof Player player && FTBTeamCompat.isSameTeamByUUID(player.getUUID(), ownerUUID)) return false;
            }
        }

        if (e instanceof Player ePlayer && entity instanceof Player player) {
            if (!player.canHarmPlayer(ePlayer)) return false;
            return !FTBTeamCompat.isSameTeamSafe(player, ePlayer);
        }

        return true;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.translatable(this.getDescriptionId() + ".desc", TarotCards.CONFIG.cards.the_world_slownessamplifier + 1).withStyle(ChatFormatting.BLUE));
    }

}
