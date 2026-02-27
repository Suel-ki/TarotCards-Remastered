package io.github.suel_ki.tarotcards.common.loot.fabric;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import io.github.suel_ki.tarotcards.TarotCards;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import org.jetbrains.annotations.NotNull;

public class TarotLootFunction  extends LootItemConditionalFunction {
    public static LootItemFunctionType TYPE;

    protected TarotLootFunction(LootItemCondition[] conditions) {
        super(conditions);
    }

    @Override
    public LootItemFunctionType getType() {
        return TYPE;
    }

    @Override
    protected ItemStack run(ItemStack stack, LootContext context) {
        if (!TarotCards.CONFIG.loot.do_loot_generation) {
            return ItemStack.EMPTY;
        }
        Item item = stack.getItem();

        float chance = TarotCards.CONFIG.loot.default_loot_chance;

        if (context.getRandom().nextFloat() < chance) {
            return stack;
        }

        return ItemStack.EMPTY;
    }

    public static class Serializer extends LootItemConditionalFunction.Serializer<TarotLootFunction> {
        @Override
        public TarotLootFunction deserialize(JsonObject json, JsonDeserializationContext context, LootItemCondition[] conditions) {
            return new TarotLootFunction(conditions);
        }
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
