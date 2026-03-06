package io.github.suel_ki.tarotcards.core.platform.fabric;

import com.mojang.serialization.Codec;
import io.github.suel_ki.tarotcards.TarotCards;
import io.github.suel_ki.tarotcards.common.menu.MenuFactory;
import io.github.suel_ki.tarotcards.core.network.fabric.TarotDeckPayload;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.advancements.criterion.SimpleCriterionTrigger;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

public class RegisterPlatformImpl {
    public static final List<Item> ITEMS = new ArrayList<>();

    public static <T extends Item> Supplier<T> registerItem(String name, Function<Item.Properties, T> factory) {
        Identifier id = TarotCards.id(name);
        T registry = Registry.register(BuiltInRegistries.ITEM, id, factory.apply(new Item.Properties().setId(ResourceKey.create(Registries.ITEM, id))));
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
        ExtendedScreenHandlerType<T, TarotDeckPayload> type = new ExtendedScreenHandlerType<>(
                (syncId, inv, payload) -> factory.create(syncId, inv, payload.stack()),
                TarotDeckPayload.CODEC
        );
        var registry = Registry.register(BuiltInRegistries.MENU, TarotCards.id(name), type);
        return () -> registry;
    }

    public static <T extends SimpleCriterionTrigger<?>> Supplier<T> registerTrigger(String name, Supplier<T> trigger) {
        T registry = Registry.register(BuiltInRegistries.TRIGGER_TYPES, TarotCards.id(name), trigger.get());
        return () -> registry;
    }

    public static Supplier<DataComponentType<Boolean>> registerDataComponent(String name) {
        var registry = Registry.register(
                BuiltInRegistries.DATA_COMPONENT_TYPE,
                TarotCards.id("activated"),
                DataComponentType.<Boolean>builder()
                        .persistent(Codec.BOOL)
                        .networkSynchronized(ByteBufCodecs.BOOL)
                        .build()
        );
        return () -> registry;
    }

}
