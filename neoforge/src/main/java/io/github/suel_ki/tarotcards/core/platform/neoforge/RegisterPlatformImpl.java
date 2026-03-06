package io.github.suel_ki.tarotcards.core.platform.neoforge;

import com.mojang.serialization.Codec;
import io.github.suel_ki.tarotcards.TarotCards;
import io.github.suel_ki.tarotcards.common.menu.MenuFactory;
import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.advancements.criterion.SimpleCriterionTrigger;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Function;
import java.util.function.Supplier;

public class RegisterPlatformImpl {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(TarotCards.MOD_ID);
    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, TarotCards.MOD_ID);
    public static final DeferredRegister<MenuType<?>> MENU_TYPES = DeferredRegister.create(Registries.MENU, TarotCards.MOD_ID);
    public static final DeferredRegister<CriterionTrigger<?>> TRIGGER_TYPE = DeferredRegister.create(Registries.TRIGGER_TYPE, TarotCards.MOD_ID);
    public static final DeferredRegister.DataComponents COMPONENTS = DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, TarotCards.MOD_ID);

    public static <T extends Item> Supplier<T> registerItem(String name, Function<Item.Properties, T> factory) {
        return ITEMS.registerItem(name, factory);
    }

    public static Supplier<CreativeModeTab> registerItemGroup(String name, Supplier<ItemStack> icon) {
        return TABS.register(name, () -> CreativeModeTab.builder()
                .title(Component.translatable("itemGroup." + TarotCards.MOD_ID))
                .icon(icon)
                .displayItems(
                        (enabledFeatures, entries) ->
                                ITEMS.getEntries().stream().map(DeferredHolder::get).forEach(entries::accept)
                )
                .build());
    }

    public static <T extends AbstractContainerMenu> Supplier<MenuType<T>> registerMenuType(String name, MenuFactory<T> factory) {
        return MENU_TYPES.register(name, () -> IMenuTypeExtension.create((windowId, inv, data) -> {
            ItemStack stack = ItemStack.OPTIONAL_STREAM_CODEC.decode(data);
            return factory.create(windowId, inv, stack);
        }));
    }

    public static <T extends SimpleCriterionTrigger<?>> Supplier<T> registerTrigger(String name, Supplier<T> trigger) {
        return TRIGGER_TYPE.register(name, trigger);
    }

    public static Supplier<DataComponentType<Boolean>> registerDataComponent(String name) {
        return COMPONENTS.registerComponentType(name,
                (builder) -> builder
                        .persistent(Codec.BOOL)
                        .networkSynchronized(ByteBufCodecs.BOOL).cacheEncoding());
    }

}

