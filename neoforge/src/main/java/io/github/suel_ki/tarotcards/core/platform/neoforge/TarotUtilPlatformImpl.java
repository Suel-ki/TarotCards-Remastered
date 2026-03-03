package io.github.suel_ki.tarotcards.core.platform.neoforge;

import io.github.suel_ki.tarotcards.common.menu.TarotDeckMenu;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.item.ItemStack;
import net.neoforged.fml.ModList;

public class TarotUtilPlatformImpl {

    public static boolean isLoaded(String id) {
        return ModList.get().isLoaded(id);
    }

    public static void openTarotMenu(ServerPlayer player, ItemStack stack) {
        player.openMenu(new SimpleMenuProvider(
                (id, inv, p) -> new TarotDeckMenu(id, inv, stack),
                stack.getHoverName()
        ), buf -> {
            ItemStack.OPTIONAL_STREAM_CODEC.encode(buf, stack);
        });
    }
}
