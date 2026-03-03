package io.github.suel_ki.tarotcards.core.network.fabric;

import io.github.suel_ki.tarotcards.TarotCards;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.item.ItemStack;

public record TarotDeckPayload(ItemStack stack) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<TarotDeckPayload> ID = new CustomPacketPayload.Type<>(TarotCards.id("open_deck"));
    public static final StreamCodec<RegistryFriendlyByteBuf, TarotDeckPayload> CODEC = StreamCodec.composite(
            ItemStack.OPTIONAL_STREAM_CODEC, TarotDeckPayload::stack,
            TarotDeckPayload::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return ID;
    }
}