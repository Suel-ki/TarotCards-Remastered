package io.github.suel_ki.tarotcards;

import io.github.suel_ki.tarotcards.common.loot.TarotLootAdditions;
import io.github.suel_ki.tarotcards.core.config.TarotConfig;
import io.github.suel_ki.tarotcards.core.init.ItemInit;
import io.github.suel_ki.tarotcards.common.menu.TarotDeckMenu;
import io.github.suel_ki.tarotcards.core.init.TriggerInit;
import io.github.suel_ki.tarotcards.core.platform.RegisterPlatform;
import me.fzzyhmstrs.fzzy_config.api.ConfigApiJava;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.function.Supplier;

public class TarotCards {
    public static final String MOD_ID = "tarotcards";

    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

    public static final Supplier<CreativeModeTab> TAROT_ITEM_GROUP = RegisterPlatform.registerItemGroup("tarot",
            () -> new ItemStack(ItemInit.tarot_deck.get()));

    public static final Supplier<MenuType<TarotDeckMenu>> TAROT_DECK_MENU = RegisterPlatform.registerMenuType("tarot_deck", TarotDeckMenu::new);

    public static TarotConfig CONFIG = ConfigApiJava.registerAndLoadConfig(TarotConfig::new);

    public static void init() {
        ItemInit.init();
        TriggerInit.init();
        TarotLootAdditions.init();
    }

    public static ResourceLocation id(String name) {
        return new ResourceLocation(MOD_ID, name);
    }
}
