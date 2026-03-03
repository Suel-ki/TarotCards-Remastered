package io.github.suel_ki.tarotcards.core.init;

import io.github.suel_ki.tarotcards.common.advancement.TarotCriterionTrigger;
import io.github.suel_ki.tarotcards.core.platform.RegisterPlatform;

import java.util.function.Supplier;

public class TriggerInit {
    public static final Supplier<TarotCriterionTrigger> DECK_COLLECTION_TRIGGER = RegisterPlatform.registerTrigger("deck_collection", TarotCriterionTrigger::new);

    // Call during mod initialization to ensure registration
    public static void init() {}
}
