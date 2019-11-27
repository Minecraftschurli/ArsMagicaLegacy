package minecraftschurli.arsmagicalegacy.util;

import minecraftschurli.arsmagicalegacy.ArsMagicaLegacy;
import minecraftschurli.arsmagicalegacy.api.spellsystem.*;
import minecraftschurli.arsmagicalegacy.init.*;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Contains all spell parts, used for both registration<BR>
 * Skill are automatically created when doing any thing
 *
 * @author Minecraftschurli
 * @version 2019-11-27
 */
public class SpellRegistry {
    private static final List<AbstractSpellPart> SPELL_PARTS = new ArrayList<>();
    private static final List<Skill> SKILLS = new ArrayList<>();
    public static IForgeRegistry<AbstractSpellPart> SPELL_PART_REGISTRY = null;
    public static IForgeRegistry<Skill> SKILL_REGISTRY = null;

    @SubscribeEvent
    public static void registerRegistries(final RegistryEvent.NewRegistry event) {
        SPELL_PART_REGISTRY = new RegistryBuilder<AbstractSpellPart>().setName(new ResourceLocation(ArsMagicaLegacy.MODID, "spell_parts")).setType(AbstractSpellPart.class).create();
        SKILL_REGISTRY = new RegistryBuilder<Skill>().setName(new ResourceLocation(ArsMagicaLegacy.MODID, "skills")).setType(Skill.class).create();

        IInit.setEventBus(FMLJavaModLoadingContext.get().getModEventBus());

        ModBlocks.register();
        ModFluids.register();
        ModItems.register();
        ModEffects.register();
        ModBiomes.register();
        ModContainers.register();
        SpellParts.register();
    }

    /**
     * Register a spell component
     *
     * @param id : Name of this component
     * @param icon : Icon
     * @param tier : Skill Point required to unlock
     * @param part : Actual Component, use new {@link SpellComponent} ()
     * @param tree : Skill Tree
     * @param posX : Position in the tree
     * @param posY : Position in the tree
     * @param parents : Skills that need to be unlocked before this one (occulus only)
     */
    public static void registerSpellComponent(ResourceLocation id, ResourceLocation icon, SkillPoint tier, SpellComponent part, SkillTree tree, int posX, int posY, String... parents) {
        part.setRegistryName(id);
        SPELL_PARTS.add(part);
        Skill skill = new Skill(icon, tier, posX, posY, tree, parents);
        skill.setRegistryName(id);
        SKILLS.add(skill);
    }

    /**
     * Register a spell modifier
     *
     * @param id : Name of this modifier
     * @param icon : Icon
     * @param tier : Skill Point required to unlock
     * @param part : Actual Modifier, use new {@link SpellModifier} ()
     * @param tree : Skill Tree
     * @param posX : Position in the tree
     * @param posY : Position in the tree
     * @param parents : Skills that need to be unlocked before this one (occulus only)
     */
    public static void registerSpellModifier(ResourceLocation id, ResourceLocation icon, SkillPoint tier, SpellModifier part, SkillTree tree, int posX, int posY, String... parents) {
        part.setRegistryName(id);
        SPELL_PARTS.add(part);
        Skill skill = new Skill(icon, tier, posX, posY, tree, parents);
        skill.setRegistryName(id);
        SKILLS.add(skill);
    }

    /**
     * Register a spell shape
     *
     * @param id : Name of this shape
     * @param icon : Icon
     * @param tier : Skill Point required to unlock
     * @param part : Actual Shape, use new {@link SpellShape} ()
     * @param tree : Skill Tree
     * @param posX : Position in the tree
     * @param posY : Position in the tree
     * @param parents : Skills that need to be unlocked before this one (occulus only)
     */
    public static void registerSpellShape(ResourceLocation id, ResourceLocation icon, SkillPoint tier, SpellShape part, SkillTree tree, int posX, int posY, String... parents) {
        part.setRegistryName(id);
        SPELL_PARTS.add(part);
        Skill skill = new Skill(icon, tier, posX, posY, tree, parents);
        skill.setRegistryName(id);
        SKILLS.add(skill);
    }

    public static Skill getSkillFromPart(AbstractSpellPart part) {
        return SKILL_REGISTRY.getValue(part.getRegistryName());
    }

    public static AbstractSpellPart getPartByRecipe(ArrayList<ItemStack> currentAddedItems) {
        //TODO @minecraftschurli
        /*for (AbstractSpellPart data : SPELL_PART_REGISTRY.getValues()) {
            if (data != null && data.getRecipe() != null) {
                List<ItemStack> convRecipe = RecipeUtils.getConvRecipe(data);
                boolean match = currentAddedItems.size() == convRecipe.size();
                if (!match) continue;
                for (int i = 0; i < convRecipe.size(); i++) {
                    //match &= OreDictionary.itemMatches(convRecipe.get(i), currentAddedItems.get(i), false);
                    match &= convRecipe.get(i).hasTag() || (!currentAddedItems.get(i).hasTag() && NBTUtils.contains(convRecipe.get(i).getTag(), currentAddedItems.get(i).getTag()));
                    if (!match) break;
                }
                if (!match) ArsMagicaLegacy.LOGGER.debug("Part doesn't match {}", data.getRegistryName().toString());
                if (!match) continue;
                ArsMagicaLegacy.LOGGER.debug("Part matches : {}!", data.getRegistryName().toString());
                return data;
            }
        }*/
        return null;
    }

    public static SpellShape getShapeFromName(String shapeName) {
        AbstractSpellPart part = SPELL_PART_REGISTRY.getValue(new ResourceLocation(shapeName));
        return part instanceof SpellShape ? (SpellShape) part : null;
    }

    public static SpellModifier getModifierFromName(String shapeName) {
        AbstractSpellPart part = SPELL_PART_REGISTRY.getValue(new ResourceLocation(shapeName));
        return part instanceof SpellModifier ? (SpellModifier) part : null;
    }

    public static SpellComponent getComponentFromName(String shapeName) {
        AbstractSpellPart part = SPELL_PART_REGISTRY.getValue(new ResourceLocation(shapeName));
        return part instanceof SpellComponent ? (SpellComponent) part : null;
    }
}