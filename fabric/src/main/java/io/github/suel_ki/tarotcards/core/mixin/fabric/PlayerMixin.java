package io.github.suel_ki.tarotcards.core.mixin.fabric;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import io.github.suel_ki.tarotcards.TarotCards;
import io.github.suel_ki.tarotcards.common.item.TarotItem;
import io.github.suel_ki.tarotcards.common.item.tarot.*;
import io.github.suel_ki.tarotcards.core.platform.TarotUtilPlatform;
import net.minecraft.util.profiling.Profiler;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Set;

@Mixin(Player.class)
public class PlayerMixin {

    @Inject(
            method = "tick",
            at = @At("HEAD")
    )
    public void tick(CallbackInfo ci) {
        Player player = (Player) (Object) this;

        if (player.level().isClientSide()) {
            return;
        }

        if (player.tickCount % TarotCards.CONFIG.tick_rate == 0) {
            ProfilerFiller profiler = Profiler.get();
            profiler.push("TarotCards");
            Set<Item> activeCardsSnapshot = TarotUtilPlatform.getActiveTarots(player);

            for (TarotItem tarot : TarotItem.ITEMS_CARDS) {
                boolean hasThisCard = activeCardsSnapshot.contains(tarot);
                tarot.handleTick(player, hasThisCard);
            }

            profiler.pop();
        }
    }

    @ModifyExpressionValue(
            method = "actuallyHurt",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/player/Player;getDamageAfterArmorAbsorb(Lnet/minecraft/world/damagesource/DamageSource;F)F")
    )
    private float onLivingHurt(float amount, @Local(argsOnly = true) DamageSource source) {
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
