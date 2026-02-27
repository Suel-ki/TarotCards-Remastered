package io.github.suel_ki.tarotcards.client.fabric;

import io.github.suel_ki.tarotcards.TarotCards;
import io.github.suel_ki.tarotcards.client.screen.TarotDeckScreen;
import io.github.suel_ki.tarotcards.common.item.TarotItem;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.world.level.ItemLike;

public class ClientTarotCards implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        MenuScreens.register(TarotCards.TAROT_DECK_MENU.get(), TarotDeckScreen::new);
        ColorProviderRegistry.ITEM.register((stack, tintIndex) ->
                ((TarotItem) stack.getItem()).getColor(stack, tintIndex),
                TarotItem.ITEMS_CARDS.toArray(ItemLike[]::new));
    }
}
