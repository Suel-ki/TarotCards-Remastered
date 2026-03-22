package io.github.suel_ki.tarotcards.fabric;

import io.github.suel_ki.tarotcards.TarotCards;
import io.github.suel_ki.tarotcards.common.command.TarotCommand;
import io.github.suel_ki.tarotcards.core.accessories.AccessoriesHandler;
import io.github.suel_ki.tarotcards.core.config.fabric.LootConfig;
import io.github.suel_ki.tarotcards.core.network.OpenDeckPayload;
import io.github.suel_ki.tarotcards.core.network.fabric.TarotDeckPayload;
import io.github.suel_ki.tarotcards.core.platform.TarotUtilPlatform;
import me.fzzyhmstrs.fzzy_config.api.ConfigApiJava;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;

public class TarotCardsFabric implements ModInitializer {

    public static LootConfig LOOT = ConfigApiJava.registerAndLoadConfig(LootConfig::new);

    @Override
    public void onInitialize() {
        TarotCards.init();
        CommandRegistrationCallback.EVENT.register(
                (dispatcher, registryAccess, environment)
                        -> TarotCommand.register(dispatcher));
        PayloadTypeRegistry.playS2C().register(TarotDeckPayload.TYPE, TarotDeckPayload.CODEC);
        PayloadTypeRegistry.playC2S().register(OpenDeckPayload.TYPE, OpenDeckPayload.CODEC);

        ServerPlayNetworking.registerGlobalReceiver(OpenDeckPayload.TYPE, (payload, context) -> {
            context.server().execute(() -> {
                var deck = AccessoriesHandler.getDeck(context.player());
                if (!deck.isEmpty()) {
                    TarotUtilPlatform.openTarotMenu(context.player(), deck);
                }
            });
        });
    }
}
