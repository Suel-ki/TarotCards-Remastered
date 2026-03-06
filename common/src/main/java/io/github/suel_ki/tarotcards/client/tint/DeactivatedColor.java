package io.github.suel_ki.tarotcards.client.tint;

import com.mojang.serialization.MapCodec;
import io.github.suel_ki.tarotcards.common.item.TarotItem;
import net.minecraft.client.color.item.ItemTintSource;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.util.ARGB;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public record DeactivatedColor(int color) implements ItemTintSource {

    public static final MapCodec<DeactivatedColor> MAP_CODEC = ExtraCodecs.RGB_COLOR_CODEC.fieldOf("color")
            .xmap(DeactivatedColor::new, DeactivatedColor::color);

    public DeactivatedColor(int color) {
        this.color = ARGB.opaque(color);
    }

    @Override
    public int calculate(ItemStack stack, @Nullable ClientLevel level, @Nullable LivingEntity entity) {
        return !TarotItem.isActivated(stack) ? color : -1;
    }

    @Override
    public MapCodec<DeactivatedColor> type() {
        return MAP_CODEC;
    }
}
