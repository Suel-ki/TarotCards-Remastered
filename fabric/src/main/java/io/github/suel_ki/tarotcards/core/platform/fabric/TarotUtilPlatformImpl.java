package io.github.suel_ki.tarotcards.core.platform.fabric;

import io.github.suel_ki.tarotcards.common.menu.TarotDeckMenu;
import io.github.suel_ki.tarotcards.core.network.fabric.TarotDeckPayload;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;

public class TarotUtilPlatformImpl {

    public static boolean isLoaded(String id) {
        return FabricLoader.getInstance().isModLoaded(id);
    }

    public static void openTarotMenu(ServerPlayer player, ItemStack stack) {
        player.openMenu(new ExtendedScreenHandlerFactory<TarotDeckPayload>() {
            @Override
            public TarotDeckPayload getScreenOpeningData(ServerPlayer player) {
                return new TarotDeckPayload(stack);
            }

            @Override
            public Component getDisplayName() {
                return stack.getHoverName();
            }

            @Override
            public AbstractContainerMenu createMenu(int id, Inventory inv, Player player) {
                return new TarotDeckMenu(id, inv, stack);
            }
        });
    }
}
