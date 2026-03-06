package io.github.suel_ki.tarotcards.common.item.tarot;

import io.github.suel_ki.tarotcards.TarotCards;
import io.github.suel_ki.tarotcards.common.item.TarotItem;
import io.github.suel_ki.tarotcards.core.init.ItemInit;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class TheTowerTarot extends TarotItem {

    public TheTowerTarot(Properties properties) {
        super(properties);
    }

    public float onHurt(@Nullable LivingEntity attacker, Player player, DamageSource source, float amount) {
        if (source.is(DamageTypes.FALL)) {
            float negation = (float) (amount * (1 - TarotCards.CONFIG.cards.the_tower_damagenegation));

            TarotCards.LOGGER.debug("{} - Fall negation", ItemInit.the_tower.get());
            TarotCards.LOGGER.debug("Negation: {}, For: {}", negation, player);

            return negation;
        }
        return amount;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay tooltipDisplay, Consumer<Component> tooltip, TooltipFlag flag) {
        tooltip.accept(Component.translatable(this.getDescriptionId() + ".desc", String.valueOf(TarotCards.CONFIG.cards.the_tower_damagenegation * 100)).withStyle(ChatFormatting.BLUE));
    }
}
