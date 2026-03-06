package io.github.suel_ki.tarotcards.client.fabric;

import io.github.suel_ki.tarotcards.TarotCards;
import io.github.suel_ki.tarotcards.client.screen.TarotDeckScreen;
import io.github.suel_ki.tarotcards.client.tint.DeactivatedColor;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.color.item.ItemTintSources;
import net.minecraft.client.gui.screens.MenuScreens;

public class ClientTarotCards implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        MenuScreens.register(TarotCards.TAROT_DECK_MENU.get(), TarotDeckScreen::new);
        ItemTintSources.ID_MAPPER.put(TarotCards.id("deactivated"), DeactivatedColor.MAP_CODEC);
    }
}
