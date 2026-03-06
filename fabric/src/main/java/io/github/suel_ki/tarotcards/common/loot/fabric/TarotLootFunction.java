package io.github.suel_ki.tarotcards.common.loot.fabric;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.suel_ki.tarotcards.TarotCards;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class TarotLootFunction  extends LootItemConditionalFunction {
    public static final MapCodec<TarotLootFunction> CODEC = RecordCodecBuilder.mapCodec(instance ->
            commonFields(instance).apply(instance, TarotLootFunction::new)
    );

    public static LootItemFunctionType<TarotLootFunction> TYPE;

    protected TarotLootFunction(List<LootItemCondition> conditions) {
        super(conditions);
    }

    @Override
    public @NotNull LootItemFunctionType getType() {
        return TYPE;
    }

    @Override
    protected ItemStack run(ItemStack stack, LootContext context) {
        if (!TarotCards.CONFIG.loot.do_loot_generation) {
            return ItemStack.EMPTY;
        }

        float chance = TarotCards.CONFIG.loot.default_loot_chance;

        if (context.getRandom().nextFloat() < chance) {
            return stack;
        }

        return ItemStack.EMPTY;
    }

    public static class Builder extends LootItemConditionalFunction.Builder<Builder> {
        @Override
        protected @NotNull Builder getThis() {
            return this;
        }

        @Override
        public LootItemConditionalFunction build() {
            return new TarotLootFunction(this.getConditions());
        }

    }
}
