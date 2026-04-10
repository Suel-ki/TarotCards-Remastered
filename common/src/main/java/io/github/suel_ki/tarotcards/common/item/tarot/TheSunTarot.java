package io.github.suel_ki.tarotcards.common.item.tarot;

import io.github.suel_ki.tarotcards.TarotCards;
import io.github.suel_ki.tarotcards.common.item.TarotItem;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;

public class TheSunTarot extends TarotItem {

    private static final UUID SUN_UUID = UUID.nameUUIDFromBytes("TarotSun".getBytes(StandardCharsets.UTF_8));

	private static final Supplier<AttributeModifier> healthBoost = ()-> new AttributeModifier(SUN_UUID, "Tarot Card", TarotCards.CONFIG.cards.the_sun_healthboost, AttributeModifier.Operation.MULTIPLY_BASE);

    @Override
    protected Attribute getTargetAttribute() {
        return Attributes.MAX_HEALTH;
    }

    @Override
    protected AttributeModifier getModifier() {
        return healthBoost.get();
    }

    @Override
    protected void onModifierRemoved(LivingEntity entity, Attribute attr) {
        if (attr == Attributes.MAX_HEALTH) {
            if (entity.getHealth() > entity.getMaxHealth()) {
                entity.setHealth(entity.getMaxHealth());
            }
        }
    }

	@Override
	public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag flag) {
		tooltip.add(Component.translatable(this.getDescriptionId() + ".desc", TarotCards.CONFIG.cards.the_sun_healthboost * 100).withStyle(ChatFormatting.BLUE));
	}
}
