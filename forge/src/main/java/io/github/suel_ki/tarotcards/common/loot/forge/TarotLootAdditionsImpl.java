package io.github.suel_ki.tarotcards.common.loot.forge;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.suel_ki.tarotcards.TarotCards;
import io.github.suel_ki.tarotcards.core.config.TarotConfig;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.common.loot.LootModifier;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;

/**
 * Custom loot modifier that has a chance to choose 1 item from a list.
 * Chance is set by {@link TarotConfig.Loot#default_loot_chance}
 * Is completely disabled when {@link  TarotConfig.Loot#do_loot_generation} is false
 */
public class TarotLootAdditionsImpl extends LootModifier {

    public List<Item> items;

    public static final DeferredRegister<Codec<? extends IGlobalLootModifier>> LOOT_MODIFIERS = DeferredRegister.create(ForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, TarotCards.MOD_ID);

    public static final Supplier<Codec<TarotLootAdditionsImpl>> CODEC = Suppliers.memoize(() -> RecordCodecBuilder.create(inst -> codecStart(inst)
            .and(ForgeRegistries.ITEMS.getCodec()
                    .listOf()
                    .fieldOf("items")
                    .forGetter(v -> v.items))
            .apply(inst, TarotLootAdditionsImpl::new)));

    public TarotLootAdditionsImpl(LootItemCondition[] conditionsIn, List<Item> items) {
        super(conditionsIn);
        this.items = items;
    }

    public static void init() {
        LOOT_MODIFIERS.register("loot_additions", CODEC);
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
    public Codec<? extends LootModifier> codec() {
        return CODEC.get();
    }
}
