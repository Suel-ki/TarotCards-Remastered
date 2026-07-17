package io.github.suel_ki.tarotcards.common.item.tarot;

import io.github.suel_ki.tarotcards.TarotCards;
import io.github.suel_ki.tarotcards.common.item.TarotItem;
import io.github.suel_ki.tarotcards.core.helper.TargetingHelper;
import io.github.suel_ki.tarotcards.core.init.ItemInit;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.phys.AABB;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class TheLoversTarot extends TarotItem {

    private static final Supplier<MobEffectInstance> effect = () -> new MobEffectInstance(MobEffects.REGENERATION, TarotCards.CONFIG.tick_rate + 20, TarotCards.CONFIG.cards.the_lovers_regenamplifier, true, false, false);

    public TheLoversTarot(Properties properties) {
        super(properties);
    }

    @Override
    protected void handleExtraLogic(Player player, boolean hasCard) {
        if (hasCard) {
            if (player.level() instanceof ServerLevel level) {
                double range = TarotCards.CONFIG.cards.the_lovers_range;
                AABB area = player.getBoundingBox().inflate(range);
                TargetingConditions targeting = TargetingConditions.forNonCombat().ignoreLineOfSight().ignoreInvisibilityTesting()
                        .selector(TargetingHelper.getFilter(player, TargetingHelper.EffectType.POSITIVE));
                List<LivingEntity> entities = level.getNearbyEntities(LivingEntity.class, targeting, player, area);

                for (LivingEntity e : entities) {
                    TarotCards.LOGGER.debug("{} - Add regen", ItemInit.the_lovers.get());
                    TarotCards.LOGGER.debug("Ally: {}", e);

                    e.addEffect(effect.get());
                }
            }
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay tooltipDisplay, Consumer<Component> tooltip, TooltipFlag flag) {
        tooltip.accept(Component.translatable(this.getDescriptionId() + ".desc", TarotCards.CONFIG.cards.the_lovers_regenamplifier + 1).withStyle(ChatFormatting.BLUE));
    }

}
