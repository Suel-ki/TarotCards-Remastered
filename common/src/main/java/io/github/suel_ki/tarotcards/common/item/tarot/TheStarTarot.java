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

public class TheStarTarot extends TarotItem {

    private static final ResourceLocation STAR = TarotCards.id("the_star");

    @Override
    protected Holder<Attribute> getTargetAttribute() {
        return Attributes.BLOCK_INTERACTION_RANGE;
    }

    @Override
    protected AttributeModifier getModifier() {
        return new AttributeModifier(STAR, TarotCards.CONFIG.cards.the_star_reachboost, AttributeModifier.Operation.ADD_MULTIPLIED_BASE);
    }

    @Override
	public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
		tooltip.add(Component.translatable(this.getDescriptionId() + ".desc", TarotCards.CONFIG.cards.the_star_reachboost * 100).withStyle(ChatFormatting.BLUE));
	}

}
