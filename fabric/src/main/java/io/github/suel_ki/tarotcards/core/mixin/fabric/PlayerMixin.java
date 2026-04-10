package io.github.suel_ki.tarotcards.core.mixin.fabric;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import io.github.suel_ki.tarotcards.common.item.tarot.*;
import io.github.suel_ki.tarotcards.core.platform.TarotUtilPlatform;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Player.class)
public class PlayerMixin {

    @ModifyExpressionValue(
            method = "actuallyHurt",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/player/Player;getDamageAfterArmorAbsorb(Lnet/minecraft/world/damagesource/DamageSource;F)F")
    )
    private float onLivingHurt(float amount, DamageSource source) {
        Player victim = (Player) (Object) this;

        Entity attacker = source.getEntity();

        float finalAmount = amount;

        if (source.getEntity() instanceof Player attackerPlayer) {
            finalAmount = TarotUtilPlatform.handleOnAttack(attackerPlayer, victim, source, finalAmount);
        }

        // Even if the attacker is null,
        // it still needs to be called because damage reduction cards need to handle environmental damage.
        LivingEntity livingAttacker = (attacker instanceof LivingEntity le) ? le : null;

        return TarotUtilPlatform.handleOnHurt(livingAttacker, victim, source, finalAmount);
    }

    @WrapOperation(
            method = "actuallyHurt",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/player/Player;setHealth(F)V")
    )
    private void onLivingDamage(Player player, float health, Operation<Void> original,
                                @Local(argsOnly = true) DamageSource source) {

        float currentHealth = player.getHealth();
        float damage = currentHealth - health;

        float finalDamage = JudgementTarot.handleOnDamage(player, source, damage);

        original.call(player, currentHealth - finalDamage);
    }
}
