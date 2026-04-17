package io.github.suel_ki.tarotcards.common.item.tarot;

import io.github.suel_ki.tarotcards.TarotCards;
import io.github.suel_ki.tarotcards.common.item.TarotItem;
import io.github.suel_ki.tarotcards.core.init.ItemInit;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class TheHangedManTarot extends TarotItem {

    @Override
    public float onHurt(@Nullable LivingEntity attacker, LivingEntity victim, DamageSource source, float amount) {
        if (!victim.level().isClientSide()) {
            int xpamount = (int) Math.min(amount * TarotCards.CONFIG.cards.the_hanged_man_xpratio, TarotCards.CONFIG.cards.the_hanged_man_xpcap);

            TarotCards.LOGGER.debug("{} - Damage to xp orb", ItemInit.the_hanged_man.get());
            TarotCards.LOGGER.debug("Amount: {}, For: {}", xpamount, victim);

            ExperienceOrb orb = new ExperienceOrb(victim.level(), victim.getX(), victim.getY(), victim.getZ(), xpamount);
            victim.level().addFreshEntity(orb);
        }

        return amount;
    }


    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.translatable(this.getDescriptionId() + ".desc", String.valueOf(TarotCards.CONFIG.cards.the_hanged_man_xpratio * 100)).withStyle(ChatFormatting.BLUE));
    }

}
