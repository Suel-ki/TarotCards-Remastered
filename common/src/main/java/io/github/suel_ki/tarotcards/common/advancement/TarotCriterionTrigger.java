package io.github.suel_ki.tarotcards.common.advancement;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.advancements.criterion.ContextAwarePredicate;
import net.minecraft.advancements.criterion.EntityPredicate;
import net.minecraft.advancements.criterion.SimpleCriterionTrigger;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class TarotCriterionTrigger extends SimpleCriterionTrigger<TarotCriterionTrigger.Instance> {
    @Override
    public @NotNull Codec<Instance> codec() {
        return Instance.CODEC;
    }

    public void trigger(ServerPlayer player, int currentCount) {
        this.trigger(player, instance -> instance.test(currentCount));
    }

    public record Instance(Optional<ContextAwarePredicate> player, int requiredCount) implements SimpleInstance {

        public static final Codec<Instance> CODEC = RecordCodecBuilder.create(inst -> inst.group(
                EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player").forGetter(Instance::player),
                Codec.INT.optionalFieldOf("count", 22).forGetter(Instance::requiredCount)
        ).apply(inst, Instance::new));

        public boolean test(int currentCount) {
            return currentCount >= this.requiredCount;
        }
    }
}
