package io.github.suel_ki.tarotcards.core.config.fabric;


import io.github.suel_ki.tarotcards.TarotCards;
import me.fzzyhmstrs.fzzy_config.annotations.Translation;
import me.fzzyhmstrs.fzzy_config.config.Config;
import me.fzzyhmstrs.fzzy_config.validation.collection.ValidatedList;
import me.fzzyhmstrs.fzzy_config.validation.minecraft.ValidatedIdentifier;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;

@Translation(prefix = "tarotcards.config.loot")
public class LootConfig extends Config {
    public LootConfig() {
        super(TarotCards.id("loot"));
    }

    public ValidatedList<Identifier> target_loots = new ValidatedIdentifier().toList(
            BuiltInLootTables.DESERT_PYRAMID.identifier(),
            BuiltInLootTables.SIMPLE_DUNGEON.identifier(),
            BuiltInLootTables.END_CITY_TREASURE.identifier(),
            BuiltInLootTables.NETHER_BRIDGE.identifier(),
            BuiltInLootTables.ABANDONED_MINESHAFT.identifier(),
            BuiltInLootTables.SHIPWRECK_TREASURE.identifier(),
            BuiltInLootTables.STRONGHOLD_LIBRARY.identifier(),
            BuiltInLootTables.VILLAGE_TEMPLE.identifier()
    );
}
