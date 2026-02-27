package io.github.suel_ki.tarotcards.core.platform.fabric;

import com.jamieswhiteshirt.reachentityattributes.ReachEntityAttributes;
import io.github.suel_ki.tarotcards.common.menu.TarotDeckMenu;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;

import java.util.function.Supplier;

public class TarotUtilPlatformImpl {

    public static boolean isLoaded(String id) {
        return FabricLoader.getInstance().isModLoaded(id);
    }

    public static void openTarotMenu(ServerPlayer player, ItemStack stack) {
        player.openMenu(new ExtendedScreenHandlerFactory() {
            @Override
            public void writeScreenOpeningData(ServerPlayer serverPlayer, FriendlyByteBuf buf) {
                buf.writeItem(stack);
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

    public static Supplier<Attribute> BLOCK_REACH() {
        return () -> ReachEntityAttributes.REACH;
    }
}
