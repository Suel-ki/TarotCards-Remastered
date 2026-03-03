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
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;
import java.util.function.Supplier;

public class TheSunTarot extends TarotItem {

    private static final ResourceLocation SUN = TarotCards.id("the_sun");

	private static final Supplier<AttributeModifier> healthBoost = ()-> new AttributeModifier(SUN, TarotCards.CONFIG.cards.the_sun_healthboost, AttributeModifier.Operation.ADD_MULTIPLIED_BASE);

    @Override
    protected Holder<Attribute> getTargetAttribute() {
        return Attributes.MAX_HEALTH;
    }

    @Override
    protected AttributeModifier getModifier() {
        return healthBoost.get();
    }

    @Override
    protected void onModifierRemoved(Player player, Holder<Attribute> attr) {
        if (attr == Attributes.MAX_HEALTH) {
            if (player.getHealth() > player.getMaxHealth()) {
                player.setHealth(player.getMaxHealth());
            }
        }
    }

	@Override
	public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
		tooltip.add(Component.translatable(this.getDescriptionId() + ".desc", TarotCards.CONFIG.cards.the_sun_healthboost * 100).withStyle(ChatFormatting.BLUE));
	}
}
