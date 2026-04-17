<div align="center">

# Tarot Cards: Remastered

[![neoforge](https://cdn.jsdelivr.net/gh/Hyperbole-Devs/vectors@neoforge_badges/assets/cozy/supported/neoforge_vector.svg)](https://modrinth.com/mod/tarotcards-remastered/versions?l=neoforge)
[![forge](https://cdn.jsdelivr.net/npm/@intergrav/devins-badges@3/assets/cozy/supported/forge_vector.svg)](https://modrinth.com/mod/tarotcards-remastered/versions?l=forge)
[![fabric](https://cdn.jsdelivr.net/npm/@intergrav/devins-badges@3/assets/cozy/supported/fabric_vector.svg)](https://modrinth.com/mod/tarotcards-remastered/versions?l=fabric)
[![Available on Modrinth](https://cdn.jsdelivr.net/npm/@intergrav/devins-badges@3/assets/cozy/available/modrinth_vector.svg)](https://modrinth.com/mod/tarotcards-remastered)
[![Available on Curseforge](https://cdn.jsdelivr.net/npm/@intergrav/devins-badges@3/assets/cozy/available/curseforge_vector.svg)](https://curseforge.com/minecraft/mc-mods/tarotcards-remastered)

[![Modrinth](https://img.shields.io/modrinth/dt/tarotcards-remastered?color=00AF5C&label=downloads&logo=modrinth)](https://modrinth.com/mod/tarotcards-remastered)
[![CurseForge](https://cf.way2muchnoise.eu/full_1473837_downloads.svg)](https://curseforge.com/minecraft/mc-mods/tarotcards-remastered)

</div>

## About
- This is the officially authorized continuation of the original [TarotCards](https://github.com/shiroroku/TarotCards), maintained and updated with the original author's blessing under the [MIT](https://github.com/shiroroku/TarotCards/blob/master/LICENSE).
- Find each of the 22 cards throughout the world, and receive their bonus by just carrying them! This small mod just adds the cards and their abilities. You can't craft them but instead you find them all in loot chests, such as buried treasure, shipwrecks, dungeons, and more.

## Changes
***The following text in bold italics indicates new or modified content.***
- ***Issue Fixes: resolved several legacy issues inherited from the original mod***
- ***Unified Config: now powered by [FzzyConfig](https://github.com/fzzyhmstrs/fconfig)***
- ***The Lovers & The World: support for FTB Teams***
- ***The Magician: uses the `piglin_loved` tag for better compatibility with gold items from other mods.***
- ***Pet Logic: Optimized ally detection to ensure your tamed pets and team members are correctly identified***
- ***Dedicated Tarot Slot: added a dedicated `tarot` slot for both Trinkets (Fabric) and Curios (Forge)***
- ***Enchantment Capping: added `the_highpriestess_extra_levels`. When capenchants is enabled, you can define how many levels above the vanilla maximum an enchantment book can reach***
- ***NBT Storage: tarot card data is now handled via nbt storage for better stability and easier integration with other mods***
- ***Commands: Added a set of player-specific deck management commands. These commands require `per_player_deck_size` to be set to true in the config to allow individual capacity limits (0-22) for each player***
- ***Level & Advancement Lock: Customize the minimum experience level or specific advancement required to activate each card's effect***

## Cards and their abilities
The abilities are all passive, and are applied while held or in the players inventory. Cards of the same type do not stack. Can be toggled on or off by using them.
- Death: Deal +20% more damage to non-undead mobs
- Judgement: 15% to deal double damage.
- Justice: 25% of Damage taken is also dealt to the attacker in full.
- Strength: Constant Strength 2.
- Temperance: 25% less hungry.
- The Chariot: +20% speed boost.
- The Devil: Attacks inflict weakness.
- The Emperor: Constant Hero Of The Village 3.
-  Empress: Nearby animals breed, babies grow faster.
- The Fool: Jump Boost 3.
- The Hanged Man: Receive 50% of damage taken as experience.
- The Hermit: +10 Armor when away from allies.
- The Hierophant: 150% Experience gain.
- The High Priestess: Upgrades held enchantment books with experience.
- The Lovers: Nearby allies are granted Regeneration 3.
- The Magician: Golden items won't break.
- The Moon: Constant Night Vision.
- The Star: +50% Base reach distance.
- The Sun: +25% Base health.
- The Tower: Immune to Fall Damage.
- The World: Slowness 3 to nearby mobs.
- The Wheel Of Fortune: +3 Luck.

## Loot
By default, cards have a 20% chance to spawn once in:
- Desert Pyramids
- Dungeons
-  Cities
- Nether Fortresses
- Mineshafts
- Shipwrecks
- Stronghold Libraries
- Temples in Villages
#### These locations can be modified via datapacks
- ***fabric: modify or override `data/tarotcards/loot_targets/target_chests.json`***
- forge: override the GLM JSON at `data/tarotcards/loot_modifiers/tarot_loot_modifier.json`

## The Tarot Deck
Holds one of each card for easy collection. Can be equipped as curio Applies their effect even while the cards are inside. Also fire immune!
~~There's currently an issue with the Tarot Deck being empty when switching to creative when on a server. Looking for a fix is taking longer than expected, its probably an issue with forge. I recommend not using the deck if you're planning to switch game modes.~~(Fixed)

## Config
### General
- tick_rate: How many ticks it takes to check if a player has a tarot or not, more = more performace but longer time to activate/deactivate effects
- ***tarot_deck_size: The maximum capacity of the tarot deck, determines the number of active slots in the deck gui (0-22)***
- ***per_player_deck_size: Allows individual player deck sizes to override the global setting. In this mode, tarot_deck_size is used as the default value***
- require_card_in_curio: Requires the card to be in your curio slot, or in the tarot deck (also in the curio slot) to function
- tarot_deck_applies_effects: If cards in the tarot deck apply their effects to the player holding it
- ***level_lock: If enabled, players must reach a specific experience level to activate the effects of a tarot card***
- ***advancement_lock: If enabled, players must complete specific advancements to unlock the power of tarot cards***
- ***min_xp_level_required: Define the minimum experience level required for each tarot card***
- ***required_advancements: Maps each tarot card to a specific advancement path. If empty or invalid, no advancement will be required***
#### Loot
- do_loot_generation: If Tarot Cards should be added to the default loot tables specified in data
- default_loot_chance: Chance a tarot card appears in default loot (0.75 = 75%)(1.0 = there will always be a single tarot card in chests)
#### Cards
- death_damagebonus: Percentage of damage increase to living (0.5 = +50%)
- judgement_damagechance: Percentage chance of dealing double damage (0.5 = 50%)
- justice_damagemultiplier: Percentage of damage returned to the attacker (1.0 = 100%)
- the_tower_damagenegation: Percentage of fall damage negated (0.5 = 50%)
- the_sun_healthboost: Percentage increase of base health (0.5 = +50%)
- the_star_reachboost: Percentage increase of reach distance (0.5 = +50%)
- strength_amplifier: Amplifier for effect (1 = II)
- the_fool_jumpboost: Amplifier for effect (2 = III)
- the_chariot_speedboost: Percentage increase of base speed (0.5 = +50%)
- temperance_reduction: Multiplier for hunger gained (0.5 = -50%, 0 = no change)
- the_devil_weaknessamplifier: Amplifier for effect (1 = II)
- the_emperpor_heroofvillagebonus: Amplifier for effect (1 = II)
- wheel_of_fortune_luckbonus: How much luck (not fortune) is added to the player
- the_hierophant_xpboost: Percentage increase of experience (2 = +200%)
- the_hanged_man_xpratio: How much of the damage is converted to experience
- the_hanged_man_xpcap: Max amount of experience that can be obtained from an instance of damage
- the_highpriestess_capenchants: Stops upgrading enchantment books when they are at max level
- the_highpriestess_upgradecost: How much each level of enchantment costs to upgrade (cost = enchantment_level * this_config)
- ***the_highpriestess_extra_levels: Allows enchantment books to be upgraded beyond their natural max level by this many additional levels (only works if capenchants is true)***
- the_empress_range: How close animals need to be to set lovemode
- the_hermit_allyrange: How far away the player must be from allies to activate
- the_hermit_armorbonus: How much armor is added when away from allies
- the_lovers_range: How close allies need to be to apply regeneration
- the_lovers_regenamplifier: Amplifier for effect (1 = II)
- the_world_range: How close non-allies need to be to apply slowness
- the_world_slownessamplifier: Amplifier for effect (9 = X)

## Command
***These commands require per_player_deck_size to be set to true in the config***
- `tarotcards decksize <target> get`
- `tarotcards decksize <target> set <size>`