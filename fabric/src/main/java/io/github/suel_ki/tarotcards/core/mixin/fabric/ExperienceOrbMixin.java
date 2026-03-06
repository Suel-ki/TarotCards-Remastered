package io.github.suel_ki.tarotcards.core.mixin.fabric;

import io.github.suel_ki.tarotcards.common.item.tarot.TheHierophantTarot;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ExperienceOrb.class)
public abstract class ExperienceOrbMixin {

    @Shadow
    protected abstract void setValue(int value);

    @Shadow
    public abstract int getValue();

    @Inject(
            method = "playerTouch",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/ExperienceOrb;repairPlayerItems(Lnet/minecraft/server/level/ServerPlayer;I)I")
    )
    private void modifyPickupXpValue(Player player, CallbackInfo ci) {
        setValue(TheHierophantTarot.handleOnPlayerPickupXp(player, this.getValue()));
    }
}
