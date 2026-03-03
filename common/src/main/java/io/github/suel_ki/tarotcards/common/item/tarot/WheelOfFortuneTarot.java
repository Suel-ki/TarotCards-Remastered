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

public class WheelOfFortuneTarot extends TarotItem {

    private static final ResourceLocation WHEEL_OF_FORTUNE = TarotCards.id("the_wheel_of_fortune");

    private static final Supplier<AttributeModifier> attribute = () -> new AttributeModifier(WHEEL_OF_FORTUNE, TarotCards.CONFIG.cards.wheel_of_fortune_luckbonus, AttributeModifier.Operation.ADD_VALUE);

    @Override
    protected Holder<Attribute> getTargetAttribute() {
        return Attributes.LUCK;
    }

    @Override
    protected AttributeModifier getModifier() {
        return attribute.get();
    }

	@Override
	public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
		tooltip.add(Component.translatable(this.getDescriptionId() + ".desc", TarotCards.CONFIG.cards.wheel_of_fortune_luckbonus).withStyle(ChatFormatting.BLUE));
	}
}
