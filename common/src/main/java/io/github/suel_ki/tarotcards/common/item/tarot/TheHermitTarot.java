package io.github.suel_ki.tarotcards.common.item.tarot;

import io.github.suel_ki.tarotcards.TarotCards;
import io.github.suel_ki.tarotcards.common.item.TarotItem;
import io.github.suel_ki.tarotcards.core.helper.TargetingHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.phys.AABB;

import java.util.List;
import java.util.function.Supplier;

public class TheHermitTarot extends TarotItem {

    private static final ResourceLocation HERMIT = TarotCards.id("the_hermit");

    private static final Supplier<AttributeModifier> attribute = () -> new AttributeModifier(HERMIT, TarotCards.CONFIG.cards.the_hermit_armorbonus, AttributeModifier.Operation.ADD_VALUE);

    @Override
    public boolean checkExtraConditions(LivingEntity entity) {
        return !hasNearbyAllies(entity) && super.checkExtraConditions(entity);
    }

    @Override
    protected Holder<Attribute> getTargetAttribute() {
        return Attributes.ARMOR;
    }

    @Override
    protected AttributeModifier getModifier() {
        return attribute.get();
    }

    private boolean hasNearbyAllies(LivingEntity entity) {
        double range = TarotCards.CONFIG.cards.the_hermit_allyrange;
        AABB area = entity.getBoundingBox().inflate(range);

        TargetingConditions targeting = TargetingConditions.forNonCombat()
                .ignoreLineOfSight()
                .selector(e -> TargetingHelper.isAlly(entity, e));

        List<LivingEntity> allies = entity.level().getNearbyEntities(LivingEntity.class, targeting, entity, area);

        return !allies.isEmpty();
    }

	@Override
	public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
		tooltip.add(Component.translatable(this.getDescriptionId() + ".desc", TarotCards.CONFIG.cards.the_hermit_armorbonus).withStyle(ChatFormatting.BLUE));
	}

}
