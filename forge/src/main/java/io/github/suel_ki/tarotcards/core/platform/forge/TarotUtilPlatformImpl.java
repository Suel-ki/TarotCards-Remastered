package io.github.suel_ki.tarotcards.core.platform.forge;

import io.github.suel_ki.tarotcards.common.menu.TarotDeckMenu;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.network.NetworkHooks;

import java.util.function.Supplier;

public class TarotUtilPlatformImpl {

    public static boolean isLoaded(String id) {
        return ModList.get().isLoaded(id);
    }

    public static void openTarotMenu(ServerPlayer player, ItemStack stack) {
        NetworkHooks.openScreen(player, new MenuProvider() {
            @Override
            public Component getDisplayName() {
                return stack.getHoverName();
            }

            @Override
            public AbstractContainerMenu createMenu(int syncId, Inventory inv, Player player) {
                return new TarotDeckMenu(syncId, inv, stack);
            }
        }, buf -> {
            buf.writeItem(stack);
        });
    }

    public static Supplier<Attribute> BLOCK_REACH() {
        return ForgeMod.BLOCK_REACH;
    }
}
