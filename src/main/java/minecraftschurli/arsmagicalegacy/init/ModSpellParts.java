package minecraftschurli.arsmagicalegacy.init;

import java.util.function.Supplier;
import minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import minecraftschurli.arsmagicalegacy.api.registry.EtheriumRegistry;
import minecraftschurli.arsmagicalegacy.api.registry.SkillPointRegistry;
import minecraftschurli.arsmagicalegacy.api.registry.SkillRegistry;
import minecraftschurli.arsmagicalegacy.api.registry.SpellRegistry;
import minecraftschurli.arsmagicalegacy.api.skill.Skill;
import minecraftschurli.arsmagicalegacy.api.skill.SkillPoint;
import minecraftschurli.arsmagicalegacy.api.spell.SpellComponent;
import minecraftschurli.arsmagicalegacy.api.spell.SpellModifier;
import minecraftschurli.arsmagicalegacy.api.spell.SpellShape;
import minecraftschurli.arsmagicalegacy.objects.spell.component.*;
import minecraftschurli.arsmagicalegacy.objects.spell.modifier.Bounce;
import minecraftschurli.arsmagicalegacy.objects.spell.modifier.BuffPower;
import minecraftschurli.arsmagicalegacy.objects.spell.modifier.Color;
import minecraftschurli.arsmagicalegacy.objects.spell.modifier.Damage;
import minecraftschurli.arsmagicalegacy.objects.spell.modifier.Dismembering;
import minecraftschurli.arsmagicalegacy.objects.spell.modifier.Gravity;
import minecraftschurli.arsmagicalegacy.objects.spell.modifier.Healing;
import minecraftschurli.arsmagicalegacy.objects.spell.modifier.Lunar;
import minecraftschurli.arsmagicalegacy.objects.spell.modifier.MiningPower;
import minecraftschurli.arsmagicalegacy.objects.spell.modifier.Piercing;
import minecraftschurli.arsmagicalegacy.objects.spell.modifier.Prosperity;
import minecraftschurli.arsmagicalegacy.objects.spell.modifier.Radius;
import minecraftschurli.arsmagicalegacy.objects.spell.modifier.Range;
import minecraftschurli.arsmagicalegacy.objects.spell.modifier.RuneProcs;
import minecraftschurli.arsmagicalegacy.objects.spell.modifier.SilkTouch;
import minecraftschurli.arsmagicalegacy.objects.spell.modifier.Solar;
import minecraftschurli.arsmagicalegacy.objects.spell.modifier.Speed;
import minecraftschurli.arsmagicalegacy.objects.spell.modifier.TargetNonSolidBlocks;
import minecraftschurli.arsmagicalegacy.objects.spell.modifier.VelocityAdded;
import minecraftschurli.arsmagicalegacy.objects.spell.shape.AoE;
import minecraftschurli.arsmagicalegacy.objects.spell.shape.Beam;
import minecraftschurli.arsmagicalegacy.objects.spell.shape.Chain;
import minecraftschurli.arsmagicalegacy.objects.spell.shape.Channel;
import minecraftschurli.arsmagicalegacy.objects.spell.shape.MissingShape;
import minecraftschurli.arsmagicalegacy.objects.spell.shape.Projectile;
import minecraftschurli.arsmagicalegacy.objects.spell.shape.Rune;
import minecraftschurli.arsmagicalegacy.objects.spell.shape.Self;
import minecraftschurli.arsmagicalegacy.objects.spell.shape.Touch;
import minecraftschurli.arsmagicalegacy.objects.spell.shape.Wall;
import minecraftschurli.arsmagicalegacy.objects.spell.shape.Wave;
import minecraftschurli.arsmagicalegacy.objects.spell.shape.Zone;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.RegistryObject;

/**
 * @author Minecraftschurli
 * @version 2019-11-16
 */
public final class ModSpellParts implements IInit {
    //region skill points
    public static final Supplier<SkillPoint> SILVER_POINT = SkillPointRegistry.registerSkillPoint(-1, new SkillPoint(TextFormatting.GRAY, 0x999999, -1, -1).disableRender());
    public static final Supplier<SkillPoint> SKILL_POINT_1 = SkillPointRegistry.registerSkillPoint(0, new SkillPoint(TextFormatting.BLUE, 0x0000ff, 0, 1));
    public static final Supplier<SkillPoint> SKILL_POINT_2 = SkillPointRegistry.registerSkillPoint(1, new SkillPoint(TextFormatting.GREEN, 0x00ff00, 20, 2));
    public static final Supplier<SkillPoint> SKILL_POINT_3 = SkillPointRegistry.registerSkillPoint(2, new SkillPoint(TextFormatting.RED, 0xff0000, 30, 2));
    //public static final Supplier<SkillPoint> SKILL_POINT_4 = SpellRegistry.registerSkillPoint(new SkillPoint(4, TextFormatting.YELLOW, 0xffff00, 40, 3));
    //public static final Supplier<SkillPoint> SKILL_POINT_5 = SpellRegistry.registerSkillPoint(new SkillPoint(5, TextFormatting.LIGHT_PURPLE, 0xff00ff, 50, 3));
    //public static final Supplier<SkillPoint> SKILL_POINT_6 = SpellRegistry.registerSkillPoint(new SkillPoint(6, TextFormatting.AQUA, 0x00ffff, 60, 4));
    //endregion
    //region skills
    public static final RegistryObject<Skill> MANA_REGEN_1 = SkillRegistry.registerSkill(ArsMagicaAPI.MODID, "mana_regen1", SKILL_POINT_1, ModSkillTrees.TALENT, 275, 75);
    public static final RegistryObject<Skill> MANA_REGEN_2 = SkillRegistry.registerSkill(ArsMagicaAPI.MODID, "mana_regen2", SKILL_POINT_2, ModSkillTrees.TALENT, 275, 120, "arsmagicalegacy:mana_regen1");
    public static final RegistryObject<Skill> MANA_REGEN_3 = SkillRegistry.registerSkill(ArsMagicaAPI.MODID, "mana_regen3", SKILL_POINT_3, ModSkillTrees.TALENT, 275, 165, "arsmagicalegacy:mana_regen2");
    public static final RegistryObject<Skill> MAGE_POSSE_1 = SkillRegistry.registerSkill(ArsMagicaAPI.MODID, "mage_posse1", SKILL_POINT_2, ModSkillTrees.TALENT, 320, 120, "arsmagicalegacy:mana_regen2");
    public static final RegistryObject<Skill> MAGE_POSSE_2 = SkillRegistry.registerSkill(ArsMagicaAPI.MODID, "mage_posse2", SKILL_POINT_3, ModSkillTrees.TALENT, 320, 165, "arsmagicalegacy:mage_posse1");
    public static final RegistryObject<Skill> SPELL_MOTION = SkillRegistry.registerSkill(ArsMagicaAPI.MODID, "spell_motion", SKILL_POINT_2, ModSkillTrees.TALENT, 230, 120, "arsmagicalegacy:mana_regen2");
    public static final RegistryObject<Skill> AUGMENTED_CASTING = SkillRegistry.registerSkill(ArsMagicaAPI.MODID, "augmented_casting", SKILL_POINT_3, ModSkillTrees.TALENT, 230, 165, "arsmagicalegacy:spell_motion");
    public static final RegistryObject<Skill> AFFINITY_GAINS = SkillRegistry.registerSkill(ArsMagicaAPI.MODID, "affinity_gains", SKILL_POINT_1, ModSkillTrees.TALENT, 365, 120, "arsmagicalegacy:mana_regen1");
    public static final RegistryObject<Skill> EXTRA_SUMMONS = SkillRegistry.registerSkill(ArsMagicaAPI.MODID, "extra_summons", SKILL_POINT_3, ModSkillTrees.TALENT, 230, 210, "arsmagicalegacy:augmented_casting");
    public static final RegistryObject<Skill> SHIELD_OVERLOAD = SkillRegistry.registerSkill(ArsMagicaAPI.MODID, "shield_overload", SILVER_POINT, ModSkillTrees.TALENT, 275, 210);
    //endregion
    //region modifiers
    public static final RegistryObject<SpellModifier> BOUNCE = SpellRegistry.registerSpellModifier(ArsMagicaAPI.MODID, "bounce", SKILL_POINT_1, Bounce::new, ModSkillTrees.OFFENSE, 345, 70, "arsmagicalegacy:projectile");
    public static final RegistryObject<SpellModifier> BUFF_POWER = SpellRegistry.registerSpellModifier(ArsMagicaAPI.MODID, "buff_power", SILVER_POINT, BuffPower::new, ModSkillTrees.DEFENSE, 30, 135);
    public static final RegistryObject<SpellModifier> COLOR = SpellRegistry.registerSpellModifier(ArsMagicaAPI.MODID, "color", SKILL_POINT_1, Color::new, ModSkillTrees.TALENT, 230, 75);
    public static final RegistryObject<SpellModifier> DAMAGE = SpellRegistry.registerSpellModifier(ArsMagicaAPI.MODID, "damage", SKILL_POINT_3, Damage::new, ModSkillTrees.OFFENSE, 300, 315, "arsmagicalegacy:beam");
    public static final RegistryObject<SpellModifier> DISMEMBERING = SpellRegistry.registerSpellModifier(ArsMagicaAPI.MODID, "dismembering", SILVER_POINT, Dismembering::new, ModSkillTrees.OFFENSE, 75, 225);
    public static final RegistryObject<SpellModifier> DURATION = SpellRegistry.registerSpellModifier(ArsMagicaAPI.MODID, "duration", SKILL_POINT_3, Bounce::new, ModSkillTrees.DEFENSE, 312, 360, "arsmagicalegacy:chrono_anchor");
    public static final RegistryObject<SpellModifier> GRAVITY = SpellRegistry.registerSpellModifier(ArsMagicaAPI.MODID, "gravity", SKILL_POINT_1, Gravity::new, ModSkillTrees.OFFENSE, 255, 70, "arsmagicalegacy:projectile");
    public static final RegistryObject<SpellModifier> HEALING = SpellRegistry.registerSpellModifier(ArsMagicaAPI.MODID, "healing", SKILL_POINT_3, Healing::new, ModSkillTrees.DEFENSE, 402, 135, "arsmagicalegacy:heal");
    public static final RegistryObject<SpellModifier> LUNAR = SpellRegistry.registerSpellModifier(ArsMagicaAPI.MODID, "lunar", SKILL_POINT_3, Lunar::new, ModSkillTrees.UTILITY, 145, 210, "arsmagicalegacy:true_sight");
    public static final RegistryObject<SpellModifier> MINING_POWER = SpellRegistry.registerSpellModifier(ArsMagicaAPI.MODID, "mining_power", SKILL_POINT_2, MiningPower::new, ModSkillTrees.UTILITY, 185, 137, "arsmagicalegacy:silk_touch");
    public static final RegistryObject<SpellModifier> PIERCING = SpellRegistry.registerSpellModifier(ArsMagicaAPI.MODID, "piercing", SKILL_POINT_3, Piercing::new, ModSkillTrees.OFFENSE, 323, 215, "arsmagicalegacy:freeze");
    public static final RegistryObject<SpellModifier> PROSPERITY = SpellRegistry.registerSpellModifier(ArsMagicaAPI.MODID, "prosperity", SILVER_POINT, Prosperity::new, null, 0, 0);
    public static final RegistryObject<SpellModifier> RADIUS = SpellRegistry.registerSpellModifier(ArsMagicaAPI.MODID, "radius", SKILL_POINT_3, Radius::new, ModSkillTrees.UTILITY, 275, 390, "arsmagicalegacy:channel");
    public static final RegistryObject<SpellModifier> RANGE = SpellRegistry.registerSpellModifier(ArsMagicaAPI.MODID, "range", SKILL_POINT_3, Range::new, ModSkillTrees.UTILITY, 140, 345, "arsmagicalegacy:random_teleport");
    public static final RegistryObject<SpellModifier> RUNE_PROCS = SpellRegistry.registerSpellModifier(ArsMagicaAPI.MODID, "rune_procs", SKILL_POINT_2, RuneProcs::new, ModSkillTrees.DEFENSE, 157, 360, "arsmagicalegacy:rune");
    public static final RegistryObject<SpellModifier> SILK_TOUCH = SpellRegistry.registerSpellModifier(ArsMagicaAPI.MODID, "silk_touch", SKILL_POINT_1, SilkTouch::new, ModSkillTrees.UTILITY, 230, 137, "arsmagicalegacy:dig");
    public static final RegistryObject<SpellModifier> SOLAR = SpellRegistry.registerSpellModifier(ArsMagicaAPI.MODID, "solar", SKILL_POINT_3, Solar::new, ModSkillTrees.OFFENSE, 210, 255, "arsmagicalegacy:blind");
    public static final RegistryObject<SpellModifier> SPEED = SpellRegistry.registerSpellModifier(ArsMagicaAPI.MODID, "speed", SKILL_POINT_3, Speed::new, ModSkillTrees.DEFENSE, 202, 315, "arsmagicalegacy:accelerate", "arsmagicalegacy:flight");
    public static final RegistryObject<SpellModifier> TARGET_NON_SOLID_BLOCKS = SpellRegistry.registerSpellModifier(ArsMagicaAPI.MODID, "target_non_solid", SKILL_POINT_1, TargetNonSolidBlocks::new, ModSkillTrees.UTILITY, 230, 75, "arsmagicalegacy:touch");
    public static final RegistryObject<SpellModifier> VELOCITY_ADDED = SpellRegistry.registerSpellModifier(ArsMagicaAPI.MODID, "velocity_added", SKILL_POINT_3, VelocityAdded::new, ModSkillTrees.OFFENSE, 390, 290, "arsmagicalegacy:fling");
    //endregion
    //region shapes
    public static final RegistryObject<SpellShape> MISSING_SHAPE = SpellRegistry.registerSpellShape(ArsMagicaAPI.MODID, "null", null, MissingShape::new, null, 0, 0);
    public static final RegistryObject<SpellShape> AOE = SpellRegistry.registerSpellShape(ArsMagicaAPI.MODID, "aoe", SKILL_POINT_2, AoE::new, ModSkillTrees.OFFENSE, 300, 180, "arsmagicalegacy:frost_damage", "arsmagicalegacy:physical_damage", "arsmagicalegacy:fire_damage", "arsmagicalegacy:lightning_damage", "arsmagicalegacy:magic_damage");
    public static final RegistryObject<SpellShape> BEAM = SpellRegistry.registerSpellShape(ArsMagicaAPI.MODID, "beam", SKILL_POINT_3, Beam::new, ModSkillTrees.OFFENSE, 300, 270, "arsmagicalegacy:aoe");
    public static final RegistryObject<SpellShape> CHAIN = SpellRegistry.registerSpellShape(ArsMagicaAPI.MODID, "chain", SKILL_POINT_3, Chain::new, ModSkillTrees.UTILITY, 455, 210, "arsmagicalegacy:grow");
    public static final RegistryObject<SpellShape> CHANNEL = SpellRegistry.registerSpellShape(ArsMagicaAPI.MODID, "channel", SKILL_POINT_2, Channel::new, ModSkillTrees.UTILITY, 275, 345, "arsmagicalegacy:attract", "arsmagicalegacy:telekinesis");
    public static final RegistryObject<SpellShape> PROJECTILE = SpellRegistry.registerSpellShape(ArsMagicaAPI.MODID, "projectile", SKILL_POINT_1, Projectile::new, ModSkillTrees.OFFENSE, 300, 45);
    public static final RegistryObject<SpellShape> RUNE = SpellRegistry.registerSpellShape(ArsMagicaAPI.MODID, "rune", SKILL_POINT_2, Rune::new, ModSkillTrees.DEFENSE, 157, 315, "arsmagicalegacy:accelerate", "arsmagicalegacy:entangle");
    public static final RegistryObject<SpellShape> SELF = SpellRegistry.registerSpellShape(ArsMagicaAPI.MODID, "self", SKILL_POINT_1, Self::new, ModSkillTrees.DEFENSE, 267, 45);
    public static final RegistryObject<SpellShape> TOUCH = SpellRegistry.registerSpellShape(ArsMagicaAPI.MODID, "touch", SKILL_POINT_1, Touch::new, ModSkillTrees.UTILITY, 275, 75);
    public static final RegistryObject<SpellShape> WALL = SpellRegistry.registerSpellShape(ArsMagicaAPI.MODID, "wall", SKILL_POINT_2, Wall::new, ModSkillTrees.DEFENSE, 87, 200, "arsmagicalegacy:repel");
    public static final RegistryObject<SpellShape> WAVE = SpellRegistry.registerSpellShape(ArsMagicaAPI.MODID, "wave", SKILL_POINT_3, Wave::new, ModSkillTrees.OFFENSE, 367, 315, "arsmagicalegacy:beam", "arsmagicalegacy:fling");
    public static final RegistryObject<SpellShape> ZONE = SpellRegistry.registerSpellShape(ArsMagicaAPI.MODID, "zone", SKILL_POINT_3, Zone::new, ModSkillTrees.DEFENSE, 357, 225, "arsmagicalegacy:dispel");
    //endregion
    //region components
    public static final RegistryObject<SpellComponent> ABSORPTION = SpellRegistry.registerSpellComponent(ArsMagicaAPI.MODID, "absorption", SKILL_POINT_3, Absorption::new, ModSkillTrees.DEFENSE, 312, 270, "arsmagicalegacy:shield");
    public static final RegistryObject<SpellComponent> ACCELERATE = SpellRegistry.registerSpellComponent(ArsMagicaAPI.MODID, "accelerate", SKILL_POINT_2, Accelerate::new, ModSkillTrees.DEFENSE, 177, 245, "arsmagicalegacy:swift_swim");
    public static final RegistryObject<SpellComponent> ASTRAL_DISTORTION = SpellRegistry.registerSpellComponent(ArsMagicaAPI.MODID, "astral_distortion", SKILL_POINT_2, AstralDistortion::new, ModSkillTrees.OFFENSE, 367, 215, "arsmagicalegacy:magic_damage", "arsmagicalegacy:frost_damage");
    public static final RegistryObject<SpellComponent> ATTRACT = SpellRegistry.registerSpellComponent(ArsMagicaAPI.MODID, "attract", SKILL_POINT_2, Attract::new, ModSkillTrees.UTILITY, 245, 300, "arsmagicalegacy:rift");
    public static final RegistryObject<SpellComponent> BANISH_RAIN = SpellRegistry.registerSpellComponent(ArsMagicaAPI.MODID, "banish_rain", SKILL_POINT_2, BanishRain::new, ModSkillTrees.UTILITY, 365, 345, "arsmagicalegacy:drought");
    public static final RegistryObject<SpellComponent> BLIND = SpellRegistry.registerSpellComponent(ArsMagicaAPI.MODID, "blind", SKILL_POINT_2, Blind::new, ModSkillTrees.OFFENSE, 233, 180, "arsmagicalegacy:fire_damage", "arsmagicalegacy:lightning_damage");
    public static final RegistryObject<SpellComponent> BLIZZARD = SpellRegistry.registerSpellComponent(ArsMagicaAPI.MODID, "blizzard", SILVER_POINT, Blizzard::new, ModSkillTrees.OFFENSE, 75, 45);
    public static final RegistryObject<SpellComponent> CHARM = SpellRegistry.registerSpellComponent(ArsMagicaAPI.MODID, "charm", SKILL_POINT_1, Charm::new, ModSkillTrees.UTILITY, 315, 235, "arsmagicalegacy:light");
    public static final RegistryObject<SpellComponent> CHRONO_ANCHOR = SpellRegistry.registerSpellComponent(ArsMagicaAPI.MODID, "chrono_anchor", SKILL_POINT_3, ChronoAnchor::new, ModSkillTrees.DEFENSE, 312, 315, "arsmagicalegacy:reflect");
    public static final RegistryObject<SpellComponent> CREATE_WATER = SpellRegistry.registerSpellComponent(ArsMagicaAPI.MODID, "create_water", SKILL_POINT_2, CreateWater::new, ModSkillTrees.UTILITY, 365, 255, "arsmagicalegacy:light");
    public static final RegistryObject<SpellComponent> DAYLIGHT = SpellRegistry.registerSpellComponent(ArsMagicaAPI.MODID, "daylight", SILVER_POINT, Daylight::new, ModSkillTrees.UTILITY, 75, 45);
    public static final RegistryObject<SpellComponent> DIG = SpellRegistry.registerSpellComponent(ArsMagicaAPI.MODID, "dig", SKILL_POINT_1, Dig::new, ModSkillTrees.UTILITY, 275, 120, "arsmagicalegacy:touch");
    public static final RegistryObject<SpellComponent> DISARM = SpellRegistry.registerSpellComponent(ArsMagicaAPI.MODID, "disarm", SKILL_POINT_1, Disarm::new, ModSkillTrees.UTILITY, 230, 210, "arsmagicalegacy:light");
    public static final RegistryObject<SpellComponent> DISPEL = SpellRegistry.registerSpellComponent(ArsMagicaAPI.MODID, "dispel", SKILL_POINT_2, Dispel::new, ModSkillTrees.DEFENSE, 357, 180, "arsmagicalegacy:heal");
    public static final RegistryObject<SpellComponent> DIVINE_INTERVENTION = SpellRegistry.registerSpellComponent(ArsMagicaAPI.MODID, "divine_intervention", SKILL_POINT_3, DivineIntervention::new, ModSkillTrees.UTILITY, 172, 480, "arsmagicalegacy:recall", "arsmagicalegacy:mark");
    public static final RegistryObject<SpellComponent> DROUGHT = SpellRegistry.registerSpellComponent(ArsMagicaAPI.MODID, "drought", SKILL_POINT_2, Drought::new, ModSkillTrees.UTILITY, 365, 300, "arsmagicalegacy:light");
    public static final RegistryObject<SpellComponent> DROWN = SpellRegistry.registerSpellComponent(ArsMagicaAPI.MODID, "drown", SKILL_POINT_1, Drown::new, ModSkillTrees.OFFENSE, 435, 135, "arsmagicalegacy:magic_damage");
    public static final RegistryObject<SpellComponent> ENDER_INTERVENTION = SpellRegistry.registerSpellComponent(ArsMagicaAPI.MODID, "ender_intervention", SKILL_POINT_3, EnderIntervention::new, ModSkillTrees.UTILITY, 198, 480, "arsmagicalegacy:recall", "arsmagicalegacy:mark");
    public static final RegistryObject<SpellComponent> ENTANGLE = SpellRegistry.registerSpellComponent(ArsMagicaAPI.MODID, "entangle", SKILL_POINT_2, Entangle::new, ModSkillTrees.DEFENSE, 132, 245, "arsmagicalegacy:repel");
    public static final RegistryObject<SpellComponent> FALLING_STAR = SpellRegistry.registerSpellComponent(ArsMagicaAPI.MODID, "falling_star", SILVER_POINT, FallingStar::new, ModSkillTrees.OFFENSE, 75, 90);
    public static final RegistryObject<SpellComponent> FIRE_DAMAGE = SpellRegistry.registerSpellComponent(ArsMagicaAPI.MODID, "fire_damage", SKILL_POINT_1, FireDamage::new, ModSkillTrees.OFFENSE, 210, 135, "arsmagicalegacy:physical_damage");
    public static final RegistryObject<SpellComponent> FIRE_RAIN = SpellRegistry.registerSpellComponent(ArsMagicaAPI.MODID, "fire_rain", SILVER_POINT, FireRain::new, ModSkillTrees.OFFENSE, 75, 135);
    public static final RegistryObject<SpellComponent> FLIGHT = SpellRegistry.registerSpellComponent(ArsMagicaAPI.MODID, "flight", SKILL_POINT_3, Flight::new, ModSkillTrees.DEFENSE, 222, 270, "arsmagicalegacy:levitate");
    public static final RegistryObject<SpellComponent> FLING = SpellRegistry.registerSpellComponent(ArsMagicaAPI.MODID, "fling", SKILL_POINT_2, Fling::new, ModSkillTrees.OFFENSE, 390, 245, "arsmagicalegacy:knockback");
    public static final RegistryObject<SpellComponent> FORGE = SpellRegistry.registerSpellComponent(ArsMagicaAPI.MODID, "forge", SKILL_POINT_2, Forge::new, ModSkillTrees.OFFENSE, 120, 135, "arsmagicalegacy:ignition");
    public static final RegistryObject<SpellComponent> FREEZE = SpellRegistry.registerSpellComponent(ArsMagicaAPI.MODID, "freeze", SKILL_POINT_2, Freeze::new, ModSkillTrees.OFFENSE, 345, 180, "arsmagicalegacy:frost_damage");
    public static final RegistryObject<SpellComponent> FROST_DAMAGE = SpellRegistry.registerSpellComponent(ArsMagicaAPI.MODID, "frost_damage", SKILL_POINT_1, FrostDamage::new, ModSkillTrees.OFFENSE, 345, 135, "arsmagicalegacy:magic_damage");
    public static final RegistryObject<SpellComponent> FURY = SpellRegistry.registerSpellComponent(ArsMagicaAPI.MODID, "fury", SKILL_POINT_3, Fury::new, ModSkillTrees.OFFENSE, 255, 315, "arsmagicalegacy:beam", "arsmagicalegacy:storm");
    public static final RegistryObject<SpellComponent> GRAVITY_WELL = SpellRegistry.registerSpellComponent(ArsMagicaAPI.MODID, "gravity_well", SKILL_POINT_2, GravityWell::new, ModSkillTrees.DEFENSE, 222, 180, "arsmagicalegacy:slowfall");
    public static final RegistryObject<SpellComponent> GROW = SpellRegistry.registerSpellComponent(ArsMagicaAPI.MODID, "grow", SKILL_POINT_3, Grow::new, ModSkillTrees.UTILITY, 410, 210, "arsmagicalegacy:drought", "arsmagicalegacy:create_water", "arsmagicalegacy:plant", "arsmagicalegacy:plow", "arsmagicalegacy:harvest_plants");
    public static final RegistryObject<SpellComponent> HARVEST = SpellRegistry.registerSpellComponent(ArsMagicaAPI.MODID, "harvest", SKILL_POINT_2, Harvest::new, ModSkillTrees.UTILITY, 365, 120, "arsmagicalegacy:light");
    public static final RegistryObject<SpellComponent> HASTE = SpellRegistry.registerSpellComponent(ArsMagicaAPI.MODID, "haste", SKILL_POINT_1, Haste::new, ModSkillTrees.DEFENSE, 177, 155, "arsmagicalegacy:slowfall");
    public static final RegistryObject<SpellComponent> HEAL = SpellRegistry.registerSpellComponent(ArsMagicaAPI.MODID, "heal", SKILL_POINT_1, Heal::new, ModSkillTrees.DEFENSE, 357, 135, "arsmagicalegacy:regeneration");
    public static final RegistryObject<SpellComponent> IGNITION = SpellRegistry.registerSpellComponent(ArsMagicaAPI.MODID, "ignition", SKILL_POINT_2, Ignition::new, ModSkillTrees.OFFENSE, 165, 135, "arsmagicalegacy:fire_damage");
    public static final RegistryObject<SpellComponent> INVISIBILITY = SpellRegistry.registerSpellComponent(ArsMagicaAPI.MODID, "invisibility", SKILL_POINT_2, Invisiblity::new, ModSkillTrees.UTILITY, 185, 255, "arsmagicalegacy:true_sight");
    public static final RegistryObject<SpellComponent> JUMP_BOOST = SpellRegistry.registerSpellComponent(ArsMagicaAPI.MODID, "jump_boost", SKILL_POINT_1, JumpBoost::new, ModSkillTrees.DEFENSE, 222, 90, "arsmagicalegacy:self");
    public static final RegistryObject<SpellComponent> KNOCKBACK = SpellRegistry.registerSpellComponent(ArsMagicaAPI.MODID, "knockback", SKILL_POINT_2, Knockback::new, ModSkillTrees.OFFENSE, 390, 180, "arsmagicalegacy:magic_damage");
    public static final RegistryObject<SpellComponent> LEVITATION = SpellRegistry.registerSpellComponent(ArsMagicaAPI.MODID, "levitation", SKILL_POINT_2, Levitation::new, ModSkillTrees.DEFENSE, 222, 225, "arsmagicalegacy:gravity_well");
    public static final RegistryObject<SpellComponent> LIFE_DRAIN = SpellRegistry.registerSpellComponent(ArsMagicaAPI.MODID, "life_drain", SKILL_POINT_2, LifeDrain::new, ModSkillTrees.DEFENSE, 312, 180, "arsmagicalegacy:life_tap");
    public static final RegistryObject<SpellComponent> LIFE_TAP = SpellRegistry.registerSpellComponent(ArsMagicaAPI.MODID, "life_tap", SKILL_POINT_2, LifeTap::new, ModSkillTrees.DEFENSE, 312, 135, "arsmagicalegacy:heal");
    public static final RegistryObject<SpellComponent> LIGHT = SpellRegistry.registerSpellComponent(ArsMagicaAPI.MODID, "light", SKILL_POINT_1, Light::new, ModSkillTrees.UTILITY, 275, 165, "arsmagicalegacy:dig");
    public static final RegistryObject<SpellComponent> LIGHTNING_DAMAGE = SpellRegistry.registerSpellComponent(ArsMagicaAPI.MODID, "lightning_damage", SKILL_POINT_1, LightningDamage::new, ModSkillTrees.OFFENSE, 255, 135, "arsmagicalegacy:fire_damage");
    public static final RegistryObject<SpellComponent> MAGIC_DAMAGE = SpellRegistry.registerSpellComponent(ArsMagicaAPI.MODID, "magic_damage", SKILL_POINT_1, MagicDamage::new, ModSkillTrees.OFFENSE, 390, 135, "arsmagicalegacy:physical_damage");
    public static final RegistryObject<SpellComponent> MANA_BLAST = SpellRegistry.registerSpellComponent(ArsMagicaAPI.MODID, "mana_blast", SILVER_POINT, ManaBlast::new, ModSkillTrees.OFFENSE, 75, 180);
    public static final RegistryObject<SpellComponent> MANA_DRAIN = SpellRegistry.registerSpellComponent(ArsMagicaAPI.MODID, "mana_drain", SKILL_POINT_2, ManaDrain::new, ModSkillTrees.DEFENSE, 312, 225, "arsmagicalegacy:life_drain");
    public static final RegistryObject<SpellComponent> MANA_SHIELD = SpellRegistry.registerSpellComponent(ArsMagicaAPI.MODID, "mana_shield", SILVER_POINT, ManaShield::new, ModSkillTrees.DEFENSE, 30, 90);
    public static final RegistryObject<SpellComponent> MOONRISE = SpellRegistry.registerSpellComponent(ArsMagicaAPI.MODID, "moonrise", SILVER_POINT, Moonrise::new, ModSkillTrees.UTILITY, 75, 90);
    //TODO nausea
    public static final RegistryObject<SpellComponent> NIGHT_VISION = SpellRegistry.registerSpellComponent(ArsMagicaAPI.MODID, "night_vision", SKILL_POINT_1, NightVision::new, ModSkillTrees.UTILITY, 185, 165, "arsmagicalegacy:light");
    public static final RegistryObject<SpellComponent> PHYSICAL_DAMAGE = SpellRegistry.registerSpellComponent(ArsMagicaAPI.MODID, "physical_damage", SKILL_POINT_1, PhysicalDamage::new, ModSkillTrees.OFFENSE, 300, 90, "arsmagicalegacy:projectile");
    public static final RegistryObject<SpellComponent> PLACE_BLOCK = SpellRegistry.registerSpellComponent(ArsMagicaAPI.MODID, "place_block", SKILL_POINT_1, PlaceBlock::new, ModSkillTrees.UTILITY, 185, 93, "arsmagicalegacy:dig");
    public static final RegistryObject<SpellComponent> PLANT = SpellRegistry.registerSpellComponent(ArsMagicaAPI.MODID, "plant", SKILL_POINT_1, Plant::new, ModSkillTrees.UTILITY, 365, 210, "arsmagicalegacy:light");
    public static final RegistryObject<SpellComponent> PLOW = SpellRegistry.registerSpellComponent(ArsMagicaAPI.MODID, "plow", SKILL_POINT_1, Plow::new, ModSkillTrees.UTILITY, 365, 165, "arsmagicalegacy:light");
    public static final RegistryObject<SpellComponent> RANDOM_TELEPORT = SpellRegistry.registerSpellComponent(ArsMagicaAPI.MODID, "random_teleport", SKILL_POINT_1, RandomTeleport::new, ModSkillTrees.UTILITY, 185, 300, "arsmagicalegacy:invisibility");
    public static final RegistryObject<SpellComponent> RECALL = SpellRegistry.registerSpellComponent(ArsMagicaAPI.MODID, "recall", SKILL_POINT_2, Recall::new, ModSkillTrees.UTILITY, 215, 435, "arsmagicalegacy:transplace");
    public static final RegistryObject<SpellComponent> REGENERATION = SpellRegistry.registerSpellComponent(ArsMagicaAPI.MODID, "regeneration", SKILL_POINT_1, Regeneration::new, ModSkillTrees.DEFENSE, 357, 90, "arsmagicalegacy:self");
    public static final RegistryObject<SpellComponent> REPEL = SpellRegistry.registerSpellComponent(ArsMagicaAPI.MODID, "repel", SKILL_POINT_2, Repel::new, ModSkillTrees.DEFENSE, 132, 200, "arsmagicalegacy:slow");
    public static final RegistryObject<SpellComponent> RIFT = SpellRegistry.registerSpellComponent(ArsMagicaAPI.MODID, "rift", SKILL_POINT_2, Rift::new, ModSkillTrees.UTILITY, 275, 255, "arsmagicalegacy:light");
    //TODO scramble synapses
    public static final RegistryObject<SpellComponent> SHIELD = SpellRegistry.registerSpellComponent(ArsMagicaAPI.MODID, "shield", SKILL_POINT_1, Shield::new, ModSkillTrees.DEFENSE, 357, 270, "arsmagicalegacy:zone");
    public static final RegistryObject<SpellComponent> SHRINK = SpellRegistry.registerSpellComponent(ArsMagicaAPI.MODID, "shrink", SKILL_POINT_1, Shrink::new, ModSkillTrees.DEFENSE, 402, 90, "arsmagicalegacy:regeneration");
    public static final RegistryObject<SpellComponent> SILENCE = SpellRegistry.registerSpellComponent(ArsMagicaAPI.MODID, "silence", SKILL_POINT_3, Silence::new, ModSkillTrees.OFFENSE, 345, 245, "arsmagicalegacy:astral_distortion");
    public static final RegistryObject<SpellComponent> SLOW = SpellRegistry.registerSpellComponent(ArsMagicaAPI.MODID, "slow", SKILL_POINT_1, Slow::new, ModSkillTrees.DEFENSE, 132, 155, "arsmagicalegacy:slowfall");
    public static final RegistryObject<SpellComponent> SLOWFALL = SpellRegistry.registerSpellComponent(ArsMagicaAPI.MODID, "slowfall", SKILL_POINT_1, Slowfall::new, ModSkillTrees.DEFENSE, 222, 135, "arsmagicalegacy:leap");
    public static final RegistryObject<SpellComponent> SPELL_REFLECT = SpellRegistry.registerSpellComponent(ArsMagicaAPI.MODID, "spell_reflect", SKILL_POINT_3, SpellReflect::new, ModSkillTrees.DEFENSE, 357, 315, "arsmagicalegacy:shield");
    public static final RegistryObject<SpellComponent> STORM = SpellRegistry.registerSpellComponent(ArsMagicaAPI.MODID, "storm", SKILL_POINT_3, Storm::new, ModSkillTrees.OFFENSE, 255, 225, "arsmagicalegacy:lightning_damage");
    public static final RegistryObject<SpellComponent> SUMMON = SpellRegistry.registerSpellComponent(ArsMagicaAPI.MODID, "summon", SKILL_POINT_2, Summon::new, ModSkillTrees.DEFENSE, 267, 135, "arsmagicalegacy:life_tap");
    public static final RegistryObject<SpellComponent> SWIFT_SWIM = SpellRegistry.registerSpellComponent(ArsMagicaAPI.MODID, "swift_swim", SKILL_POINT_1, SwiftSwim::new, ModSkillTrees.DEFENSE, 177, 200, "arsmagicalegacy:haste");
    public static final RegistryObject<SpellComponent> TELEKINESIS = SpellRegistry.registerSpellComponent(ArsMagicaAPI.MODID, "telekinesis", SKILL_POINT_2, Telekinesis::new, ModSkillTrees.UTILITY, 305, 300, "arsmagicalegacy:rift");
    public static final RegistryObject<SpellComponent> TRANSPLACE = SpellRegistry.registerSpellComponent(ArsMagicaAPI.MODID, "transplace", SKILL_POINT_1, Transplace::new, ModSkillTrees.UTILITY, 185, 390, "arsmagicalegacy:random_teleport");
    public static final RegistryObject<SpellComponent> TRUE_SIGHT = SpellRegistry.registerSpellComponent(ArsMagicaAPI.MODID, "true_sight", SKILL_POINT_1, TrueSight::new, ModSkillTrees.UTILITY, 185, 210, "arsmagicalegacy:night_vision");
    public static final RegistryObject<SpellComponent> WATER_BREATHING = SpellRegistry.registerSpellComponent(ArsMagicaAPI.MODID, "water_breathing", SKILL_POINT_1, WaterBreathing::new, ModSkillTrees.UTILITY, 410, 345, "arsmagicalegacy:drought");
    public static final RegistryObject<SpellComponent> WATERY_GRAVE = SpellRegistry.registerSpellComponent(ArsMagicaAPI.MODID, "watery_grave", SKILL_POINT_2, WateryGrave::new, ModSkillTrees.OFFENSE, 435, 245, "arsmagicalegacy:drown");
    public static final RegistryObject<SpellComponent> WIZARDS_AUTUMN = SpellRegistry.registerSpellComponent(ArsMagicaAPI.MODID, "wizards_autumn", SKILL_POINT_1, WizardsAutumn::new, ModSkillTrees.UTILITY, 315, 120, "arsmagicalegacy:dig");
    //endregion

    public static void register() {
        EtheriumRegistry.registerEtherium(ArsMagicaAPI.MODID, "dark", 0xffffff);
        EtheriumRegistry.registerEtherium(ArsMagicaAPI.MODID, "light", 0xffffff);
        EtheriumRegistry.registerEtherium(ArsMagicaAPI.MODID, "neutral", 0xffffff);
    }
}
