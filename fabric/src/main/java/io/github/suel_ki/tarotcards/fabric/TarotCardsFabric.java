package io.github.suel_ki.tarotcards.fabric;

import io.github.suel_ki.tarotcards.TarotCards;
import io.github.suel_ki.tarotcards.common.command.TarotCommand;
import io.github.suel_ki.tarotcards.core.accessories.AccessoriesHandler;
import io.github.suel_ki.tarotcards.core.platform.TarotUtilPlatform;
import io.github.suel_ki.tarotcards.core.resource.fabric.TarotChestReloader;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.server.packs.PackType;

import static io.github.suel_ki.tarotcards.core.network.OpenDeckPacket.OPEN_DECK_ID;

public class TarotCardsFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        ResourceManagerHelper.get(PackType.SERVER_DATA).registerReloadListener(new TarotChestReloader());
        TarotCards.init();
        CommandRegistrationCallback.EVENT.register(
                (dispatcher, registryAccess, environment)
                        -> TarotCommand.register(dispatcher));
        ServerPlayNetworking.registerGlobalReceiver(OPEN_DECK_ID, (server, player, handler, buf, responseSender) -> {
            server.execute(() -> {
                if (player != null) {
                    var deck = AccessoriesHandler.getDeck(player);
                    if (!deck.isEmpty()) {
                        TarotUtilPlatform.openTarotMenu(player, deck);
                    }
                }
            });
        });
    }
}
