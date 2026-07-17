package io.github.suel_ki.tarotcards.common.item.tarot;

import io.github.suel_ki.tarotcards.TarotCards;
import io.github.suel_ki.tarotcards.common.item.TarotItem;
import io.github.suel_ki.tarotcards.core.helper.TargetingHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.phys.AABB;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class TheHermitTarot extends TarotItem {

    private static final Identifier HERMIT = TarotCards.id("the_hermit");

    private static final Supplier<AttributeModifier> attribute = () -> new AttributeModifier(HERMIT, TarotCards.CONFIG.cards.the_hermit_armorbonus, AttributeModifier.Operation.ADD_VALUE);

    public TheHermitTarot(Properties properties) {
        super(properties);
    }

    @Override
    public boolean checkExtraConditions(Player player) {
        return !hasNearbyAllies(player) && super.checkExtraConditions(player);
    }

    @Override
    protected Holder<Attribute> getTargetAttribute() {
        return Attributes.ARMOR;
    }

    @Override
    protected AttributeModifier getModifier() {
        return attribute.get();
    }

    private boolean hasNearbyAllies(Player player) {
        if (player.level() instanceof ServerLevel level) {
            double range = TarotCards.CONFIG.cards.the_hermit_allyrange;
            AABB area = player.getBoundingBox().inflate(range);

            TargetingConditions targeting = TargetingConditions.forNonCombat()
                    .ignoreLineOfSight()
                    .selector((e, l) -> TargetingHelper.isAlly(player, e));

            List<LivingEntity> allies = level.getNearbyEntities(LivingEntity.class, targeting, player, area);

            return !allies.isEmpty();
        }
        return false;
    }

	@Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay tooltipDisplay, Consumer<Component> tooltip, TooltipFlag flag) {
        tooltip.accept(Component.translatable(this.getDescriptionId() + ".desc", TarotCards.CONFIG.cards.the_hermit_armorbonus).withStyle(ChatFormatting.BLUE));
	}

}
