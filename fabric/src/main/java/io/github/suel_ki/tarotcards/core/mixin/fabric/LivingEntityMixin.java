package io.github.suel_ki.tarotcards.core.mixin.fabric;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import io.github.suel_ki.tarotcards.common.item.tarot.JudgementTarot;
import io.github.suel_ki.tarotcards.core.platform.TarotUtilPlatform;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {

    @ModifyExpressionValue(
            method = "actuallyHurt",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/LivingEntity;getDamageAfterArmorAbsorb(Lnet/minecraft/world/damagesource/DamageSource;F)F")
    )
    private float modifyHurtAmount(float original, DamageSource source) {
        LivingEntity victim = (LivingEntity) (Object) this;

        if (victim instanceof Player) return original;

        if (source.getEntity() instanceof Player attacker) {
            return TarotUtilPlatform.handleOnAttack(attacker, victim, source, original);
        }

        return original;
    }

    @WrapOperation(
            method = "actuallyHurt",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/LivingEntity;setHealth(F)V")
    )
    private void onLivingDamage(LivingEntity entity, float health, Operation<Void> original, @Local(argsOnly = true) DamageSource source) {

        float currentHealth = entity.getHealth();
        float damage = currentHealth - health;

        if (source.getEntity() instanceof Player) {
            damage = JudgementTarot.handleOnDamage(entity, source, damage);
        }

        original.call(entity, currentHealth - damage);
    }
}
