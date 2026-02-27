package io.github.suel_ki.tarotcards.common.item.tarot;

import io.github.suel_ki.tarotcards.TarotCards;
import io.github.suel_ki.tarotcards.common.item.TarotItem;
import io.github.suel_ki.tarotcards.core.init.ItemInit;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class TheHierophantTarot extends TarotItem {

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
	public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag flag) {
		tooltip.add(Component.translatable(this.getDescriptionId() + ".desc", String.valueOf(TarotCards.CONFIG.cards.the_hierophant_xpboost * 100)).withStyle(ChatFormatting.BLUE));
	}
}
