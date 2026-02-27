package io.github.suel_ki.tarotcards.fabric;

import io.github.suel_ki.tarotcards.TarotCards;
import io.github.suel_ki.tarotcards.common.command.TarotCommand;
import io.github.suel_ki.tarotcards.core.resource.fabric.TarotChestReloader;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.server.packs.PackType;

public class TarotCardsFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        ResourceManagerHelper.get(PackType.SERVER_DATA).registerReloadListener(new TarotChestReloader());
        TarotCards.init();
        CommandRegistrationCallback.EVENT.register(
                (dispatcher, registryAccess, environment)
                        -> TarotCommand.register(dispatcher));
    }
}
