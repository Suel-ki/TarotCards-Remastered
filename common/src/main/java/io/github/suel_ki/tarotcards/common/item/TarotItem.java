package io.github.suel_ki.tarotcards.common.item;

import io.github.suel_ki.tarotcards.TarotCards;
import io.github.suel_ki.tarotcards.core.accessories.AccessoriesHandler;
import io.github.suel_ki.tarotcards.core.init.ItemInit;
import net.minecraft.ChatFormatting;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.ItemContainerContents;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public abstract class TarotItem extends Item {

    public static final TagKey<Item> TAROT = TagKey.create(Registries.ITEM, TarotCards.id("tarot_cards"));
    public static final List<TarotItem> ITEMS_CARDS = new ArrayList<>();
    private Identifier cachedId;

    public TarotItem(Properties properties) {
        super(properties.rarity(Rarity.UNCOMMON).stacksTo(1).component(TarotCards.ACTIVATED.get(), true));
        ITEMS_CARDS.add(this);
    }

    public Identifier getCacheId() {
        if (this.cachedId == null) {
            this.cachedId = BuiltInRegistries.ITEM.getKey(this);
        }
        return this.cachedId;
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return isActivated(stack);
    }

    /**
     * If the Tarot Card is active (Using toggles Tarot Card)
     */
    public static boolean isActivated(ItemStack tarot) {
        return tarot.getOrDefault(TarotCards.ACTIVATED.get(), true);
    }

    public static void setActivated(ItemStack tarot, boolean activated) {
        tarot.set(TarotCards.ACTIVATED.get(), activated);
    }

    /**
     * Toggles Tarot Card on use.
     */
    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        ItemStack tarot = player.getItemInHand(hand);
        setActivated(tarot, !isActivated(tarot));
        return super.use(level, player, hand);
    }

    /**
     * If the specified player has the given tarot on them or in their Tarot Deck.
     */
    public static boolean hasTarot(Player player, Item tarot) {
        if (player == null) {
            return false;
        }

        if (AccessoriesHandler.hasAccessoryActivated(player, tarot)) return true;

        // If they have the deck in a curio slot, save it for checking later
        ItemStack deck = AccessoriesHandler.getDeck(player);

        // Only search the inventory if config allows it
        if (!TarotCards.CONFIG.require_card_in_curio) {
            Inventory inv = player.getInventory();
            // Check player for card and deck
            for (int i = 0; i < inv.getContainerSize(); i++) {
                ItemStack stack = inv.getItem(i);
                if (stack.isEmpty()) continue;
                // If we find the card, return it
                // if we find the deck, remember it
                if (stack.is(tarot)) {
                    if (isActivated(stack)) return true;
                } else if (stack.is(ItemInit.tarot_deck.get())) { // This will choose player inventory decks over curios
                    deck = stack;
                }
            }
        }

        // If we didnt find the deck either, return false
        if (deck == null || deck.isEmpty()) return false;

        return checkCardInDeck(deck, tarot);
    }

    // Check tarot deck for card if config allows it
    private static boolean checkCardInDeck(ItemStack deck, Item tarot) {
        if (!TarotCards.CONFIG.tarot_deck_applies_effects) return false;

        if (!deck.has(DataComponents.CONTAINER)) return false;

        ItemContainerContents contents = deck.getOrDefault(DataComponents.CONTAINER, ItemContainerContents.EMPTY);

        for (ItemStack stack : contents.nonEmptyItems()) {
            if (stack.is(tarot)) {
                return isActivated(stack);
            }
        }
        return false;
    }

    public void handleTick(Player player, boolean hasCard) {
        // COMBINE: Must be in inventory AND meet custom conditions
        boolean isTrulyActive = hasCard && checkExtraConditions(player);

        updateAttributes(player, isTrulyActive);
        handleExtraLogic(player, isTrulyActive);
    }

    /**
     * Subclasses override this to add unique requirements (like being alone).
     * @return true by default.
     */
    public boolean checkExtraConditions(Player player) {

        if (TarotCards.CONFIG.level_lock) {
            int requiredLevel = TarotCards.CONFIG.min_xp_level_required.getOrDefault(getCacheId(), 0);
            if (player.experienceLevel < requiredLevel) {
                return false;
            }
        }

        if (TarotCards.CONFIG.advancement_lock) {
            if (player instanceof ServerPlayer serverPlayer) {
                String advPath = TarotCards.CONFIG.required_advancements.get(getCacheId());

                if (advPath != null && !advPath.isEmpty()) {
                    Identifier advId = Identifier.parse(advPath);
                    AdvancementHolder advancement = serverPlayer.level().getServer().getAdvancements().get(advId);

                    if (advancement != null) {
                        if (!serverPlayer.getAdvancements().getOrStartProgress(advancement).isDone()) {
                            return false;
                        }
                    } else {
                        TarotCards.LOGGER.warn("Advancement not found: '{}' for tarot card: '{}'. Please check your config", advPath, getCacheId());
                    }
                }
            }
        }

        return true;
    }

    /**
     * Will add or remove attribute modifiers if the player has the tarot.
     */
    protected void updateAttributes(Player player, boolean hasCard) {
        AttributeModifier mod = getModifier();
        if (mod == null) return; // Not all cards might have attribute modifiers

        Holder<Attribute> targetAttr = getTargetAttribute();
        if (targetAttr == null) return;

        AttributeInstance inst = player.getAttribute(getTargetAttribute());
        if (inst == null) return;

        boolean hasMod = inst.hasModifier(mod.id());

        if (hasCard && !hasMod) {
            inst.addTransientModifier(mod);
            onModifierAdded(player, targetAttr);
            TarotCards.LOGGER.debug("Added modifier {} to player {}", mod.id(), player.getName().getString());
        } else if (!hasCard && hasMod) {
            inst.removeModifier(mod.id());
            onModifierRemoved(player, targetAttr);
            TarotCards.LOGGER.debug("Removed modifier {} from player {}", mod.id(), player.getName().getString());
        }
    }

    // Default implementations return null.
    // Subclasses only override these if they actually need attributes.
    protected Holder<Attribute> getTargetAttribute() { return null; }
    protected AttributeModifier getModifier() { return null; }

    protected void onModifierAdded(Player player, Holder<Attribute> attr) {}
    protected void onModifierRemoved(Player player, Holder<Attribute> attr) {}

    // Optional: for cards with unique logic
    protected void handleExtraLogic(Player player, boolean hasCard) {}

    public float onAttack(Player attacker, LivingEntity victim, DamageSource source, float amount) {
        return amount;
    }

    public float onHurt(@Nullable LivingEntity attacker, Player player, DamageSource source, float amount) {
        return amount;
    }

//    public float onDamage(LivingEntity entity, DamageSource source, float amount)  {
//        return amount;
//    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay tooltipDisplay, Consumer<Component> tooltip, TooltipFlag flag) {
        tooltip.accept(Component.translatable(this.getDescriptionId() + ".desc").withStyle(ChatFormatting.BLUE));
    }

}
