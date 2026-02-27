package io.github.suel_ki.tarotcards.common.item.tarot;

import io.github.suel_ki.tarotcards.TarotCards;
import io.github.suel_ki.tarotcards.common.item.TarotItem;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Supplier;

public class TheEmperorTarot extends TarotItem {

    private static final Supplier<MobEffectInstance> effect = () -> new MobEffectInstance(MobEffects.HERO_OF_THE_VILLAGE, TarotCards.CONFIG.tick_rate + 20, TarotCards.CONFIG.cards.the_emperpor_heroofvillagebonus, true, false);

    protected void handleExtraLogic(Player player, boolean hasCard) {
        if (hasCard) {
            player.addEffect(effect.get());
        }
    }

	@Override
	public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag flag) {
		tooltip.add(Component.translatable(this.getDescriptionId() + ".desc", TarotCards.CONFIG.cards.the_emperpor_heroofvillagebonus + 1).withStyle(ChatFormatting.BLUE));
	}
}

