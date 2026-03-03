package io.github.suel_ki.tarotcards.common.item.tarot;

import io.github.suel_ki.tarotcards.TarotCards;
import io.github.suel_ki.tarotcards.common.item.TarotItem;
import io.github.suel_ki.tarotcards.core.init.ItemInit;
import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class JusticeTarot extends TarotItem {

    private static final ResourceKey<DamageType> JUSTICE = ResourceKey.create(Registries.DAMAGE_TYPE, TarotCards.id("justice"));

    private static DamageSource justice(Entity entity) {
        return new DamageSource(entity.level().registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(JUSTICE));
    }

    //Make sure it is a living entity hurting a player
	public float onHurt(@Nullable LivingEntity attacker, Player player, DamageSource source, float amount) {
        //Damage taken from justice shouldnt be returned
        if (source.is(JUSTICE) || attacker == null) {
            return amount;
        }

        amount = (float) (amount * TarotCards.CONFIG.cards.justice_damagemultiplier);

        attacker.hurt(justice(player), amount);

        TarotCards.LOGGER.debug("{} - Returning damage", ItemInit.justice.get());
        TarotCards.LOGGER.debug("From: {}, To: {} [{}]", player, attacker, attacker.getHealth());
        TarotCards.LOGGER.debug("Amount: {}", amount);

        return amount;
    }

	@Override
	public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
		tooltip.add(Component.translatable(this.getDescriptionId() + ".desc", String.valueOf(TarotCards.CONFIG.cards.justice_damagemultiplier * 100)).withStyle(ChatFormatting.BLUE));
	}
}
