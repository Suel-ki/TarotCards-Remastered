package io.github.suel_ki.tarotcards.common.item.tarot;

import io.github.suel_ki.tarotcards.TarotCards;
import io.github.suel_ki.tarotcards.common.item.TarotItem;
import io.github.suel_ki.tarotcards.core.compat.FTBTeamCompat;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.phys.AABB;

import java.util.List;
import java.util.function.Supplier;

public class TheHermitTarot extends TarotItem {

    private static final ResourceLocation HERMIT = TarotCards.id("the_hermit");

    private static final Supplier<AttributeModifier> attribute = () -> new AttributeModifier(HERMIT, TarotCards.CONFIG.cards.the_hermit_armorbonus, AttributeModifier.Operation.ADD_VALUE);

    public boolean checkExtraConditions(Player player) {
        return !hasNearbyAllies(player) && super.checkExtraConditions(player);
    }

    @Override
    protected Holder<Attribute> getTargetAttribute() {
        return Attributes.ARMOR;
    }

    @Override
    protected AttributeModifier getModifier() {
        return attribute.get();
    }

    private boolean hasNearbyAllies(Player player) {
        double range = TarotCards.CONFIG.cards.the_hermit_allyrange;
        AABB area = player.getBoundingBox().inflate(range);

        TargetingConditions targeting = TargetingConditions.forNonCombat().ignoreLineOfSight();

        List<LivingEntity> entities = player.level().getNearbyEntities(LivingEntity.class, targeting, player, area);

        for (LivingEntity e : entities) {
            if (e.isAlliedTo(player)) {
                return true;
            }
            if (e instanceof Player ePlayer) {
                if (FTBTeamCompat.isSameTeamSafe(player, ePlayer)) {
                    return true;
                }
            }
        }
        return false;
    }

	@Override
	public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
		tooltip.add(Component.translatable(this.getDescriptionId() + ".desc", TarotCards.CONFIG.cards.the_hermit_armorbonus).withStyle(ChatFormatting.BLUE));
	}

}
