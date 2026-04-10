package io.github.suel_ki.tarotcards.forge;

import io.github.suel_ki.tarotcards.TarotCards;
import io.github.suel_ki.tarotcards.common.loot.forge.TarotLootAdditionsImpl;
import io.github.suel_ki.tarotcards.core.compat.forge.MaidCompat;
import io.github.suel_ki.tarotcards.core.network.forge.ForgeNetworkInit;
import io.github.suel_ki.tarotcards.core.platform.forge.RegisterPlatformImpl;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(TarotCards.MOD_ID)
public class TarotCardsForge {
    public TarotCardsForge() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        IEventBus eventBus = MinecraftForge.EVENT_BUS;
        RegisterPlatformImpl.ITEMS.register(bus);
        RegisterPlatformImpl.TABS.register(bus);
        RegisterPlatformImpl.MENU_TYPES.register(bus);
        TarotLootAdditionsImpl.LOOT_MODIFIERS.register(bus);
        bus.addListener(this::commonInit);
        registerCompatEvent(eventBus);
        TarotCards.init();
    }

    private void commonInit(FMLCommonSetupEvent event) {
        ForgeNetworkInit.register();
    }

    private void registerCompatEvent(IEventBus bus) {
        if (ModList.get().isLoaded("touhou_little_maid")) {
            bus.register(MaidCompat.class);
        }
    }
}
