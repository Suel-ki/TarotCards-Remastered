package io.github.suel_ki.tarotcards.core.mixin;

import io.github.suel_ki.tarotcards.common.item.tarot.TemperanceTarot;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.food.FoodData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FoodData.class)
public abstract class FoodDataMixin {

	@Shadow
	private float exhaustionLevel;

	@Unique
	private ServerPlayer tarotCards$player;

	@Inject(
            method = "addExhaustion",
            at = @At("HEAD"),
            cancellable = true
    )
	public void addExhaustion(float amount, CallbackInfo ci) {
		float temp = TemperanceTarot.handleExhaustionAmount(amount, tarotCards$player);
		if (temp != amount) {
			this.exhaustionLevel = Math.min(this.exhaustionLevel + temp, 40.0F);
			ci.cancel();
		}
	}

	@Inject(
            method = "tick",
            at = @At("HEAD")
    )
	public void tick(ServerPlayer player, CallbackInfo ci) {
		this.tarotCards$player = player;
	}

}
