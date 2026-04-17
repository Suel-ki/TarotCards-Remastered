package io.github.suel_ki.tarotcards.common.item.deck;

import io.github.suel_ki.tarotcards.common.item.TarotItem;
import io.github.suel_ki.tarotcards.common.item.tooltip.TarotDeckTooltip;
import io.github.suel_ki.tarotcards.core.platform.TarotUtilPlatform;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.component.DataComponents;
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
import net.minecraft.world.item.component.ItemContainerContents;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TarotDeckItem extends Item {

    public TarotDeckItem() {
        super(new Properties().stacksTo(1).rarity(Rarity.UNCOMMON).fireResistant().component(DataComponents.CONTAINER, ItemContainerContents.EMPTY));
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
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

        ItemContainerContents contents = stack.get(DataComponents.CONTAINER);

        if (contents == null || contents.equals(ItemContainerContents.EMPTY)) {
            return super.getTooltipImage(stack);
        }

        List<ItemStack> cards = new ArrayList<>();
        for (ItemStack s : contents.nonEmptyItems()) {
            if (s.getItem() instanceof TarotItem) {
                cards.add(s);
            }
        }

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
