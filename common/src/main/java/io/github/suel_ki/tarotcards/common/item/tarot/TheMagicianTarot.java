package io.github.suel_ki.tarotcards.common.item.tarot;

import io.github.suel_ki.tarotcards.TarotCards;
import io.github.suel_ki.tarotcards.common.item.TarotItem;
import io.github.suel_ki.tarotcards.core.init.ItemInit;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class TheMagicianTarot extends TarotItem {

    public static boolean handleItemDamage(ItemStack item, Player player) {
        if (hasTarot(player, ItemInit.the_magician.get())) {
            if (item.isDamageableItem() && item.getTags().anyMatch(t -> (t.equals(ItemTags.PIGLIN_LOVED)))) {

                TarotCards.LOGGER.debug("{} - Unbreakable gold items", ItemInit.the_magician.get());
                TarotCards.LOGGER.debug("For: {}", player);

                return true;
            }
        }
        return false;
    }

}
