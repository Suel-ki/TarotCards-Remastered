package io.github.suel_ki.tarotcards.common.menu;

import io.github.suel_ki.tarotcards.TarotCards;
import io.github.suel_ki.tarotcards.common.access.TarotPlayerAccess;
import io.github.suel_ki.tarotcards.common.item.TarotItem;
import io.github.suel_ki.tarotcards.core.accessories.AccessoriesHandler;
import io.github.suel_ki.tarotcards.core.init.TriggerInit;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemContainerContents;

import java.util.HashSet;
import java.util.Set;

public class TarotDeckMenu extends AbstractContainerMenu {

    private final ItemStack deck;

    private final SimpleContainer deckInventory;

    public TarotDeckMenu(int id, Inventory inv, ItemStack deck) {
        super(TarotCards.TAROT_DECK_MENU.get(), id);
        this.deck = deck;
        this.deckInventory = new SimpleContainer(22) {
            @Override
            public void setChanged() {
                super.setChanged();
                saveToItem();
                checkAndTriggerAdvancement(inv.player);
            }
        };

        loadFromItem();

        int deckSize = ((TarotPlayerAccess) inv.player).tarotcards$getDeckSize();

        if (!inv.player.level().isClientSide) {
            for (int i = deckSize; i < 22; i++) {
                ItemStack stackInSlot = deckInventory.getItem(i);
                if (!stackInSlot.isEmpty()) {
                    if (!inv.add(stackInSlot)) {
                        inv.player.drop(stackInSlot, false);
                    }
                    deckInventory.setItem(i, ItemStack.EMPTY);
                    deckInventory.setChanged();
                }
            }
        }

        if (!deck.isEmpty()) {
                int slotIndex = 0;
                int invX = 10;
                int invY = 25;
                for (int y = 0; y < 2; y++) {
                    for (int x = 0; x < 11; x++) {
                        addSlot(new DeckSlot(deckInventory, slotIndex, invX + 14 * x, invY + 18 * y, deckSize));
                        slotIndex++;
                    }
                }
        }

        //Add player slots
        int slotIndex = 0;
        int invX = 8;
        int invY = 142;
        for (int x = 0; x < 9; x++) {
            addSlot(new Slot(inv, slotIndex, invX + 18 * x, invY));
            slotIndex++;
        }
        invY -= 58;
        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 9; x++) {
                addSlot(new Slot(inv, slotIndex, invX + 18 * x, invY + 18 * y));
                slotIndex++;
            }

        }
    }

    private void loadFromItem() {
        ItemContainerContents contents = this.deck.getOrDefault(DataComponents.CONTAINER, ItemContainerContents.EMPTY);
        contents.copyInto(this.deckInventory.getItems());
    }

    private void saveToItem() {
        this.deck.set(DataComponents.CONTAINER, ItemContainerContents.fromItems(this.deckInventory.getItems()));
    }

    private void checkAndTriggerAdvancement(Player player) {
        if (player instanceof ServerPlayer serverPlayer) {
            for (int i = 0; i < 22; i++) {
                if (deckInventory.getItem(i).isEmpty()) return;
            }
            Set<Item> uniqueCards = new HashSet<>(22);
            for (int i = 0; i < 22; i++) {
                uniqueCards.add(deckInventory.getItem(i).getItem());
            }

            if (uniqueCards.size() == 22) {
                TriggerInit.DECK_COLLECTION_TRIGGER.get().trigger(serverPlayer, 22);
            }
        }
    }

    public static class DeckSlot extends Slot {
        private final int deckSize;
        public DeckSlot(Container container, int index, int xPosition, int yPosition, int deckSize) {
            super(container, index, xPosition, yPosition);
            this.deckSize = deckSize;
        }

        @Override
        public boolean mayPlace(ItemStack stack) {
            if (this.getContainerSlot() >= deckSize) {
                return false;
            }
            return canPut(stack);
        }

        @Override
        public boolean isActive() {
            return this.getContainerSlot() < deckSize;
        }

        private boolean canPut(ItemStack stack) {
            if (!stack.is(TarotItem.TAROT)) return false;
            for(int i = 0; i < container.getContainerSize(); i++) {
                if (container.getItem(i).is(stack.getItem())) {
                    return false;
                }
            }
            return true;
        }

        @Override
        public boolean isHighlightable() {
            return false;
        }
    }

    @Override
    public ItemStack quickMoveStack(Player playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot != null && slot.hasItem()) {
            ItemStack slotItem = slot.getItem();
            itemstack = slotItem.copy();
            if (index < 22) {
                if (!this.moveItemStackTo(slotItem, 22, this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else {
                if (!this.moveItemStackTo(slotItem, 0, 22, false)) {
                    return ItemStack.EMPTY;
                }
            }
            if (slotItem.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
        }
        return itemstack;
    }

    @Override
    public boolean stillValid(Player player) {
        return !player.isRemoved() && (player.getMainHandItem().is(deck.getItem()) || player.getOffhandItem().is(deck.getItem()) || !AccessoriesHandler.getDeck(player).isEmpty());
    }

}
