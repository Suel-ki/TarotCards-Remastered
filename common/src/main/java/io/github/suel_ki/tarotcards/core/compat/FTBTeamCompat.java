package io.github.suel_ki.tarotcards.core.compat;

import dev.ftb.mods.ftbteams.api.FTBTeamsAPI;
import io.github.suel_ki.tarotcards.core.platform.TarotUtilPlatform;
import net.minecraft.world.entity.player.Player;

import java.util.UUID;

public class FTBTeamCompat {
    private static final boolean LOADED = TarotUtilPlatform.isLoaded("ftbteams");

    public static boolean isSameTeamSafe(Player p1, Player p2) {
        return isSameTeamByUUID(p1.getUUID(), p2.getUUID());
    }

    public static boolean isSameTeamByUUID(UUID u1, UUID u2) {
        if (LOADED) {
            try {
                return FTBInternal.check(u1, u2);
            } catch (Throwable ignored) {}
        }
        return false;
    }

    private static class FTBInternal {
        private static boolean check(UUID u1, UUID u2) {
            return FTBTeamsAPI.api().getManager().arePlayersInSameTeam(u1, u2);
        }
    }
}
