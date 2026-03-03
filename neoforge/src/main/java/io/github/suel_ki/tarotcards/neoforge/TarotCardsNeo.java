package io.github.suel_ki.tarotcards.neoforge;

import io.github.suel_ki.tarotcards.TarotCards;
import io.github.suel_ki.tarotcards.common.loot.neoforge.TarotLootAdditionsImpl;
import io.github.suel_ki.tarotcards.core.platform.neoforge.RegisterPlatformImpl;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

@Mod(TarotCards.MOD_ID)
public class TarotCardsNeo {
    public TarotCardsNeo(IEventBus bus) {
        RegisterPlatformImpl.ITEMS.register(bus);
        RegisterPlatformImpl.TABS.register(bus);
        RegisterPlatformImpl.MENU_TYPES.register(bus);
        RegisterPlatformImpl.TRIGGER_TYPE.register(bus);
        RegisterPlatformImpl.COMPONENTS.register(bus);
        TarotLootAdditionsImpl.LOOT_MODIFIERS.register(bus);
        TarotCards.init();
    }
}
