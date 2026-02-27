package io.github.suel_ki.tarotcards.common.item.deck;

import io.github.suel_ki.tarotcards.core.platform.TarotUtilPlatform;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;

public class TarotDeckItem extends Item {

    public TarotDeckItem() {
        super(new Properties().stacksTo(1).rarity(Rarity.UNCOMMON).fireResistant());
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
