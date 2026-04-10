package io.github.suel_ki.tarotcards.core.compat;

import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import io.github.suel_ki.tarotcards.core.platform.TarotUtilPlatform;
import net.minecraft.world.entity.LivingEntity;

public class TouhouLittleMaidCompat {
    private static final boolean LOADED = TarotUtilPlatform.isLoaded("touhou_little_maid");

    public static boolean isMaid(LivingEntity entity) {
        if (LOADED) {
            return MaidInternal.check(entity);
        }
        return false;
    }

    private static class MaidInternal {
        private static boolean check(LivingEntity entity) {
            return entity instanceof EntityMaid;
        }
    }
}
