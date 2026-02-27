package io.github.suel_ki.tarotcards.common.item.tarot;

import io.github.suel_ki.tarotcards.TarotCards;
import io.github.suel_ki.tarotcards.common.item.TarotItem;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;

public class TheChariotTarot extends TarotItem {
    private static final UUID CHARIOT_UUID = UUID.nameUUIDFromBytes("TarotChariot".getBytes(StandardCharsets.UTF_8));

    private static final Supplier<AttributeModifier> attribute = () -> new AttributeModifier(CHARIOT_UUID, "Tarot Card", TarotCards.CONFIG.cards.the_chariot_speedboost, AttributeModifier.Operation.MULTIPLY_BASE);

    @Override
    protected Attribute getTargetAttribute() {
        return Attributes.MOVEMENT_SPEED;
    }
    @Override
    protected AttributeModifier getModifier() {
        return attribute.get();
    }

	@Override
	public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag flag) {
		tooltip.add(Component.translatable(this.getDescriptionId() + ".desc", TarotCards.CONFIG.cards.the_chariot_speedboost * 100).withStyle(ChatFormatting.BLUE));
	}
}
