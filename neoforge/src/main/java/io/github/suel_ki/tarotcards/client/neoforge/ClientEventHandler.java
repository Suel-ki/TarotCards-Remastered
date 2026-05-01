package io.github.suel_ki.tarotcards.client.neoforge;

import io.github.suel_ki.tarotcards.TarotCards;
import io.github.suel_ki.tarotcards.client.key.KeyHandler;
import io.github.suel_ki.tarotcards.client.screen.TarotDeckScreen;
import io.github.suel_ki.tarotcards.client.tint.DeactivatedColor;
import io.github.suel_ki.tarotcards.client.tooltip.ClientTarotDeckTooltip;
import io.github.suel_ki.tarotcards.common.item.tooltip.TarotDeckTooltip;
import io.github.suel_ki.tarotcards.core.network.OpenDeckPayload;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.*;
import net.neoforged.neoforge.client.network.ClientPacketDistributor;

@EventBusSubscriber(modid = TarotCards.MOD_ID, value = Dist.CLIENT)
public class ClientEventHandler {
    @SubscribeEvent
    public static void onClientSetup(RegisterMenuScreensEvent event) {
        event.register(TarotCards.TAROT_DECK_MENU.get(), TarotDeckScreen::new);
    }

    @SubscribeEvent
    public static void onItemColorHandler(RegisterColorHandlersEvent.ItemTintSources event) {
        event.register(TarotCards.id("deactivated"), DeactivatedColor.MAP_CODEC);
    }

    @SubscribeEvent
    public static void registerTooltipComponents(RegisterClientTooltipComponentFactoriesEvent event) {
        event.register(TarotDeckTooltip.class, ClientTarotDeckTooltip::new);
    }


    @SubscribeEvent
    public static void registerKeys(RegisterKeyMappingsEvent event) {
        event.register(KeyHandler.OPEN_DECK_KEY);
    }

    @SubscribeEvent
    public static void keyInput(InputEvent.Key event) {
        if(Minecraft.getInstance().screen == null) {
            if (KeyHandler.OPEN_DECK_KEY.consumeClick()) {
                ClientPacketDistributor.sendToServer(new OpenDeckPayload());
            }
        }
    }
}
