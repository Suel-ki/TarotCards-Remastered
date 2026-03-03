package io.github.suel_ki.tarotcards.common.item.tarot;

import io.github.suel_ki.tarotcards.TarotCards;
import io.github.suel_ki.tarotcards.common.item.TarotItem;
import io.github.suel_ki.tarotcards.core.init.ItemInit;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class TheHangedManTarot extends TarotItem {

    public float onHurt(@Nullable LivingEntity attacker, Player player, DamageSource source, float amount) {
        if (!player.level().isClientSide()) {
            int xpamount = (int) Math.min(amount * TarotCards.CONFIG.cards.the_hanged_man_xpratio, TarotCards.CONFIG.cards.the_hanged_man_xpcap);

            TarotCards.LOGGER.debug("{} - Damage to xp orb", ItemInit.the_hanged_man.get());
            TarotCards.LOGGER.debug("Amount: {}, For: {}", xpamount, player);

            ExperienceOrb orb = new ExperienceOrb(player.level(), player.getX(), player.getY(), player.getZ(), xpamount);
            player.level().addFreshEntity(orb);
        }

        return amount;
    }


    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.translatable(this.getDescriptionId() + ".desc", String.valueOf(TarotCards.CONFIG.cards.the_hanged_man_xpratio * 100)).withStyle(ChatFormatting.BLUE));
    }

}
