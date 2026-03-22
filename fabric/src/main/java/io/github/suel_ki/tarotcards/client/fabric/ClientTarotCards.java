package io.github.suel_ki.tarotcards.client.fabric;

import io.github.suel_ki.tarotcards.TarotCards;
import io.github.suel_ki.tarotcards.client.key.KeyHandler;
import io.github.suel_ki.tarotcards.client.screen.TarotDeckScreen;
import io.github.suel_ki.tarotcards.common.item.TarotItem;
import io.github.suel_ki.tarotcards.core.network.OpenDeckPacket;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.world.level.ItemLike;

import static io.github.suel_ki.tarotcards.core.network.OpenDeckPacket.OPEN_DECK_ID;

public class ClientTarotCards implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        MenuScreens.register(TarotCards.TAROT_DECK_MENU.get(), TarotDeckScreen::new);
        ColorProviderRegistry.ITEM.register((stack, tintIndex) ->
                ((TarotItem) stack.getItem()).getColor(stack, tintIndex),
                TarotItem.ITEMS_CARDS.toArray(ItemLike[]::new));
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.screen == null && client.player != null) {
                while (KeyHandler.OPEN_DECK_KEY.consumeClick()) {
                    ClientPlayNetworking.send(OPEN_DECK_ID, PacketByteBufs.empty());
                }
            }
        });
    }
}
