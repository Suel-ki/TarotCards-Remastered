package io.github.suel_ki.tarotcards.common.item.deck;

import io.github.suel_ki.tarotcards.core.platform.TarotUtilPlatform;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.component.ItemContainerContents;
import net.minecraft.world.level.Level;

public class TarotDeckItem extends Item {

    public TarotDeckItem(Properties properties) {
        super(properties.stacksTo(1).rarity(Rarity.UNCOMMON).fireResistant().component(DataComponents.CONTAINER, ItemContainerContents.EMPTY));
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
