package io.github.suel_ki.tarotcards.neoforge.event;


import io.github.suel_ki.tarotcards.TarotCards;
import io.github.suel_ki.tarotcards.common.command.TarotCommand;
import io.github.suel_ki.tarotcards.common.item.TarotItem;
import io.github.suel_ki.tarotcards.common.item.tarot.*;
import io.github.suel_ki.tarotcards.core.accessories.AccessoriesHandler;
import io.github.suel_ki.tarotcards.core.network.OpenDeckPayload;
import io.github.suel_ki.tarotcards.core.platform.TarotUtilPlatform;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.player.PlayerXpEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

import java.util.Set;

@EventBusSubscriber(modid = TarotCards.MOD_ID)
public class EventHandler {
    @SubscribeEvent
    public static void onRegisterCommands(RegisterCommandsEvent event) {
        TarotCommand.register(event.getDispatcher());
    }

    @SubscribeEvent
    public static void onPlayerTick(EntityTickEvent.Post event) {
        if (event.getEntity().level().isClientSide()) return;
        if (event.getEntity() instanceof LivingEntity entity) {
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
    }

    @SubscribeEvent
    public static void onLivingHurt(LivingIncomingDamageEvent event) {
        LivingEntity victim = event.getEntity();
        DamageSource source = event.getSource();
        float amount = event.getAmount();

        if (source.getEntity() instanceof LivingEntity attacker) {
            amount = TarotUtilPlatform.handleOnAttack(attacker, victim, source, amount);
        }

        Entity attacker = source.getEntity();
        LivingEntity livingAttacker = (attacker instanceof LivingEntity le) ? le : null;

        amount = TarotUtilPlatform.handleOnHurt(livingAttacker, victim, source, amount);

        event.setAmount(amount);
    }

    @SubscribeEvent
    public static void onLivingDamage(LivingDamageEvent.Pre event) {
        LivingEntity victim = event.getEntity();
        DamageSource source = event.getSource();
        float amount = event.getNewDamage();
        //  float amount = TarotUtilPlatform.handleOnDamage(event.getEntity(), event.getSource(), event.getAmount());
        amount = JudgementTarot.handleOnDamage(victim, source, amount);
        event.setNewDamage(amount);
    }

    @SubscribeEvent
    public static void onPlayerPickupXpEvent(PlayerXpEvent.PickupXp event) {
        event.getOrb().value = TheHierophantTarot.handleOnPlayerPickupXp(event.getEntity(), event.getOrb().value);
    }

    @SubscribeEvent
    public static void register(RegisterPayloadHandlersEvent event) {
        PayloadRegistrar registrar = event.registrar(TarotCards.MOD_ID).versioned("1");

        registrar.playToServer(OpenDeckPayload.TYPE, OpenDeckPayload.CODEC, (payload, context) -> {
            context.enqueueWork(() -> {
                if (context.player() instanceof ServerPlayer player) {
                    var deck = AccessoriesHandler.getDeck(player);
                    if (!deck.isEmpty()) {
                        TarotUtilPlatform.openTarotMenu(player, deck);
                    }
                }
            });
        });
    }

}
