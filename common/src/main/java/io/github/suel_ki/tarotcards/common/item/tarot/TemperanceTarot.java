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

public class TemperanceTarot extends TarotItem {

	public TemperanceTarot(Properties properties) {
		super(properties);
	}

	public static float handleExhaustionAmount(float amount, Player player) {
		if (hasTarot(player, ItemInit.temperance.get()) && amount != 0) {
            TarotCards.LOGGER.debug("{} - Reducing hunger", ItemInit.temperance.get());
            TarotCards.LOGGER.debug("From: {}, To: {}, For: {}", amount, (float) (amount * (1f - TarotCards.CONFIG.cards.temperance_reduction)), player);
            return (float) (amount * (1f - TarotCards.CONFIG.cards.temperance_reduction));
        }
		return amount;
	}

	@Override
	public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay tooltipDisplay, Consumer<Component> tooltip, TooltipFlag flag) {
		tooltip.accept(Component.translatable(this.getDescriptionId() + ".desc", String.valueOf(TarotCards.CONFIG.cards.temperance_reduction * 100)).withStyle(ChatFormatting.BLUE));
	}

}
