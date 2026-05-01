package io.github.suel_ki.tarotcards.common.item.deck;

import io.github.suel_ki.tarotcards.common.item.TarotItem;
import io.github.suel_ki.tarotcards.common.item.tooltip.TarotDeckTooltip;
import io.github.suel_ki.tarotcards.core.platform.TarotUtilPlatform;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.ItemContainerContents;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public class TarotDeckItem extends Item {

    public TarotDeckItem(Properties properties) {
        super(properties.stacksTo(1).rarity(Rarity.UNCOMMON).fireResistant().component(DataComponents.CONTAINER, ItemContainerContents.EMPTY));
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay tooltipDisplay, Consumer<Component> consumer, TooltipFlag flag) {
        if (!Minecraft.getInstance().hasShiftDown()) {
            consumer.accept(Component.translatable(this.getDescriptionId() + ".desc").withStyle(ChatFormatting.GRAY));
            consumer.accept(Component.translatable(this.getDescriptionId() + ".shift.desc"));
        }
    }

    @Override
    public Optional<TooltipComponent> getTooltipImage(ItemStack stack) {
        if (!Minecraft.getInstance().hasShiftDown()) {
            return super.getTooltipImage(stack);
        }

        ItemContainerContents contents = stack.get(DataComponents.CONTAINER);

        if (contents == null || contents.equals(ItemContainerContents.EMPTY)) {
            return super.getTooltipImage(stack);
        }

        List<ItemStack> cards = contents.stream().toList();

        boolean hasAnyTarot = cards.stream().anyMatch(s -> !s.isEmpty() && s.getItem() instanceof TarotItem);

        return !hasAnyTarot
                ? super.getTooltipImage(stack)
                : Optional.of(new TarotDeckTooltip(cards));
    }

    @Override
    public InteractionResult use(Level world, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (!world.isClientSide() && player instanceof ServerPlayer serverPlayer) {
            TarotUtilPlatform.openTarotMenu(serverPlayer, stack);
            return InteractionResult.SUCCESS_SERVER;
        }
        return InteractionResult.CONSUME;
    }
}
