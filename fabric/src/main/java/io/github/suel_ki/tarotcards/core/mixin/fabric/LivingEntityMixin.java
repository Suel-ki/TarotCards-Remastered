package io.github.suel_ki.tarotcards.core.mixin.fabric;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import io.github.suel_ki.tarotcards.TarotCards;
import io.github.suel_ki.tarotcards.common.item.TarotItem;
import io.github.suel_ki.tarotcards.common.item.tarot.JudgementTarot;
import io.github.suel_ki.tarotcards.core.platform.TarotUtilPlatform;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Set;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {

    @Inject(
            method = "tick",
            at = @At("HEAD")
    )
    public void tick(CallbackInfo ci) {
        LivingEntity entity = (LivingEntity) (Object) this;

        if (entity.level().isClientSide()) {
            return;
        }

        if (entity.tickCount % TarotCards.CONFIG.tick_rate == 0) {
            entity.level().getProfiler().push("TarotCards");
            Set<Item> activeCardsSnapshot = TarotUtilPlatform.getActiveTarots(entity);

            for (TarotItem tarot : TarotItem.ITEMS_CARDS) {
                boolean hasThisCard = activeCardsSnapshot.contains(tarot);
                tarot.handleTick(entity, hasThisCard);
            }

            entity.level().getProfiler().pop();
        }
    }

    @ModifyExpressionValue(
            method = "actuallyHurt",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/LivingEntity;getDamageAfterArmorAbsorb(Lnet/minecraft/world/damagesource/DamageSource;F)F")
    )
    private float modifyHurtAmount(float original, DamageSource source) {
        LivingEntity victim = (LivingEntity) (Object) this;

        if (victim instanceof Player) return original;

        if (source.getEntity() instanceof LivingEntity attacker) {
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

        if (source.getEntity() instanceof LivingEntity) {
            damage = JudgementTarot.handleOnDamage(entity, source, damage);
        }

        original.call(entity, currentHealth - damage);
    }
}
