package io.github.suel_ki.tarotcards.client.neoforge;

import io.github.suel_ki.tarotcards.TarotCards;
import io.github.suel_ki.tarotcards.client.screen.TarotDeckScreen;
import io.github.suel_ki.tarotcards.common.item.TarotItem;
import net.minecraft.world.level.ItemLike;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;

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
}
