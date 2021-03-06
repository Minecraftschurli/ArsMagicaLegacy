package minecraftschurli.arsmagicalegacy.init;

import java.util.Objects;
import javax.annotation.Nonnull;
import minecraftschurli.arsmagicalegacy.ArsMagicaLegacy;
import minecraftschurli.arsmagicalegacy.objects.armor.ArmorMaterial;
import minecraftschurli.arsmagicalegacy.objects.item.CrystalWrench;
import minecraftschurli.arsmagicalegacy.objects.item.InfinityOrb;
import minecraftschurli.arsmagicalegacy.objects.item.InscriptionTableUpgrade;
import minecraftschurli.arsmagicalegacy.objects.item.spawnegg.ManaCreeperSpawnEgg;
import minecraftschurli.arsmagicalegacy.objects.item.WizardChalk;
import minecraftschurli.arsmagicalegacy.objects.item.affinity.AffinityEssenceItem;
import minecraftschurli.arsmagicalegacy.objects.item.affinity.AffinityTomeItem;
import minecraftschurli.arsmagicalegacy.objects.item.spell.SpellItem;
import minecraftschurli.arsmagicalegacy.objects.item.spellbook.SpellBookItem;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.BlockItem;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.item.TallBlockItem;
import net.minecraft.item.WallOrFloorItem;
import net.minecraft.potion.EffectInstance;
import net.minecraftforge.fml.RegistryObject;

/**
 * @author Minecraftschurli
 * @version 2019-11-07
 */
@SuppressWarnings("unused")
public final class ModItems implements IInit {
    public static final Item.Properties ITEM_64 = new Item.Properties().group(ArsMagicaLegacy.ITEM_GROUP).maxStackSize(64);
    public static final Item.Properties ITEM_1 = new Item.Properties().group(ArsMagicaLegacy.ITEM_GROUP).maxStackSize(1);

    public static final RegistryObject<BlockItem> CHIMERITE_ORE = stackableBlockItem64(ModBlocks.CHIMERITE_ORE);
    public static final RegistryObject<BlockItem> CHIMERITE_BLOCK = stackableBlockItem64(ModBlocks.CHIMERITE_BLOCK);
    public static final RegistryObject<Item> CHIMERITE = stackableItem64("chimerite");
    public static final RegistryObject<BlockItem> TOPAZ_ORE = stackableBlockItem64(ModBlocks.TOPAZ_ORE);
    public static final RegistryObject<BlockItem> TOPAZ_BLOCK = stackableBlockItem64(ModBlocks.TOPAZ_BLOCK);
    public static final RegistryObject<Item> TOPAZ = stackableItem64("topaz");
    public static final RegistryObject<BlockItem> VINTEUM_ORE = stackableBlockItem64(ModBlocks.VINTEUM_ORE);
    public static final RegistryObject<BlockItem> VINTEUM_BLOCK = stackableBlockItem64(ModBlocks.VINTEUM_BLOCK);
    public static final RegistryObject<Item> VINTEUM = stackableItem64("vinteum");
    public static final RegistryObject<BlockItem> MOONSTONE_ORE = stackableBlockItem64(ModBlocks.MOONSTONE_ORE);
    public static final RegistryObject<BlockItem> MOONSTONE_BLOCK = stackableBlockItem64(ModBlocks.MOONSTONE_BLOCK);
    public static final RegistryObject<Item> MOONSTONE = stackableItem64("moonstone");
    public static final RegistryObject<BlockItem> SUNSTONE_ORE = stackableBlockItem64(ModBlocks.SUNSTONE_ORE);
    public static final RegistryObject<BlockItem> SUNSTONE_BLOCK = stackableBlockItem64(ModBlocks.SUNSTONE_BLOCK);
    public static final RegistryObject<Item> SUNSTONE = stackableItem64("sunstone");
    public static final RegistryObject<BlockItem> WITCHWOOD_LOG = stackableBlockItem64(ModBlocks.WITCHWOOD_LOG);
    public static final RegistryObject<BlockItem> WITCHWOOD_WOOD = stackableBlockItem64(ModBlocks.WITCHWOOD_WOOD);
    public static final RegistryObject<BlockItem> STRIPPED_WITCHWOOD_LOG = stackableBlockItem64(ModBlocks.STRIPPED_WITCHWOOD_LOG);
    public static final RegistryObject<BlockItem> STRIPPED_WITCHWOOD_WOOD = stackableBlockItem64(ModBlocks.STRIPPED_WITCHWOOD_WOOD);
    public static final RegistryObject<BlockItem> WITCHWOOD_PLANKS = stackableBlockItem64(ModBlocks.WITCHWOOD_PLANKS);
    public static final RegistryObject<BlockItem> WITCHWOOD_SLAB = stackableBlockItem64(ModBlocks.WITCHWOOD_SLAB);
    public static final RegistryObject<BlockItem> WITCHWOOD_STAIRS = stackableBlockItem64(ModBlocks.WITCHWOOD_STAIRS);
    public static final RegistryObject<BlockItem> WITCHWOOD_LEAVES = stackableBlockItem64(ModBlocks.WITCHWOOD_LEAVES);
    public static final RegistryObject<BlockItem> WITCHWOOD_FENCE = stackableBlockItem64(ModBlocks.WITCHWOOD_FENCE);
    public static final RegistryObject<BlockItem> WITCHWOOD_FENCE_GATE = stackableBlockItem64(ModBlocks.WITCHWOOD_FENCE_GATE);
    public static final RegistryObject<Item> WITCHWOOD_DOOR = ITEMS.register("witchwood_door", () -> new TallBlockItem(ModBlocks.WITCHWOOD_DOOR.get(), ITEM_64));
    public static final RegistryObject<BlockItem> WITCHWOOD_TRAPDOOR = stackableBlockItem64(ModBlocks.WITCHWOOD_TRAPDOOR);
    public static final RegistryObject<BlockItem> WITCHWOOD_BUTTON = stackableBlockItem64(ModBlocks.WITCHWOOD_BUTTON);
    public static final RegistryObject<BlockItem> WITCHWOOD_PRESSURE_PLATE = stackableBlockItem64(ModBlocks.WITCHWOOD_PRESSURE_PLATE);
    public static final RegistryObject<BlockItem> WITCHWOOD_SAPLING = stackableBlockItem64(ModBlocks.WITCHWOOD_SAPLING);
    public static final RegistryObject<BlockItem> AUM = stackableBlockItem64(ModBlocks.AUM);
    public static final RegistryObject<BlockItem> CERUBLOSSOM = stackableBlockItem64(ModBlocks.CERUBLOSSOM);
    public static final RegistryObject<BlockItem> DESERT_NOVA = stackableBlockItem64(ModBlocks.DESERT_NOVA);
    public static final RegistryObject<BlockItem> TARMA_ROOT = stackableBlockItem64(ModBlocks.TARMA_ROOT);
    public static final RegistryObject<BlockItem> WAKEBLOOM = stackableBlockItem64(ModBlocks.WAKEBLOOM);
    public static final RegistryObject<Item> RUNE = stackableItem64("rune");
    public static final RegistryObject<Item> WHITE_RUNE = stackableItem64("white_rune");
    public static final RegistryObject<Item> ORANGE_RUNE = stackableItem64("orange_rune");
    public static final RegistryObject<Item> MAGENTA_RUNE = stackableItem64("magenta_rune");
    public static final RegistryObject<Item> LIGHT_BLUE_RUNE = stackableItem64("light_blue_rune");
    public static final RegistryObject<Item> YELLOW_RUNE = stackableItem64("yellow_rune");
    public static final RegistryObject<Item> LIME_RUNE = stackableItem64("lime_rune");
    public static final RegistryObject<Item> PINK_RUNE = stackableItem64("pink_rune");
    public static final RegistryObject<Item> GRAY_RUNE = stackableItem64("gray_rune");
    public static final RegistryObject<Item> LIGHT_GRAY_RUNE = stackableItem64("light_gray_rune");
    public static final RegistryObject<Item> CYAN_RUNE = stackableItem64("cyan_rune");
    public static final RegistryObject<Item> PURPLE_RUNE = stackableItem64("purple_rune");
    public static final RegistryObject<Item> BLUE_RUNE = stackableItem64("blue_rune");
    public static final RegistryObject<Item> BROWN_RUNE = stackableItem64("brown_rune");
    public static final RegistryObject<Item> GREEN_RUNE = stackableItem64("green_rune");
    public static final RegistryObject<Item> RED_RUNE = stackableItem64("red_rune");
    public static final RegistryObject<Item> BLACK_RUNE = stackableItem64("black_rune");
    public static final RegistryObject<Item> LESSER_FOCUS = stackableItem64("lesser_focus");
    public static final RegistryObject<Item> STANDARD_FOCUS = stackableItem64("standard_focus");
    public static final RegistryObject<Item> GREATER_FOCUS = stackableItem64("greater_focus");
    public static final RegistryObject<Item> MANA_FOCUS = stackableItem64("mana_focus");
    public static final RegistryObject<Item> PLAYER_FOCUS = stackableItem64("player_focus");
    public static final RegistryObject<Item> CREATURE_FOCUS = stackableItem64("creature_focus");
    public static final RegistryObject<Item> MONSTER_FOCUS = stackableItem64("monster_focus");
    public static final RegistryObject<Item> ITEM_FOCUS = stackableItem64("item_focus");
    public static final RegistryObject<Item> CHARGE_FOCUS = stackableItem64("charge_focus");
    public static final RegistryObject<AffinityEssenceItem> AFFINITY_ESSENCE = ITEMS.register("affinity_essence", AffinityEssenceItem::new);
    public static final RegistryObject<Item> FLICKER_JAR = stackableItem64("flicker_jar");
    //    public static final RegistryObject<Item> WATER_FLICKER_JAR = stackableItem64("water_flicker_jar");
//    public static final RegistryObject<Item> FIRE_FLICKER_JAR = stackableItem64("fire_flicker_jar");
//    public static final RegistryObject<Item> EARTH_FLICKER_JAR = stackableItem64("earth_flicker_jar");
//    public static final RegistryObject<Item> AIR_FLICKER_JAR = stackableItem64("air_flicker_jar");
//    public static final RegistryObject<Item> LIGHTNING_FLICKER_JAR = stackableItem64("lightning_flicker_jar");
//    public static final RegistryObject<Item> ICE_FLICKER_JAR = stackableItem64("ice_flicker_jar");
//    public static final RegistryObject<Item> NATURE_FLICKER_JAR = stackableItem64("nature_flicker_jar");
//    public static final RegistryObject<Item> LIFE_FLICKER_JAR = stackableItem64("life_flicker_jar");
//    public static final RegistryObject<Item> ARCANE_FLICKER_JAR = stackableItem64("arcane_flicker_jar");
//    public static final RegistryObject<Item> ENDER_FLICKER_JAR = stackableItem64("ender_flicker_jar");
    public static final RegistryObject<AffinityTomeItem> AFFINITY_TOME = ITEMS.register("affinity_tome", AffinityTomeItem::new);
    public static final RegistryObject<Item> BUTCHER_FLICKER_FOCUS = stackableItem64("butcher_flicker_focus");
    public static final RegistryObject<Item> CONTAINMENT_FLICKER_FOCUS = stackableItem64("containment_flicker_focus");
    public static final RegistryObject<Item> FELLING_FLICKER_FOCUS = stackableItem64("felling_flicker_focus");
    public static final RegistryObject<Item> FISHING_FLICKER_FOCUS = stackableItem64("fishing_flicker_focus");
    public static final RegistryObject<Item> FLAT_LAND_FLICKER_FOCUS = stackableItem64("flat_land_flicker_focus");
    public static final RegistryObject<Item> GENTLE_RAIN_FLICKER_FOCUS = stackableItem64("gentle_rain_flicker_focus");
    public static final RegistryObject<Item> INTERDICTION_FLICKER_FOCUS = stackableItem64("interdiction_flicker_focus");
    public static final RegistryObject<Item> ITEM_TRANSPORT_FLICKER_FOCUS = stackableItem64("item_transport_flicker_focus");
    public static final RegistryObject<Item> LIGHT_FLICKER_FOCUS = stackableItem64("light_flicker_focus");
    public static final RegistryObject<Item> LUNAR_FLICKER_FOCUS = stackableItem64("lunar_flicker_focus");
    public static final RegistryObject<Item> NATURE_BOUNTY_FLICKER_FOCUS = stackableItem64("nature_bounty_flicker_focus");
    public static final RegistryObject<Item> PACKED_EARTH_FLICKER_FOCUS = stackableItem64("packed_earth_flicker_focus");
    public static final RegistryObject<Item> PROGENY_FLICKER_FOCUS = stackableItem64("progeny_flicker_focus");
    public static final RegistryObject<Item> MANA_CAKE = stackableFoodItem64("mana_cake", new Food.Builder().hunger(3).saturation(0.6f).effect(ModEffects.MANA_REGEN.lazyMap(effect -> new EffectInstance(effect, 600)), 1).build());
    public static final RegistryObject<Item> MANA_MARTINI = stackableFoodItem64("mana_martini", new Food.Builder().hunger(0).saturation(0).effect(ModEffects.BURNOUT_REDUCTION.lazyMap(effect -> new EffectInstance(effect, 300)), 1).build());
    public static final RegistryObject<BlockItem> REDSTONE_INLAY = stackableBlockItem64(ModBlocks.REDSTONE_INLAY);
    public static final RegistryObject<BlockItem> IRON_INLAY = stackableBlockItem64(ModBlocks.IRON_INLAY);
    public static final RegistryObject<BlockItem> GOLD_INLAY = stackableBlockItem64(ModBlocks.GOLD_INLAY);
    public static final RegistryObject<Item> VINTEUM_TORCH = ITEMS.register("vinteum_torch", () -> new WallOrFloorItem(ModBlocks.VINTEUM_TORCH.get(), ModBlocks.VINTEUM_WALL_TORCH.get(), ITEM_64));
    public static final ArmorMaterial MAGE_ARMOR = new ArmorMaterial("mage", 33, 2, 6, 4, 2, 15, 0.5f, RUNE);
    public static final ArmorMaterial BATTLEMAGE_ARMOR = new ArmorMaterial("battlemage", 48, 3, 8, 6, 3, 10, 1, GOLD_INLAY);
    public static final RegistryObject<Item> MAGE_HELMET = ITEMS.register("mage_helmet", () -> new ArmorItem(MAGE_ARMOR, EquipmentSlotType.HEAD, ITEM_1));
    public static final RegistryObject<Item> MAGE_CHESTPLATE = ITEMS.register("mage_chestplate", () -> new ArmorItem(MAGE_ARMOR, EquipmentSlotType.CHEST, ITEM_1));
    public static final RegistryObject<Item> MAGE_LEGGINGS = ITEMS.register("mage_leggings", () -> new ArmorItem(MAGE_ARMOR, EquipmentSlotType.LEGS, ITEM_1));
    public static final RegistryObject<Item> MAGE_BOOTS = ITEMS.register("mage_boots", () -> new ArmorItem(MAGE_ARMOR, EquipmentSlotType.FEET, ITEM_1));
    public static final RegistryObject<Item> BATTLEMAGE_HELMET = ITEMS.register("battlemage_helmet", () -> new ArmorItem(BATTLEMAGE_ARMOR, EquipmentSlotType.HEAD, ITEM_1));
    public static final RegistryObject<Item> BATTLEMAGE_CHESTPLATE = ITEMS.register("battlemage_chestplate", () -> new ArmorItem(BATTLEMAGE_ARMOR, EquipmentSlotType.CHEST, ITEM_1));
    public static final RegistryObject<Item> BATTLEMAGE_LEGGINGS = ITEMS.register("battlemage_leggings", () -> new ArmorItem(BATTLEMAGE_ARMOR, EquipmentSlotType.LEGS, ITEM_1));
    public static final RegistryObject<Item> BATTLEMAGE_BOOTS = ITEMS.register("battlemage_boots", () -> new ArmorItem(BATTLEMAGE_ARMOR, EquipmentSlotType.FEET, ITEM_1));
    public static final RegistryObject<Item> MANA_CREEPER_SPAWN_EGG = ITEMS.register("mana_creeper_spawn_egg", ManaCreeperSpawnEgg::new);
    public static final RegistryObject<InfinityOrb> INFINITY_ORB = ITEMS.register("infinity_orb", InfinityOrb::new);
    public static final RegistryObject<InscriptionTableUpgrade> INSCRIPTION_UPGRADE = ITEMS.register("inscription_upgrade", InscriptionTableUpgrade::new);
    public static final RegistryObject<Item> INSCRIPTION_TABLE = ITEMS.register("inscription_table", () -> new BlockItem(ModBlocks.INSCRIPTION_TABLE.get(), ITEM_1) {
        protected boolean placeBlock(@Nonnull BlockItemUseContext context, @Nonnull BlockState state) {
            context.getWorld().setBlockState(context.getPos().offset(context.getPlacementHorizontalFacing().rotateYCCW()), Blocks.AIR.getDefaultState(), 27);
            return super.placeBlock(context, state);
        }
    });
    public static final RegistryObject<Item> OBELISK = ITEMS.register("obelisk", () -> new TallBlockItem(ModBlocks.OBELISK.get(), ITEM_1));
    public static final RegistryObject<Item> CELESTIAL_PRISM = ITEMS.register("celestial_prism", () -> new TallBlockItem(ModBlocks.CELESTIAL_PRISM.get(), ITEM_1));
    public static final RegistryObject<BlockItem> BLACK_AUREM = blockItem(ModBlocks.BLACK_AUREM);
    public static final RegistryObject<BlockItem> ALTAR_CORE = stackableBlockItem64(ModBlocks.ALTAR_CORE);
    public static final RegistryObject<BlockItem> MAGIC_WALL = stackableBlockItem64(ModBlocks.MAGIC_WALL);
    public static final RegistryObject<BlockItem> OCCULUS = blockItem(ModBlocks.OCCULUS);
    public static final RegistryObject<BlockItem> MANA_BATTERY = stackableBlockItem64(ModBlocks.MANA_BATTERY);
    public static final RegistryObject<BlockItem> ESSENCE_REFINER = stackableBlockItem64(ModBlocks.ESSENCE_REFINER);
    public static final RegistryObject<BlockItem> ARMOR_IMBUEMENT_TABLE = stackableBlockItem64(ModBlocks.ARMOR_IMBUEMENT_TABLE);
    public static final RegistryObject<BlockItem> SLIPSTREAM_GENERATOR = stackableBlockItem64(ModBlocks.SLIPSTREAM_GENERATOR);
    public static final RegistryObject<BlockItem> DRAINING_WELL = stackableBlockItem64(ModBlocks.DRAINING_WELL);
    public static final RegistryObject<BlockItem> FLICKER_LURE = stackableBlockItem64(ModBlocks.FLICKER_LURE);
    public static final RegistryObject<BlockItem> EVERSTONE = stackableBlockItem64(ModBlocks.EVERSTONE);
    public static final RegistryObject<Item> KEYSTONE_DOOR = ITEMS.register("keystone_door", () -> new TallBlockItem(ModBlocks.KEYSTONE_DOOR.get(), ITEM_64));
    public static final RegistryObject<Item> SPELL_SEALED_DOOR = ITEMS.register("spell_sealed_door", () -> new TallBlockItem(ModBlocks.SPELL_SEALED_DOOR.get(), ITEM_64));
    public static final RegistryObject<BlockItem> KEYSTONE_TRAPDOOR = stackableBlockItem64(ModBlocks.KEYSTONE_TRAPDOOR);
    public static final RegistryObject<Item> KEYSTONE = stackableItem64("keystone");
    public static final RegistryObject<Item> ARCANE_ASH = stackableItem64("arcane_ash");
    public static final RegistryObject<Item> ARCANE_COMPOUND = stackableItem64("arcane_compound");
    public static final RegistryObject<Item> HELL_COW_HORN = stackableItem64("hell_cow_horn");
    public static final RegistryObject<Item> PIG_FAT = stackableItem64("pig_fat");
    public static final RegistryObject<Item> PURIFIED_VINTEUM = stackableItem64("purified_vinteum");
    public static final RegistryObject<Item> SPELL_PARCHMENT = stackableItem64("spell_parchment");
    public static final RegistryObject<Item> WOODEN_LEG = stackableItem64("wooden_leg");
    public static final RegistryObject<Item> EVIL_BOOK = stackableItem64("evil_book");
    public static final RegistryObject<Item> SPELL_BOOK = ITEMS.register("spell_book", SpellBookItem::new);
    public static final RegistryObject<Item> SPELL = ITEMS.register("spell", SpellItem::new);
    public static final RegistryObject<Item> BASE_ESSENCE_CORE = stackableItem64("base_essence_core");
    public static final RegistryObject<Item> HIGH_ESSENCE_CORE = stackableItem64("high_essence_core");
    public static final RegistryObject<Item> PURE_ESSENCE_CORE = stackableItem64("pure_essence_core");
    public static final RegistryObject<Item> DEFICIT_CRYSTAL = stackableItem64("deficit_crystal");
    public static final RegistryObject<Item> RUNE_BAG = ITEMS.register("rune_bag", () -> new Item(ITEM_1));
    public static final RegistryObject<Item> ESSENCE_BAG = ITEMS.register("essence_bag", () -> new Item(ITEM_1));
    public static final RegistryObject<Item> JOURNAL = ITEMS.register("journal", () -> new Item(ITEM_1));
    public static final RegistryObject<Item> MAGITECH_GOGGLES = ITEMS.register("magitech_goggles", () -> new ArmorItem(new ArmorMaterial("magitech_goggles", 15, 1, 3, 2, 1, 15, 0, CHIMERITE), EquipmentSlotType.HEAD, ITEM_1));
    public static final RegistryObject<Item> MAGITECH_STAFF = ITEMS.register("magitech_staff", () -> new Item(ITEM_1));
    public static final RegistryObject<Item> CRYSTAL_WRENCH = ITEMS.register("crystal_wrench", CrystalWrench::new);
    public static final RegistryObject<Item> WIZARD_CHALK = ITEMS.register("wizard_chalk", WizardChalk::new);
    public static final RegistryObject<Item> CRYSTAL_PHYLACTERY = ITEMS.register("crystal_phylactery", () -> new Item(ITEM_1));

    public static void register() {
    }

    private static <T extends Block> RegistryObject<BlockItem> stackableBlockItem64(final RegistryObject<T> block) {
        Objects.requireNonNull(block);
        return ITEMS.register(block.getId().getPath(), block.lazyMap(b -> new BlockItem(b, ITEM_64)));
    }

    private static <T extends Block> RegistryObject<BlockItem> blockItem(final RegistryObject<T> block) {
        Objects.requireNonNull(block);
        return ITEMS.register(block.getId().getPath(), block.lazyMap(b -> new BlockItem(b, ITEM_1)));
    }

    private static RegistryObject<Item> stackableItem64(final String name) {
        Objects.requireNonNull(name);
        return ITEMS.register(name, ModItems::item64);
    }

    private static RegistryObject<Item> stackableFoodItem64(final String name, Food food) {
        Objects.requireNonNull(name);
        Objects.requireNonNull(food);
        return ITEMS.register(name, () -> new Item(new Item.Properties().group(ArsMagicaLegacy.ITEM_GROUP).maxStackSize(64).food(food)));
    }

    private static Item item64() {
        return new Item(ITEM_64);
    }
}
