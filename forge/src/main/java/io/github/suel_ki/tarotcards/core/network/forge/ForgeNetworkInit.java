package io.github.suel_ki.tarotcards.core.network.forge;

import io.github.suel_ki.tarotcards.TarotCards;
import io.github.suel_ki.tarotcards.core.accessories.AccessoriesHandler;
import io.github.suel_ki.tarotcards.core.network.OpenDeckPacket;
import io.github.suel_ki.tarotcards.core.platform.TarotUtilPlatform;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.function.Supplier;

public class ForgeNetworkInit {
    private static final String PROTOCOL_VERSION = "1";

    public static int ID = 0;

    public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(
            TarotCards.id("main"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );

    public static void register() {
        CHANNEL.registerMessage(
                ID++,
                OpenDeckPacket.class,
                OpenDeckPacket::encode,
                OpenDeckPacket::new,
                ForgeNetworkInit::handleOpenDeck
        );
    }

    private static void handleOpenDeck(OpenDeckPacket packet, Supplier<NetworkEvent.Context> ctx) {
        NetworkEvent.Context context = ctx.get();
        if (context.getDirection() == NetworkDirection.PLAY_TO_SERVER) {
            context.enqueueWork(() -> {
                var player = context.getSender();
                if (player != null) {
                    var deck = AccessoriesHandler.getDeck(player);
                    if (!deck.isEmpty()) {
                        TarotUtilPlatform.openTarotMenu(player, deck);
                    }
                }
            });
        }
        context.setPacketHandled(true);
    }
}
