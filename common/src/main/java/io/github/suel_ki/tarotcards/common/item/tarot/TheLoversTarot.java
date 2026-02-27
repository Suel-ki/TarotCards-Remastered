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
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Supplier;

public class TheLoversTarot extends TarotItem {

    private static final TargetingConditions targeting = TargetingConditions.forNonCombat().ignoreLineOfSight().ignoreInvisibilityTesting();
    private static final Supplier<MobEffectInstance> effect = () -> new MobEffectInstance(MobEffects.REGENERATION, TarotCards.CONFIG.tick_rate + 20, TarotCards.CONFIG.cards.the_lovers_regenamplifier, true, false, false);

    @Override
    protected void handleExtraLogic(Player player, boolean hasCard) {
        if (hasCard) {
            double range = TarotCards.CONFIG.cards.the_lovers_range;
            AABB area = player.getBoundingBox().inflate(range);
            List<LivingEntity> entities = player.level().getNearbyEntities(LivingEntity.class, targeting, player, area);

            for (LivingEntity e : entities) {
                if (shouldAffect(player, e)) {
                    TarotCards.LOGGER.debug("{} - Add regen", ItemInit.the_lovers.get());
                    TarotCards.LOGGER.debug("Ally: {}", e);

                    e.addEffect(effect.get());
                }
            }
        }
    }

    private boolean shouldAffect(Player player, LivingEntity e) {
        if (e.isAlliedTo(player)) return true;
        return e instanceof Player eplayer && FTBTeamCompat.isSameTeamSafe(player, eplayer);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.translatable(this.getDescriptionId() + ".desc", TarotCards.CONFIG.cards.the_lovers_regenamplifier + 1).withStyle(ChatFormatting.BLUE));
    }

}
