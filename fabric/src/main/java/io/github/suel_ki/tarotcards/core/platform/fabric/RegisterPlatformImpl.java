package io.github.suel_ki.tarotcards.core.platform.fabric;

import io.github.suel_ki.tarotcards.TarotCards;
import io.github.suel_ki.tarotcards.common.menu.MenuFactory;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class RegisterPlatformImpl {
    public static final List<Item> ITEMS = new ArrayList<>();

    public static <T extends Item> Supplier<T> registerItem(String name, Supplier<T> item) {
        T registry = Registry.register(BuiltInRegistries.ITEM, TarotCards.id(name), item.get());
        ITEMS.add(registry);
        return () -> registry;
    }

    public static Supplier<CreativeModeTab> registerItemGroup(String name, Supplier<ItemStack> icon) {
        CreativeModeTab tab = FabricItemGroup.builder()
                .title(Component.translatable("itemGroup." + TarotCards.MOD_ID))
                .icon(icon)
                .displayItems((enabledFeatures, entries) -> ITEMS.stream().map(ItemStack::new).forEach(entries::accept))
                .build();
        var registry = Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, TarotCards.id(name), tab);
        return () -> registry;
    }

    public static <T extends AbstractContainerMenu> Supplier<MenuType<T>> registerMenuType(String name, MenuFactory<T> factory) {
        MenuType<T> registry = Registry.register(BuiltInRegistries.MENU, TarotCards.id(name), new ExtendedScreenHandlerType<>(factory::create));
        return () -> registry;
    }

    public static <T extends SimpleCriterionTrigger<?>> Supplier<T> registerTrigger(Supplier<T> trigger) {
        T registry = CriteriaTriggers.register(trigger.get());
        return () -> registry;
    }

}
