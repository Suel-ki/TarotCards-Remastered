package io.github.suel_ki.tarotcards.client.tooltip;

import io.github.suel_ki.tarotcards.TarotCards;
import io.github.suel_ki.tarotcards.common.item.tooltip.TarotDeckTooltip;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.Identifier;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ClientTarotDeckTooltip implements ClientTooltipComponent {
    private static final Identifier GUI_TEXTURE = TarotCards.id("textures/gui/tarot_deck.png");

    private static final int SRC_U = 5;
    private static final int SRC_V = 22;

    private static final int WIDTH = 166;

    private static final int HEIGHT = 40;

    private final List<ItemStack> cards;

    public ClientTarotDeckTooltip(TarotDeckTooltip tooltip) {
        this.cards = tooltip.cards();
    }

    @Override
    public int getWidth(Font font) {
        return WIDTH;
    }

    @Override
    public int getHeight(Font font) {
        return HEIGHT + 3;
    }

    @Override
    public void renderImage(Font font, int x, int y, int width, int height, GuiGraphics graphics) {
        graphics.blit(RenderPipelines.GUI_TEXTURED, GUI_TEXTURE, x, y, SRC_U, SRC_V, WIDTH, HEIGHT, 256, 256);

        x = x + 4;
        y = y + 2;

        for (int i = 0; i < this.cards.size(); i++) {
            if (i >= 22) break;

            ItemStack stack = this.cards.get(i);

            if (stack.isEmpty()) {
                continue;
            }

            int col = i % 11;
            int row = i / 11;

            int itemX = x + (col * 14) + 1;
            int itemY = y + (row * 18) + 1;

            graphics.renderItem(stack, itemX, itemY);
            graphics.renderItemDecorations(font, stack, itemX, itemY);
        }
    }

    @Nullable
    public static ClientTooltipComponent get(TooltipComponent component) {
        if (component instanceof TarotDeckTooltip tooltip) {
            return new ClientTarotDeckTooltip(tooltip);
        }
        return null;
    }
}