package io.github.suel_ki.tarotcards.core.compat.neoforge;

import com.github.tartaricacid.touhoulittlemaid.api.event.MaidPickupEvent;
import io.github.suel_ki.tarotcards.common.item.tarot.TheHierophantTarot;
import net.neoforged.bus.api.SubscribeEvent;

public class MaidCompat {
    @SubscribeEvent
    public static void onMaidPickupXp(MaidPickupEvent.ExperienceResult event) {
        event.getExperienceOrb().value = TheHierophantTarot.handleOnPlayerPickupXp(event.getMaid(), event.getExperienceOrb().value);
    }
}
