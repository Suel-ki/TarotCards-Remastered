package io.github.suel_ki.tarotcards.common.loot.fabric;

import io.github.suel_ki.tarotcards.TarotCards;
import io.github.suel_ki.tarotcards.common.item.TarotItem;
import io.github.suel_ki.tarotcards.fabric.TarotCardsFabric;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;

import static io.github.suel_ki.tarotcards.TarotCards.CONFIG;

public class TarotLootAdditionsImpl {

    public static void init() {
        TarotLootFunction.TYPE = Registry.register(
                BuiltInRegistries.LOOT_FUNCTION_TYPE,
                TarotCards.id("tarot_loot"),
                new LootItemFunctionType<>(TarotLootFunction.CODEC)
        );
        LootTableEvents.MODIFY.register((key, tableBuilder, source, registries) -> {

            if (!CONFIG.loot.do_loot_generation || !TarotCardsFabric.LOOT.target_loots.contains(key.location())) {
                return;
            }

            LootPool.Builder poolBuilder = LootPool.lootPool()
                    .setRolls(ConstantValue.exactly(1.0F))
                    .apply(new TarotLootFunction.Builder());

            for (Item tarotCard : TarotItem.ITEMS_CARDS) {
                poolBuilder.add(LootItem.lootTableItem(tarotCard).setWeight(1));
            }

            tableBuilder.withPool(poolBuilder);
        });
    }
}
