package io.github.suel_ki.tarotcards.core.init;

import io.github.suel_ki.tarotcards.common.item.TarotItem;
import io.github.suel_ki.tarotcards.common.item.deck.TarotDeckItem;
import io.github.suel_ki.tarotcards.common.item.tarot.*;
import io.github.suel_ki.tarotcards.core.platform.RegisterPlatform;
import net.minecraft.world.item.Item;

import java.util.function.Supplier;

public class ItemInit {

    public static final Supplier<Item> tarot_deck = RegisterPlatform.registerItem("tarot_deck", TarotDeckItem::new);
    public static final Supplier<TarotItem> death = RegisterPlatform.registerItem("death", DeathTarot::new);
    public static final Supplier<TarotItem> judgement = RegisterPlatform.registerItem("judgement", JudgementTarot::new);
    public static final Supplier<TarotItem> justice = RegisterPlatform.registerItem("justice", JusticeTarot::new);
    public static final Supplier<TarotItem> strength = RegisterPlatform.registerItem("strength", StrengthTarot::new);
    public static final Supplier<TarotItem> temperance = RegisterPlatform.registerItem("temperance", TemperanceTarot::new);
    public static final Supplier<TarotItem> the_chariot = RegisterPlatform.registerItem("the_chariot", TheChariotTarot::new);
    public static final Supplier<TarotItem> the_devil = RegisterPlatform.registerItem("the_devil", TheDevilTarot::new);
    public static final Supplier<TarotItem> the_emperor = RegisterPlatform.registerItem("the_emperor", TheEmperorTarot::new);
    public static final Supplier<TarotItem> the_empress = RegisterPlatform.registerItem("the_empress", TheEmpressTarot::new);
    public static final Supplier<TarotItem> the_fool = RegisterPlatform.registerItem("the_fool", TheFoolTarot::new);
    public static final Supplier<TarotItem> the_hanged_man = RegisterPlatform.registerItem("the_hanged_man", TheHangedManTarot::new);
    public static final Supplier<TarotItem> the_hermit = RegisterPlatform.registerItem("the_hermit", TheHermitTarot::new);
    public static final Supplier<TarotItem> the_hierophant = RegisterPlatform.registerItem("the_hierophant", TheHierophantTarot::new);
    public static final Supplier<TarotItem> the_high_priestess = RegisterPlatform.registerItem("the_high_priestess", TheHighPriestessTarot::new);
    public static final Supplier<TarotItem> the_lovers = RegisterPlatform.registerItem("the_lovers", TheLoversTarot::new);
    public static final Supplier<TarotItem> the_magician = RegisterPlatform.registerItem("the_magician", TheMagicianTarot::new);
    public static final Supplier<TarotItem> the_moon = RegisterPlatform.registerItem("the_moon", TheMoonTarot::new);
    public static final Supplier<TarotItem> the_star = RegisterPlatform.registerItem("the_star", TheStarTarot::new);
    public static final Supplier<TarotItem> the_sun = RegisterPlatform.registerItem("the_sun", TheSunTarot::new);
    public static final Supplier<TarotItem> the_tower = RegisterPlatform.registerItem("the_tower", TheTowerTarot::new);
    public static final Supplier<TarotItem> the_world = RegisterPlatform.registerItem("the_world", TheWorldTarot::new);
    public static final Supplier<TarotItem> wheel_of_fortune = RegisterPlatform.registerItem("wheel_of_fortune", WheelOfFortuneTarot::new);

    // Call during mod initialization to ensure registration
    public static void init() {}
}
