package io.github.suel_ki.tarotcards.common.advancement;

import com.google.gson.JsonObject;
import io.github.suel_ki.tarotcards.TarotCards;
import net.minecraft.advancements.critereon.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.GsonHelper;
import org.jetbrains.annotations.NotNull;

public class TarotCriterionTrigger extends SimpleCriterionTrigger<TarotCriterionTrigger.Instance> {
    public static final ResourceLocation ID = TarotCards.id("deck_collection");

    @NotNull
    @Override
    public Instance createInstance(JsonObject json, ContextAwarePredicate predicate, DeserializationContext deserializationContext) {
        int required = GsonHelper.getAsInt(json, "count", 22);
        return new Instance(predicate, required);
    }

    @NotNull
    @Override
    public ResourceLocation getId() {
        return ID;
    }

    public void trigger(ServerPlayer player, int currentCount) {
        this.trigger(player, instance -> instance.test(currentCount));
    }

    static class Instance extends AbstractCriterionTriggerInstance {
        private final int requiredCount;

        Instance(ContextAwarePredicate predicate, int requiredCount) {
            super(ID, predicate);
            this.requiredCount = requiredCount;
        }

        @NotNull
        @Override
        public ResourceLocation getCriterion() {
            return ID;
        }

        boolean test(int currentCount) {
            return currentCount >= this.requiredCount;
        }

        @NotNull
        @Override
        public JsonObject serializeToJson(SerializationContext context) {
            JsonObject json = super.serializeToJson(context);
            json.addProperty("count", requiredCount);
            return json;
        }
    }
}
