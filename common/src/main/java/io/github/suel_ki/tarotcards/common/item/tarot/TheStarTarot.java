package io.github.suel_ki.tarotcards.common.item.tarot;

import io.github.suel_ki.tarotcards.TarotCards;
import io.github.suel_ki.tarotcards.common.item.TarotItem;
import io.github.suel_ki.tarotcards.core.platform.TarotUtilPlatform;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;

public class TheStarTarot extends TarotItem {

    private static final UUID STAR_UUID = UUID.nameUUIDFromBytes("TarotStar".getBytes(StandardCharsets.UTF_8));

    @Override
    protected Attribute getTargetAttribute() {
        return TarotUtilPlatform.BLOCK_REACH().get();
    }

    @Override
    protected AttributeModifier getModifier() {
        double percent = TarotCards.CONFIG.cards.the_star_reachboost;

        double absoluteBonus = 4.5 * percent;

        return new AttributeModifier(STAR_UUID, "Tarot Card", absoluteBonus, AttributeModifier.Operation.ADDITION);
    }

    @Override
	public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag flag) {
		tooltip.add(Component.translatable(this.getDescriptionId() + ".desc", TarotCards.CONFIG.cards.the_star_reachboost * 100).withStyle(ChatFormatting.BLUE));
	}

}
