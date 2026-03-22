package io.github.suel_ki.tarotcards.client.fabric;

import io.github.suel_ki.tarotcards.TarotCards;
import io.github.suel_ki.tarotcards.client.key.KeyHandler;
import io.github.suel_ki.tarotcards.client.screen.TarotDeckScreen;
import io.github.suel_ki.tarotcards.client.tint.DeactivatedColor;
import io.github.suel_ki.tarotcards.core.network.OpenDeckPayload;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.color.item.ItemTintSources;
import net.minecraft.client.gui.screens.MenuScreens;

public class ClientTarotCards implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        MenuScreens.register(TarotCards.TAROT_DECK_MENU.get(), TarotDeckScreen::new);
        ItemTintSources.ID_MAPPER.put(TarotCards.id("deactivated"), DeactivatedColor.MAP_CODEC);
        KeyBindingHelper.registerKeyBinding(KeyHandler.OPEN_DECK_KEY);
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.screen == null && client.player != null) {
                while (KeyHandler.OPEN_DECK_KEY.consumeClick()) {
                    ClientPlayNetworking.send(new OpenDeckPayload());
                }
            }
        });
    }
}
