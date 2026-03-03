package io.github.suel_ki.tarotcards.common.item.tarot;

import io.github.suel_ki.tarotcards.TarotCards;
import io.github.suel_ki.tarotcards.common.item.TarotItem;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;
import java.util.function.Supplier;

public class TheChariotTarot extends TarotItem {
    private static final ResourceLocation CHARIOT = TarotCards.id("the_chariot");

    private static final Supplier<AttributeModifier> attribute = () -> new AttributeModifier(CHARIOT, TarotCards.CONFIG.cards.the_chariot_speedboost, AttributeModifier.Operation.ADD_MULTIPLIED_BASE);

    @Override
    protected Holder<Attribute> getTargetAttribute() {
        return Attributes.MOVEMENT_SPEED;
    }
    @Override
    protected AttributeModifier getModifier() {
        return attribute.get();
    }

	@Override
	public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
		tooltip.add(Component.translatable(this.getDescriptionId() + ".desc", TarotCards.CONFIG.cards.the_chariot_speedboost * 100).withStyle(ChatFormatting.BLUE));
	}
}
