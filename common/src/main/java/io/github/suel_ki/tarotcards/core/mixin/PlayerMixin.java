package io.github.suel_ki.tarotcards.core.mixin;

import io.github.suel_ki.tarotcards.TarotCards;
import io.github.suel_ki.tarotcards.common.access.TarotPlayerAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
public abstract class PlayerMixin extends LivingEntity implements TarotPlayerAccess {
    @Unique
    private static final EntityDataAccessor<Integer> tarotcards$DATA_DECK_SIZE = SynchedEntityData.defineId(Player.class, EntityDataSerializers.INT);

    protected PlayerMixin(EntityType<? extends LivingEntity> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(
            method = "defineSynchedData",
            at = @At("TAIL")
    )
    private void defineDeckSize(CallbackInfo ci) {
        this.entityData.define(tarotcards$DATA_DECK_SIZE, TarotCards.CONFIG.tarot_deck_size);
    }

    @Inject(
            method = "addAdditionalSaveData",
            at = @At("TAIL")
    )
    private void addAdditionalSaveData(CompoundTag tag, CallbackInfo ci) {
        tag.putInt("TarotDeckSize", this.tarotcards$getDeckSize());
    }

    @Inject(
            method = "readAdditionalSaveData",
            at = @At("TAIL")
    )
    private void readAdditionalSaveData(CompoundTag tag, CallbackInfo ci) {
        if (tag.contains("TarotDeckSize", Tag.TAG_INT)) {
            this.tarotcards$setDeckSize(tag.getInt("TarotDeckSize"));
        }
    }

    @Override
    public int tarotcards$getDeckSize() {
        boolean isPerPlayer = TarotCards.CONFIG.per_player_deck_size;

        int deckSize = isPerPlayer ?
                this.entityData.get(tarotcards$DATA_DECK_SIZE) :
                TarotCards.CONFIG.tarot_deck_size;

        return Mth.clamp(deckSize, 0, 22);
    }

    @Override
    public void tarotcards$setDeckSize(int size) {
        this.entityData.set(tarotcards$DATA_DECK_SIZE, size);
    }
}
