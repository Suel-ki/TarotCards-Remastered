package io.github.suel_ki.tarotcards.core.config.fabric;


import io.github.suel_ki.tarotcards.TarotCards;
import me.fzzyhmstrs.fzzy_config.annotations.Translation;
import me.fzzyhmstrs.fzzy_config.config.Config;
import me.fzzyhmstrs.fzzy_config.validation.collection.ValidatedList;
import me.fzzyhmstrs.fzzy_config.validation.minecraft.ValidatedIdentifier;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;

@Translation(prefix = "tarotcards.config.loot")
public class LootConfig extends Config {
    public LootConfig() {
        super(TarotCards.id("loot"));
    }

    public ValidatedList<ResourceLocation> target_loots = new ValidatedIdentifier().toList(
            BuiltInLootTables.DESERT_PYRAMID.location(),
            BuiltInLootTables.SIMPLE_DUNGEON.location(),
            BuiltInLootTables.END_CITY_TREASURE.location(),
            BuiltInLootTables.NETHER_BRIDGE.location(),
            BuiltInLootTables.ABANDONED_MINESHAFT.location(),
            BuiltInLootTables.SHIPWRECK_TREASURE.location(),
            BuiltInLootTables.STRONGHOLD_LIBRARY.location(),
            BuiltInLootTables.VILLAGE_TEMPLE.location()
    );
}
