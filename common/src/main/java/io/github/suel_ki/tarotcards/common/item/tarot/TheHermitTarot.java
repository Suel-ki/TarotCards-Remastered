package io.github.suel_ki.tarotcards.common.item.tarot;

import io.github.suel_ki.tarotcards.TarotCards;
import io.github.suel_ki.tarotcards.common.item.TarotItem;
import io.github.suel_ki.tarotcards.core.compat.FTBTeamCompat;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.Nullable;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;

public class TheHermitTarot extends TarotItem {

    private static final UUID HERMIT_UUID = UUID.nameUUIDFromBytes("TarotHermit".getBytes(StandardCharsets.UTF_8));

    private static final Supplier<AttributeModifier> attribute = () -> new AttributeModifier(HERMIT_UUID, "Tarot Card", TarotCards.CONFIG.cards.the_hermit_armorbonus, AttributeModifier.Operation.ADDITION);

    @Override
    public boolean checkExtraConditions(LivingEntity entity) {
        return !hasNearbyAllies(entity) && super.checkExtraConditions(entity);
    }

    @Override
    protected Attribute getTargetAttribute() {
        return Attributes.ARMOR;
    }

    @Override
    protected AttributeModifier getModifier() {
        return attribute.get();
    }

    private boolean hasNearbyAllies(LivingEntity entity) {
        double range = TarotCards.CONFIG.cards.the_hermit_allyrange;
        AABB area = entity.getBoundingBox().inflate(range);

        TargetingConditions targeting = TargetingConditions.forNonCombat().ignoreLineOfSight();

        List<LivingEntity> entities = entity.level().getNearbyEntities(LivingEntity.class, targeting, entity, area);

        for (LivingEntity e : entities) {
            if (e.isAlliedTo(entity) || entity.isAlliedTo(e)) {
                return true;
            }
            if (e instanceof Player ePlayer && entity instanceof Player player) {
                if (FTBTeamCompat.isSameTeamSafe(player, ePlayer)) {
                    return true;
                }
            }
        }
        return false;
    }

	@Override
	public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag flag) {
		tooltip.add(Component.translatable(this.getDescriptionId() + ".desc", TarotCards.CONFIG.cards.the_hermit_armorbonus).withStyle(ChatFormatting.BLUE));
	}

}
