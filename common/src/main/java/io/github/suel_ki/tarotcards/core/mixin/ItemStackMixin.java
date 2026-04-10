package io.github.suel_ki.tarotcards.core.mixin;

import io.github.suel_ki.tarotcards.common.item.tarot.TheMagicianTarot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Consumer;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {

	@Shadow
	public abstract ItemStack copy();

	@Inject(
            method = "hurtAndBreak",
            at = @At("HEAD"),
            cancellable = true
	)
	public <T extends LivingEntity> void hurtAndBreak(int amount, T entity, Consumer<T> consumer, CallbackInfo ci) {
		if (!entity.level().isClientSide) {
			if (TheMagicianTarot.handleItemDamage(this.copy(), entity)) {
				ci.cancel();
			}
		}
	}

}
