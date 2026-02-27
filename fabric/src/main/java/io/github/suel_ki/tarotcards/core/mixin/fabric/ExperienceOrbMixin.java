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
public class ExperienceOrbMixin {

    @Shadow
    private int value;

    @Inject(
            method = "playerTouch",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/ExperienceOrb;repairPlayerItems(Lnet/minecraft/world/entity/player/Player;I)I")
    )
    private void modifyPickupXpValue(Player player, CallbackInfo ci) {
        this.value = TheHierophantTarot.handleOnPlayerPickupXp(player, this.value);
    }
}
