package io.github.suel_ki.tarotcards.forge.event;


import io.github.suel_ki.tarotcards.TarotCards;
import io.github.suel_ki.tarotcards.common.command.TarotCommand;
import io.github.suel_ki.tarotcards.common.item.TarotItem;
import io.github.suel_ki.tarotcards.common.item.tarot.*;
import io.github.suel_ki.tarotcards.core.platform.TarotUtilPlatform;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerXpEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;

import java.util.Set;

@Mod.EventBusSubscriber(modid = TarotCards.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class EventHandler {
    @SubscribeEvent
    public static void onRegisterCommands(RegisterCommandsEvent event) {
        TarotCommand.register(event.getDispatcher());
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.player.tickCount % TarotCards.CONFIG.tick_rate == 0 && event.side == LogicalSide.SERVER && event.phase == TickEvent.Phase.END) {
            event.player.level().getProfiler().push("TarotCards");

            Set<Item> activeCardsSnapshot = TarotUtilPlatform.getActiveTarots(event.player);
            for (TarotItem tarot : TarotItem.ITEMS_CARDS) {
                boolean hasThisCard = activeCardsSnapshot.contains(tarot);
                tarot.handleTick(event.player, hasThisCard);
            }

            event.player.level().getProfiler().pop();
        }
    }

    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event) {
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
    public static void onLivingDamage(LivingDamageEvent event) {
        if (event.getSource().getEntity() instanceof Player) {
          //  float amount = TarotUtilPlatform.handleOnDamage(event.getEntity(), event.getSource(), event.getAmount());
            float amount = JudgementTarot.handleOnDamage(event.getEntity(), event.getSource(), event.getAmount());
            event.setAmount(amount);
        }
    }

    @SubscribeEvent
    public static void onPlayerPickupXpEvent(PlayerXpEvent.PickupXp event) {
        event.getOrb().value = TheHierophantTarot.handleOnPlayerPickupXp(event.getEntity(), event.getOrb().value);
    }




}
