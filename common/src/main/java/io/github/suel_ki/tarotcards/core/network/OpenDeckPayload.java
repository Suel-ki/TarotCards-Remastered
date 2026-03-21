package io.github.suel_ki.tarotcards.core.network;

import io.github.suel_ki.tarotcards.TarotCards;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

public record OpenDeckPayload() implements CustomPacketPayload {
    public static final Type<OpenDeckPayload> TYPE = new Type<>(TarotCards.id("open_deck"));
    public static final StreamCodec<ByteBuf, OpenDeckPayload> CODEC = StreamCodec.unit(new OpenDeckPayload());

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
