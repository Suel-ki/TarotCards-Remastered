package io.github.suel_ki.tarotcards.common.item.deck;

import io.github.suel_ki.tarotcards.common.item.TarotItem;
import io.github.suel_ki.tarotcards.common.item.tooltip.TarotDeckTooltip;
import io.github.suel_ki.tarotcards.core.platform.TarotUtilPlatform;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class TarotDeckItem extends Item {

    public TarotDeckItem() {
        super(new Properties().stacksTo(1).rarity(Rarity.UNCOMMON).fireResistant());
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag flag) {
        if (!Screen.hasShiftDown()) {
            tooltip.add(Component.translatable(this.getDescriptionId() + ".desc").withStyle(ChatFormatting.GRAY));
            tooltip.add(Component.translatable(this.getDescriptionId() + ".shift.desc"));
        }
    }

    @Override
    public Optional<TooltipComponent> getTooltipImage(ItemStack stack) {
        if (!Screen.hasShiftDown()) {
            return super.getTooltipImage(stack);
        }

        CompoundTag nbt = stack.getTag();
        if (nbt == null || !nbt.contains("TarotDeckInventory", Tag.TAG_COMPOUND)) {
            return super.getTooltipImage(stack);
        }

        ListTag listTag = nbt.getCompound("TarotDeckInventory").getList("Items", Tag.TAG_COMPOUND);
        if (listTag.isEmpty()) {
            return super.getTooltipImage(stack);
        }

        List<ItemStack> cards = listTag.stream()
                .map(tag -> (CompoundTag) tag)
                .filter(itemTag -> itemTag.getString("id").contains("tarot"))
                .map(ItemStack::of)
                .filter(innerStack -> !innerStack.isEmpty() && innerStack.getItem() instanceof TarotItem)
                .toList();

        return cards.isEmpty()
                ? super.getTooltipImage(stack)
                : Optional.of(new TarotDeckTooltip(cards));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (!world.isClientSide && player instanceof ServerPlayer serverPlayer) {
            TarotUtilPlatform.openTarotMenu(serverPlayer, stack);
        }
        return InteractionResultHolder.sidedSuccess(stack, world.isClientSide());
    }
}
