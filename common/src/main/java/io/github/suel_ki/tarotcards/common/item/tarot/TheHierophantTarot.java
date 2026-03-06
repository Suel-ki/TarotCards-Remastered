package io.github.suel_ki.tarotcards.common.item.tarot;

import io.github.suel_ki.tarotcards.TarotCards;
import io.github.suel_ki.tarotcards.common.item.TarotItem;
import io.github.suel_ki.tarotcards.core.init.ItemInit;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;

import java.util.function.Consumer;

public class TheHierophantTarot extends TarotItem {

	public TheHierophantTarot(Properties properties) {
		super(properties);
	}

	public static int handleOnPlayerPickupXp(Player player, int value) {
		if (hasTarot(player, ItemInit.the_hierophant.get())) {
            int new_value = (int) Math.round(value * TarotCards.CONFIG.cards.the_hierophant_xpboost);

            TarotCards.LOGGER.debug("{} - XP Boost", ItemInit.the_hierophant.get());
            TarotCards.LOGGER.debug("From: {}, To: {}", value, new_value);

            value = new_value;
        }
        return value;
	}

	@Override
	public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay tooltipDisplay, Consumer<Component> tooltip, TooltipFlag flag) {
		tooltip.accept(Component.translatable(this.getDescriptionId() + ".desc", String.valueOf(TarotCards.CONFIG.cards.the_hierophant_xpboost * 100)).withStyle(ChatFormatting.BLUE));
	}
}
