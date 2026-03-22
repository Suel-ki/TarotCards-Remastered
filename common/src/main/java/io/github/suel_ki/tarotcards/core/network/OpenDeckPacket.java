package io.github.suel_ki.tarotcards.core.network;

import io.github.suel_ki.tarotcards.TarotCards;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public class OpenDeckPacket {
    public static final ResourceLocation OPEN_DECK_ID = TarotCards.id("open_deck");

    public OpenDeckPacket() {
    }

    public OpenDeckPacket(FriendlyByteBuf buf) {
    }

    public void encode(FriendlyByteBuf buf) {
    }
}
