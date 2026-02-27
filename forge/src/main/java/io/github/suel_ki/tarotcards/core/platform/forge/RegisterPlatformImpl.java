package io.github.suel_ki.tarotcards.core.platform.forge;

import io.github.suel_ki.tarotcards.TarotCards;
import io.github.suel_ki.tarotcards.common.menu.MenuFactory;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class RegisterPlatformImpl {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, TarotCards.MOD_ID);
    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, TarotCards.MOD_ID);
    public static final DeferredRegister<MenuType<?>> MENU_TYPES = DeferredRegister.create(ForgeRegistries.MENU_TYPES, TarotCards.MOD_ID);

    public static <T extends Item> Supplier<T> registerItem(String name, Supplier<T> item) {
        return ITEMS.register(name, item);
    }

    public static Supplier<CreativeModeTab> registerItemGroup(String name, Supplier<ItemStack> icon) {
        return TABS.register(name, () -> CreativeModeTab.builder()
                .title(Component.translatable("itemGroup." + TarotCards.MOD_ID))
                .icon(icon)
                .displayItems(
                        (enabledFeatures, entries) ->
                                ITEMS.getEntries().stream().map(RegistryObject::get).forEach(entries::accept)
                )
                .build());
    }

    public static <T extends AbstractContainerMenu> Supplier<MenuType<T>> registerMenuType(String name, MenuFactory<T> factory) {
        return MENU_TYPES.register(name, () -> IForgeMenuType.create(factory::create));
    }

    public static <T extends SimpleCriterionTrigger<?>> Supplier<T> registerTrigger(Supplier<T> trigger) {
        T registry = CriteriaTriggers.register(trigger.get());
        return () -> registry;
    }
}

