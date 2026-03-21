package io.github.suel_ki.tarotcards.client.neoforge;

import io.github.suel_ki.tarotcards.TarotCards;
import io.github.suel_ki.tarotcards.client.key.KeyHandler;
import io.github.suel_ki.tarotcards.client.screen.TarotDeckScreen;
import io.github.suel_ki.tarotcards.common.item.TarotItem;
import io.github.suel_ki.tarotcards.core.network.OpenDeckPayload;
import net.minecraft.client.Minecraft;
import net.minecraft.world.level.ItemLike;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.InputEvent;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.network.PacketDistributor;

@EventBusSubscriber(modid = TarotCards.MOD_ID, value = Dist.CLIENT)
public class ClientEventHandler {
    @SubscribeEvent
    public static void onClientSetup(RegisterMenuScreensEvent event) {
        event.register(TarotCards.TAROT_DECK_MENU.get(), TarotDeckScreen::new);
    }

    @SubscribeEvent
    public static void onItemColorHandler(RegisterColorHandlersEvent.Item event) {
        event.register((stack, tintIndex) ->
                        ((TarotItem) stack.getItem()).getColor(stack, tintIndex),
                TarotItem.ITEMS_CARDS.toArray(ItemLike[]::new));
    }

    @SubscribeEvent
    public static void registerKeys(RegisterKeyMappingsEvent event) {
        event.register(KeyHandler.OPEN_DECK_KEY);
    }

    @SubscribeEvent
    public static void keyInput(InputEvent.Key event) {
        if(Minecraft.getInstance().screen == null) {
            if (KeyHandler.OPEN_DECK_KEY.consumeClick()) {
                PacketDistributor.sendToServer(new OpenDeckPayload());
            }
        }
    }
}
