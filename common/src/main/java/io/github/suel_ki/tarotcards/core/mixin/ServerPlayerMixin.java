package io.github.suel_ki.tarotcards.core.mixin;

import com.mojang.authlib.GameProfile;
import io.github.suel_ki.tarotcards.common.access.TarotPlayerAccess;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayer.class)
public abstract class ServerPlayerMixin extends Player {

    public ServerPlayerMixin(Level level, BlockPos pos, float yRot, GameProfile gameProfile) {
        super(level, pos, yRot, gameProfile);
    }

    @Inject(
            method = "restoreFrom",
            at = @At("TAIL")
    )
    private void restoreFrom(ServerPlayer oldPlayer, boolean alive, CallbackInfo ci) {
        if (((Object)oldPlayer) instanceof TarotPlayerAccess oldAccess) {
            int oldSize = oldAccess.tarotcards$getDeckSize();
            ((TarotPlayerAccess)this).tarotcards$setDeckSize(oldSize);
        }
    }
}
