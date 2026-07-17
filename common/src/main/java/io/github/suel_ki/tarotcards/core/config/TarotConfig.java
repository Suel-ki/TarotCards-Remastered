package io.github.suel_ki.tarotcards.core.config;

import io.github.suel_ki.tarotcards.TarotCards;
import io.github.suel_ki.tarotcards.common.item.TarotItem;
import me.fzzyhmstrs.fzzy_config.annotations.RootConfig;
import me.fzzyhmstrs.fzzy_config.annotations.Translation;
import me.fzzyhmstrs.fzzy_config.config.Config;
import me.fzzyhmstrs.fzzy_config.config.ConfigSection;
import me.fzzyhmstrs.fzzy_config.validation.collection.ValidatedIdentifierMap;
import me.fzzyhmstrs.fzzy_config.validation.minecraft.ValidatedIdentifier;
import me.fzzyhmstrs.fzzy_config.validation.misc.ValidatedString;
import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedDouble;
import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedFloat;
import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedInt;
import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedNumber;
import net.minecraft.resources.ResourceLocation;

import java.util.LinkedHashMap;

@RootConfig
@Translation(prefix = "tarotcards.config")
public class TarotConfig extends Config {
    public TarotConfig() {
        super(TarotCards.id("tarotcards"));
    }

    @ValidatedInt.Restrict(min = 1, max = 1200, type = ValidatedNumber.WidgetType.TEXTBOX)
    public int tick_rate = 20;
    @ValidatedInt.Restrict(min = 0, max = 22, type = ValidatedNumber.WidgetType.TEXTBOX)
    public int tarot_deck_size = 22;
    public boolean per_player_deck_size = false;
    public boolean require_card_in_curio = false;
    public boolean tarot_deck_applies_effects = true;
    public boolean level_lock = false;
    public boolean advancement_lock = false;
    public boolean effect_based_targeting= false;

    public ValidatedIdentifierMap<Integer> min_xp_level_required = new ValidatedIdentifierMap<>(
            new LinkedHashMap<>(),
            ValidatedIdentifier.ofTag(ResourceLocation.parse("tarotcards:the_fool"), TarotItem.TAROT),
            new ValidatedInt(0, Integer.MAX_VALUE, 0).instanceEntry()
    );

    public ValidatedIdentifierMap<String> required_advancements = new ValidatedIdentifierMap<>(
            new LinkedHashMap<>(),
            ValidatedIdentifier.ofTag(ResourceLocation.parse("tarotcards:the_fool"), TarotItem.TAROT),
            new ValidatedString("minecraft:story/mine_diamond")
    );

    public Loot loot = new Loot();
    @Translation(prefix = "tarotcards.config.loot")
    public static class Loot extends ConfigSection {
        @ValidatedFloat.Restrict(min = 0, max = 1, type = ValidatedNumber.WidgetType.TEXTBOX)
        public float default_loot_chance = 0.2F;
        public boolean do_loot_generation = true;
    }

    public Cards cards = new Cards();
    @Translation(prefix = "tarotcards.config.cards")
    public static class Cards extends ConfigSection {
        @ValidatedDouble.Restrict(min = 0, max = 100, type = ValidatedNumber.WidgetType.TEXTBOX)
        public double death_damagebonus = 0.2D;
        @ValidatedDouble.Restrict(min = 0D, max = 1D, type = ValidatedNumber.WidgetType.TEXTBOX)
        public double judgement_damagechance = 0.15D;
        @ValidatedDouble.Restrict(min = 0D, max = 100D, type = ValidatedNumber.WidgetType.TEXTBOX)
        public double justice_damagemultiplier = 0.25D;
        @ValidatedInt.Restrict(min = 0, max = 20, type = ValidatedNumber.WidgetType.TEXTBOX)
        public int strength_amplifier = 1;
        @ValidatedDouble.Restrict(min = 0D, max = 1D, type = ValidatedNumber.WidgetType.TEXTBOX)
        public double temperance_reduction = 0.25D;
        @ValidatedDouble.Restrict(min = 0D, max = 100D, type = ValidatedNumber.WidgetType.TEXTBOX)
        public double wheel_of_fortune_luckbonus = 3D;
        @ValidatedDouble.Restrict(min = 0D, max = 1D, type = ValidatedNumber.WidgetType.TEXTBOX)
        public double the_tower_damagenegation = 1D;
        @ValidatedDouble.Restrict(min = 0D, max = 100D, type = ValidatedNumber.WidgetType.TEXTBOX)
        public double the_chariot_speedboost = 0.2D;
        @ValidatedDouble.Restrict(min = 0D, max = 256D, type = ValidatedNumber.WidgetType.TEXTBOX)
        public double the_empress_range = 3D;
        @ValidatedInt.Restrict(min = 0, max = 20, type = ValidatedNumber.WidgetType.TEXTBOX)
        public int the_emperpor_heroofvillagebonus = 2;
        @ValidatedDouble.Restrict(min = 0D, max = 1D, type = ValidatedNumber.WidgetType.TEXTBOX)
        public double the_hanged_man_xpratio = 0.5D;
        @ValidatedInt.Restrict(min = 0, type = ValidatedNumber.WidgetType.TEXTBOX)
        public int the_hanged_man_xpcap = 25;
        @ValidatedDouble.Restrict(min = 0D, max = 256D, type = ValidatedNumber.WidgetType.TEXTBOX)
        public double the_hermit_allyrange = 10D;
        @ValidatedDouble.Restrict(min = 0D, max = 100D, type = ValidatedNumber.WidgetType.TEXTBOX)
        public double the_hermit_armorbonus = 10D;
        @ValidatedDouble.Restrict(min = 1D, max = 100D, type = ValidatedNumber.WidgetType.TEXTBOX)
        public double the_hierophant_xpboost = 1.5D;
        @ValidatedDouble.Restrict(min = 0D, max = 100D, type = ValidatedNumber.WidgetType.TEXTBOX)
        public double the_star_reachboost = 0.5D;
        @ValidatedDouble.Restrict(min = 0D, max = 100D, type = ValidatedNumber.WidgetType.TEXTBOX)
        public double the_sun_healthboost = 0.25D;
        @ValidatedDouble.Restrict(min = 0D, max = 256D, type = ValidatedNumber.WidgetType.TEXTBOX)
        public double the_world_range = 3D;
        @ValidatedInt.Restrict(min = 0, max = 20, type = ValidatedNumber.WidgetType.TEXTBOX)
        public int the_world_slownessamplifier = 3;
        @ValidatedDouble.Restrict(min = 0D, max = 256D, type = ValidatedNumber.WidgetType.TEXTBOX)
        public double the_lovers_range = 5D;
        @ValidatedInt.Restrict(min = 0, max = 20, type = ValidatedNumber.WidgetType.TEXTBOX)
        public int the_lovers_regenamplifier = 2;
        @ValidatedInt.Restrict(min = 0, max = 20, type = ValidatedNumber.WidgetType.TEXTBOX)
        public int the_devil_weaknessamplifier = 2;
        @ValidatedInt.Restrict(min = 0, max = 100, type = ValidatedNumber.WidgetType.TEXTBOX)
        public int the_fool_jumpboost = 2;
        @ValidatedInt.Restrict(min = 1, max = 100, type = ValidatedNumber.WidgetType.TEXTBOX)
        public int the_highpriestess_upgradecost = 3;
        @ValidatedInt.Restrict(min = 0, max = 100, type = ValidatedNumber.WidgetType.TEXTBOX)
        public int the_highpriestess_extra_levels = 3;
        public boolean the_highpriestess_capenchants = false;
        public boolean the_highpriestess_taxfree = false;
        public boolean the_highpriestess_skip_maxed = false;

    }

}
