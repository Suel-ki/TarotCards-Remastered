package io.github.suel_ki.tarotcards.common.loot.neoforge;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.suel_ki.tarotcards.TarotCards;
import io.github.suel_ki.tarotcards.core.config.TarotConfig;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.common.loot.LootModifier;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.List;

/**
 * Custom loot modifier that has a chance to choose 1 item from a list.
 * Chance is set by {@link TarotConfig.Loot#default_loot_chance}
 * Is completely disabled when {@link  TarotConfig.Loot#do_loot_generation} is false
 */
public class TarotLootAdditionsImpl extends LootModifier {

    public List<Item> items;

    public static final DeferredRegister<MapCodec<? extends IGlobalLootModifier>> LOOT_MODIFIERS = DeferredRegister.create(NeoForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, TarotCards.MOD_ID);

    public static final MapCodec<TarotLootAdditionsImpl> CODEC = RecordCodecBuilder.mapCodec(instance ->
            codecStart(instance)
                    .and(BuiltInRegistries.ITEM.byNameCodec().listOf().fieldOf("items").forGetter(v -> v.items))
                    .apply(instance, TarotLootAdditionsImpl::new)
    );

    public TarotLootAdditionsImpl(LootItemCondition[] conditionsIn, List<Item> items) {
        super(conditionsIn);
        this.items = items;
    }

    public static void init() {
        LOOT_MODIFIERS.register("loot_additions", () -> CODEC);
    }

    @Override
    protected ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        if (!TarotCards.CONFIG.loot.do_loot_generation) {
            return generatedLoot;
        }

        if (items.isEmpty()) {
            return generatedLoot;
        }

        float chance = TarotCards.CONFIG.loot.default_loot_chance;

        if (context.getRandom().nextFloat() < chance) {
            generatedLoot.add(new ItemStack(items.get(context.getRandom().nextInt(items.size()))));
        }

        return generatedLoot;
    }

    @Override
    public MapCodec<? extends LootModifier> codec() {
        return CODEC;
    }
}
