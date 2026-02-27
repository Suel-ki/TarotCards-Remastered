package io.github.suel_ki.tarotcards.common.access;

public interface TarotPlayerAccess {
    int tarotcards$getDeckSize();

    void tarotcards$setDeckSize(int size);

    default void tarotcards$addDeckSize(int amount) {
        tarotcards$setDeckSize(tarotcards$getDeckSize() + amount);
    }
}
