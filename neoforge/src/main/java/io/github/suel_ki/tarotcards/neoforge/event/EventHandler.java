package io.github.suel_ki.tarotcards.neoforge.event;


import io.github.suel_ki.tarotcards.TarotCards;
import io.github.suel_ki.tarotcards.common.command.TarotCommand;
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
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.player.PlayerXpEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

import java.util.Set;

@EventBusSubscriber(modid = TarotCards.MOD_ID)
public class EventHandler {
    @SubscribeEvent
    public static void onRegisterCommands(RegisterCommandsEvent event) {
        TarotCommand.register(event.getDispatcher());
    }

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        if (event.getEntity().level().isClientSide()) return;
        Player player = event.getEntity();
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

    @SubscribeEvent
    public static void onLivingHurt(LivingIncomingDamageEvent event) {
        LivingEntity victim = event.getEntity();
        DamageSource source = event.getSource();
        float amount = event.getAmount();

        if (source.getEntity() instanceof Player attackerPlayer) {
            amount = TarotUtilPlatform.handleOnAttack(attackerPlayer, victim, source, amount);
        }

        if (victim instanceof Player playerVictim) {
            Entity attacker = source.getEntity();
            LivingEntity livingAttacker = (attacker instanceof LivingEntity le) ? le : null;

            amount = TarotUtilPlatform.handleOnHurt(livingAttacker, playerVictim, source, amount);
        }

        event.setAmount(amount);
    }

    @SubscribeEvent
    public static void onLivingDamage(LivingDamageEvent.Pre event) {
        LivingEntity victim = event.getEntity();
        DamageSource source = event.getSource();
        if (source.getEntity() instanceof Player) {
            float amount = event.getNewDamage();
          //  float amount = TarotUtilPlatform.handleOnDamage(event.getEntity(), event.getSource(), event.getAmount());
            amount = JudgementTarot.handleOnDamage(victim, source, amount);
            event.setNewDamage(amount);
        }
    }

    @SubscribeEvent
    public static void onPlayerPickupXpEvent(PlayerXpEvent.PickupXp event) {
        event.getOrb().setValue(TheHierophantTarot.handleOnPlayerPickupXp(event.getEntity(), event.getOrb().getValue()));
    }




}
