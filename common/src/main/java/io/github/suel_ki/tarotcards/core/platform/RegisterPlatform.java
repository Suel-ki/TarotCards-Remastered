package io.github.suel_ki.tarotcards.core.platform;

import dev.architectury.injectables.annotations.ExpectPlatform;
import io.github.suel_ki.tarotcards.common.menu.MenuFactory;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.function.Supplier;

public class RegisterPlatform {
    @ExpectPlatform
    public static <T extends Item> Supplier<T> registerItem(String name, Supplier<T> item) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static Supplier<CreativeModeTab> registerItemGroup(String name, Supplier<ItemStack> icon) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static <T extends AbstractContainerMenu> Supplier<MenuType<T>> registerMenuType(String name, MenuFactory<T> factory) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static <T extends SimpleCriterionTrigger<?>> Supplier<T> registerTrigger(String name, Supplier<T> trigger) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static Supplier<DataComponentType<Boolean>> registerDataComponent(String name) {
        throw new AssertionError();
    }
}
