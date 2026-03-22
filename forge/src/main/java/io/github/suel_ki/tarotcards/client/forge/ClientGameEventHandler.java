package io.github.suel_ki.tarotcards.client.forge;

import io.github.suel_ki.tarotcards.TarotCards;
import io.github.suel_ki.tarotcards.client.key.KeyHandler;
import io.github.suel_ki.tarotcards.core.network.OpenDeckPacket;
import io.github.suel_ki.tarotcards.core.network.forge.ForgeNetworkInit;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = TarotCards.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ClientGameEventHandler {
    @SubscribeEvent
    public static void keyInput(InputEvent.Key event) {
        if(Minecraft.getInstance().screen == null) {
            if (KeyHandler.OPEN_DECK_KEY.consumeClick()) {
                ForgeNetworkInit.CHANNEL.sendToServer(new OpenDeckPacket());
            }
        }
    }
}
