package io.github.suel_ki.tarotcards.client.forge;

import io.github.suel_ki.tarotcards.TarotCards;
import io.github.suel_ki.tarotcards.client.screen.TarotDeckScreen;
import io.github.suel_ki.tarotcards.common.item.TarotItem;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import java.awt.*;

@Mod.EventBusSubscriber(modid = TarotCards.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientEventHandler {
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> MenuScreens.register(TarotCards.TAROT_DECK_MENU.get(), TarotDeckScreen::new));
    }

    @SubscribeEvent
    public static void onItemColorHandler(RegisterColorHandlersEvent.Item event) {
        event.register((stack, tintIndex) ->
                        ((TarotItem) stack.getItem()).getColor(stack, tintIndex),
                TarotItem.ITEMS_CARDS.toArray(ItemLike[]::new));
    }
}
