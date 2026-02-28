package io.github.suel_ki.tarotcards.common.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import io.github.suel_ki.tarotcards.TarotCards;
import io.github.suel_ki.tarotcards.common.access.TarotPlayerAccess;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

public class TarotCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("tarotcards")
                .requires(source -> source.hasPermission(2))
                .then(Commands.literal("decksize")
                        .then(Commands.argument("targets", EntityArgument.players())
                                .then(Commands.literal("get")
                                        .executes(context -> getDeckSize(context.getSource(), EntityArgument.getPlayer(context, "targets"))))
                                .then(Commands.literal("set")
                                        .then(Commands.argument("size", IntegerArgumentType.integer(0, 22))
                                                .executes(context -> setDeckSize(context.getSource(),
                                                        EntityArgument.getPlayer(context, "targets"),
                                                        IntegerArgumentType.getInteger(context, "size")))))
                        )
                )
        );
    }

    private static int getDeckSize(CommandSourceStack source, ServerPlayer player) {
        int size = ((TarotPlayerAccess) player).tarotcards$getDeckSize();
        source.sendSuccess(() -> Component.translatable("commands.tarotcards.decksize.get", player.getDisplayName(), size), false);
        return size;
    }

    private static int setDeckSize(CommandSourceStack source, ServerPlayer player, int size) {
        if (!TarotCards.CONFIG.per_player_deck_size) {
            source.sendFailure(Component.translatable("commands.tarotcards.decksize.warning"));
            return -1;
        }
        if (size < 0 || size > 22) {
            source.sendFailure(Component.translatable("commands.tarotcards.decksize.invalid"));
            return -1;
        }
        ((TarotPlayerAccess) player).tarotcards$setDeckSize(size);
        source.sendSuccess(() -> Component.translatable("commands.tarotcards.decksize.set", player.getDisplayName(), size), true);
        return 1;
    }
}
