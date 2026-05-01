package io.github.suel_ki.tarotcards.common.item.tooltip;

import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public record TarotDeckTooltip(List<ItemStack> cards) implements TooltipComponent {
}