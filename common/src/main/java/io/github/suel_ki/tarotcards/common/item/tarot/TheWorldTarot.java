package io.github.suel_ki.tarotcards.common.item.tarot;

import io.github.suel_ki.tarotcards.TarotCards;
import io.github.suel_ki.tarotcards.common.item.TarotItem;
import io.github.suel_ki.tarotcards.core.helper.TargetingHelper;
import io.github.suel_ki.tarotcards.core.init.ItemInit;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.phys.AABB;

import java.util.List;
import java.util.function.Supplier;

public class TheWorldTarot extends TarotItem {

    public static final Supplier<MobEffectInstance> effect = () -> new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 60 + TarotCards.CONFIG.tick_rate, TarotCards.CONFIG.cards.the_world_slownessamplifier, true, false, false);

    @Override
    protected void handleExtraLogic(LivingEntity entity, boolean hasCard) {
        if (hasCard) {
            double range = TarotCards.CONFIG.cards.the_world_range;
            AABB area = entity.getBoundingBox().inflate(range);
            TargetingConditions targeting = TargetingConditions.DEFAULT
                    .selector(TargetingHelper.getFilter(entity, TargetingHelper.EffectType.NEGATIVE));
            List<LivingEntity> entities = entity.level().getNearbyEntities(LivingEntity.class, targeting, entity, area);

            for (LivingEntity e : entities) {
                TarotCards.LOGGER.debug("{} - Slow nearby", ItemInit.the_world.get());
                TarotCards.LOGGER.debug("Entity: {}", e);

                e.addEffect(effect.get());
            }
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.translatable(this.getDescriptionId() + ".desc", TarotCards.CONFIG.cards.the_world_slownessamplifier + 1).withStyle(ChatFormatting.BLUE));
    }

}
