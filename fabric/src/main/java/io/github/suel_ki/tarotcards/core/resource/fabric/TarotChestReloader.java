package io.github.suel_ki.tarotcards.core.resource.fabric;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import io.github.suel_ki.tarotcards.TarotCards;
import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;

import java.io.Reader;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

// To support datapacks
public class TarotChestReloader extends SimpleJsonResourceReloadListener implements IdentifiableResourceReloadListener {
    private static final Gson GSON = new Gson();
    private static final ResourceLocation ID = TarotCards.id("loot_targets");

    public static final Set<ResourceLocation> TARGET_CHESTS = new HashSet<>();

    public TarotChestReloader() {
        super(GSON, "loot_targets");
    }

    public static void forceReload(ResourceManager resourceManager) {
        TARGET_CHESTS.clear();

        resourceManager.listResources("loot_targets", (location) -> location.getPath().endsWith(".json"))
                .forEach((loc, resource) -> {
                    try (Reader reader = resource.openAsReader()) {
                        JsonElement json = GSON.fromJson(reader, JsonElement.class);
                        processJson(loc, json);
                    } catch (Exception e) {
                        TarotCards.LOGGER.error("Force reload failed for: {}", loc, e);
                    }
                });
    }

    private static void processJson(ResourceLocation fileId, JsonElement jsonElement) {
        try {
            if (jsonElement.isJsonObject() && jsonElement.getAsJsonObject().has("values")) {
                jsonElement.getAsJsonObject().getAsJsonArray("values").forEach(val -> {
                    String locationString = val.getAsString();
                    if (ResourceLocation.isValidResourceLocation(locationString)) {
                        TARGET_CHESTS.add(new ResourceLocation(locationString));
                    } else {
                        TarotCards.LOGGER.warn("Invalid ResourceLocation found in {}: {}", fileId, locationString);
                    }
                });
            }
        } catch (Exception e) {
            TarotCards.LOGGER.error("Failed to parse tarot chest file: {}", fileId, e);
        }
    }

    @Override
    public ResourceLocation getFabricId() {
        return ID;
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> object, ResourceManager resourceManager, ProfilerFiller profiler) {
        TARGET_CHESTS.clear();
        object.forEach(TarotChestReloader::processJson);
        TarotCards.LOGGER.info("Loaded {} chests from standard reload", TARGET_CHESTS.size());
    }
}