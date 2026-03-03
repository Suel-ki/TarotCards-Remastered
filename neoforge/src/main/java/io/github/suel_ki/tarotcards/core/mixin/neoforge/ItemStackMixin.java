package io.github.suel_ki.tarotcards.core.mixin.neoforge;

import io.github.suel_ki.tarotcards.common.item.tarot.TheMagicianTarot;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {

	@Shadow
	public abstract ItemStack copy();

	@Inject(
            method = "hurtAndBreak(ILnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/entity/EquipmentSlot;)V",
            at = @At("HEAD"),
            cancellable = true
	)
	public void hurtAndBreak(int amount, LivingEntity entity, EquipmentSlot slot, CallbackInfo ci) {
		if (entity instanceof Player player && !entity.level().isClientSide) {
			if (TheMagicianTarot.handleItemDamage(this.copy(), player)) {
				ci.cancel();
			}
		}
	}

}
