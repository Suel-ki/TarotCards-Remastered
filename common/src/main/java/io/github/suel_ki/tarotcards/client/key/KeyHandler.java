package io.github.suel_ki.tarotcards.client.key;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import org.lwjgl.glfw.GLFW;

public class KeyHandler {
    public static final KeyMapping OPEN_DECK_KEY = new KeyMapping(
            "key.tarotcards.open_deck",
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_G,
            "itemGroup.tarotcards"
    );
}
