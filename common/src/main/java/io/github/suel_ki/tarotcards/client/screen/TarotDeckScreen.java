package io.github.suel_ki.tarotcards.client.screen;

import io.github.suel_ki.tarotcards.TarotCards;
import io.github.suel_ki.tarotcards.common.access.TarotPlayerAccess;
import io.github.suel_ki.tarotcards.common.menu.TarotDeckMenu;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;
import net.minecraft.world.entity.player.Inventory;

public class TarotDeckScreen extends AbstractContainerScreen<TarotDeckMenu> {

	private final ResourceLocation GUI = TarotCards.id("textures/gui/tarot_deck.png");

    public TarotDeckScreen(TarotDeckMenu container, Inventory inv, Component name) {
        super(container, inv, name);
    }

	@Override
	public void render(GuiGraphics matrixStack, int mouseX, int mouseY, float partialTicks) {
		this.renderBackground(matrixStack);
		super.render(matrixStack, mouseX, mouseY, partialTicks);

        int deckSize = ((TarotPlayerAccess) Minecraft.getInstance().player).tarotcards$getDeckSize();

        // Custom slot highlight
        matrixStack.pose().pushPose();
        matrixStack.pose().translate((int) ((this.width - this.imageWidth) * 0.5), (int) ((this.height - this.imageHeight) * 0.5), 0);
        this.menu.slots.stream().filter(s -> s instanceof TarotDeckMenu.DeckSlot).forEach(slot -> {
            if (slot.index < deckSize) {
               if (this.isHovering(slot.x + 3, slot.y, 10, 16, mouseX, mouseY)) {
                   matrixStack.fillGradient(RenderType.guiOverlay(), slot.x + 3, slot.y, slot.x + 13, slot.y + 16, FastColor.ARGB32.color(180, 253, 255, 221), FastColor.ARGB32.color(100, 230, 164, 42), 0);
               }
            } else {
                matrixStack.fill(slot.x + 3, slot.y, slot.x + 13, slot.y + 16, 0x99000000);
            }
        });
        matrixStack.pose().popPose();

		this.renderTooltip(matrixStack, mouseX, mouseY);
	}

    @Override
    protected void renderLabels(GuiGraphics matrixStack, int mouseX, int mouseY) {
        Component text = Component.translatable("item.tarotcards.tarot_deck");
        matrixStack.drawString(Minecraft.getInstance().font, text, this.imageWidth / 2 - Minecraft.getInstance().font.width(text) / 2, 8, 0xfff699);
    }

    @Override
    protected void renderBg(GuiGraphics matrixStack, float partialTicks, int mouseX, int mouseY) {
        matrixStack.blit(GUI, (int) ((this.width - this.imageWidth) * 0.5), (int) ((this.height - this.imageHeight) * 0.5), 0, 0, this.imageWidth, this.imageHeight);
    }

}

