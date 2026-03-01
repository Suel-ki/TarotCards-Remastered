package io.github.suel_ki.tarotcards.common.loot.fabric;

import io.github.suel_ki.tarotcards.TarotCards;
import io.github.suel_ki.tarotcards.common.item.TarotItem;
import io.github.suel_ki.tarotcards.core.resource.fabric.TarotChestReloader;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;

import static io.github.suel_ki.tarotcards.TarotCards.CONFIG;
import static io.github.suel_ki.tarotcards.core.resource.fabric.TarotChestReloader.TARGET_CHESTS;

public class TarotLootAdditionsImpl {

    public static void init() {
        TarotLootFunction.TYPE = Registry.register(
                BuiltInRegistries.LOOT_FUNCTION_TYPE,
                TarotCards.id("tarot_loot"),
                new LootItemFunctionType(new TarotLootFunction.Serializer())
        );
        LootTableEvents.MODIFY.register((resourceManager, lootManager, id, tableBuilder, source) -> {
            // Let the data load in advance
            if (TARGET_CHESTS.isEmpty()) {
                TarotChestReloader.forceReload(resourceManager);
            }

            if (!CONFIG.loot.do_loot_generation || !TARGET_CHESTS.contains(id)) {
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
