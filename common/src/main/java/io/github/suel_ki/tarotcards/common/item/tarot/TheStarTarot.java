package io.github.suel_ki.tarotcards.common.item.tarot;

import io.github.suel_ki.tarotcards.TarotCards;
import io.github.suel_ki.tarotcards.common.item.TarotItem;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;

import java.util.function.Consumer;

public class TheStarTarot extends TarotItem {

    private static final Identifier STAR = TarotCards.id("the_star");

    public TheStarTarot(Properties properties) {
        super(properties);
    }

    @Override
    protected Holder<Attribute> getTargetAttribute() {
        return Attributes.BLOCK_INTERACTION_RANGE;
    }

    @Override
    protected AttributeModifier getModifier() {
        return new AttributeModifier(STAR, TarotCards.CONFIG.cards.the_star_reachboost, AttributeModifier.Operation.ADD_MULTIPLIED_BASE);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay tooltipDisplay, Consumer<Component> tooltip, TooltipFlag flag) {
        tooltip.accept(Component.translatable(this.getDescriptionId() + ".desc", TarotCards.CONFIG.cards.the_star_reachboost * 100).withStyle(ChatFormatting.BLUE));
	}

}
