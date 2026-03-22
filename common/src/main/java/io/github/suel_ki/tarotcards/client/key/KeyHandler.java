package io.github.suel_ki.tarotcards.client.key;

import com.mojang.blaze3d.platform.InputConstants;
import io.github.suel_ki.tarotcards.TarotCards;
import net.minecraft.client.KeyMapping;
import org.lwjgl.glfw.GLFW;

public class KeyHandler {
    public static final KeyMapping.Category TAROT_CARDS = KeyMapping.Category.register(TarotCards.id("tarotcards"));
    public static final KeyMapping OPEN_DECK_KEY = new KeyMapping(
            "key.tarotcards.open_deck",
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_G,
            TAROT_CARDS
    );
}