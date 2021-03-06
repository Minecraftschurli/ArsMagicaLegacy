package minecraftschurli.arsmagicalegacy.api.registry;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Supplier;
import minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import minecraftschurli.arsmagicalegacy.api.skill.Skill;
import minecraftschurli.arsmagicalegacy.api.skill.SkillPoint;
import minecraftschurli.arsmagicalegacy.api.skill.SkillTree;
import minecraftschurli.arsmagicalegacy.api.spell.AbstractSpellPart;
import minecraftschurli.arsmagicalegacy.api.spell.SpellComponent;
import minecraftschurli.arsmagicalegacy.api.spell.SpellModifier;
import minecraftschurli.arsmagicalegacy.api.spell.SpellShape;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.IForgeRegistry;

/**
 * Contains all spell parts, used for both registration<BR>
 * Skill are automatically created when doing any thing
 *
 * @author Minecraftschurli
 * @version 2019-11-27
 */
public class SpellRegistry {
    public static final ResourceLocation MISSING_SHAPE = new ResourceLocation(ArsMagicaAPI.MODID, "null");

    private static final Map<RegistryObject<AbstractSpellPart>, Supplier<? extends AbstractSpellPart>> parts = new LinkedHashMap<>();

    static void onSpellPartRegister(RegistryEvent.Register<AbstractSpellPart> event) {
        if (event.getGenericType() != AbstractSpellPart.class) return;
        IForgeRegistry<AbstractSpellPart> reg = event.getRegistry();
        for (Map.Entry<RegistryObject<AbstractSpellPart>, Supplier<? extends AbstractSpellPart>> e : parts.entrySet()) {
            reg.register(e.getValue().get());
            e.getKey().updateReference(reg);
        }
    }

    /**
     * Register a spell component
     *
     * @param id      : Name of this component
     * @param tier    : Skill Point required to unlock
     * @param part    : the actual Component
     * @param tree    : Skill Tree
     * @param posX    : Position in the tree
     * @param posY    : Position in the tree
     * @param parents : Skills that need to be unlocked before this one (occulus only)
     */
    public static <T extends AbstractSpellPart> RegistryObject<T> registerSpellComponent(ResourceLocation id, Supplier<SkillPoint> tier, Supplier<SpellComponent> part, Supplier<SkillTree> tree, int posX, int posY, String... parents) {
        RegistryObject<T> ret = RegistryObject.of(id, RegistryHandler.getSpellPartRegistry());
        if (parts.putIfAbsent((RegistryObject<AbstractSpellPart>) ret, () -> part.get().setRegistryName(id)) != null) throw new IllegalArgumentException("Duplicate registration " + id);
        SkillRegistry.registerSkill(id, getComponentIcon(id), tier, tree, posX, posY, parents);
        return ret;
    }

    /**
     * Register a spell component
     *
     * @param name    : Name of this component
     * @param tier    : Skill Point required to unlock
     * @param part    : the actual Component
     * @param tree    : Skill Tree
     * @param posX    : Position in the tree
     * @param posY    : Position in the tree
     * @param parents : Skills that need to be unlocked before this one (occulus only)
     */
    public static RegistryObject<SpellComponent> registerSpellComponent(String modid, String name, Supplier<SkillPoint> tier, Supplier<SpellComponent> part, Supplier<SkillTree> tree, int posX, int posY, String... parents) {
        return registerSpellComponent(new ResourceLocation(modid, name), tier, part, tree, posX, posY, parents);
    }

    /**
     * Register a spell component
     *
     * @param name    : Name of this component
     * @param tier    : Skill Point required to unlock
     * @param part    : the actual Component
     * @param tree    : Skill Tree
     * @param posX    : Position in the tree
     * @param posY    : Position in the tree
     * @param parents : Skills that need to be unlocked before this one (occulus only)
     */
    public static RegistryObject<SpellComponent> registerSpellComponent(String name, Supplier<SkillPoint> tier, Supplier<SpellComponent> part, Supplier<SkillTree> tree, int posX, int posY, String... parents) {
        return registerSpellComponent(ModLoadingContext.get().getActiveNamespace(), name, tier, part, tree, posX, posY, parents);
    }

    /**
     * Register a spell modifier
     *
     * @param id      : Name of this modifier
     * @param tier    : Skill Point required to unlock
     * @param part    : Actual Modifier, use new {@link SpellModifier}()
     * @param tree    : Skill Tree
     * @param posX    : Position in the tree
     * @param posY    : Position in the tree
     * @param parents : Skills that need to be unlocked before this one (occulus only)
     */
    public static <T extends AbstractSpellPart> RegistryObject<T> registerSpellModifier(ResourceLocation id, Supplier<SkillPoint> tier, Supplier<SpellModifier> part, Supplier<SkillTree> tree, int posX, int posY, String... parents) {
        RegistryObject<T> ret = RegistryObject.of(id, RegistryHandler.getSpellPartRegistry());
        if (parts.putIfAbsent((RegistryObject<AbstractSpellPart>) ret, () -> part.get().setRegistryName(id)) != null) throw new IllegalArgumentException("Duplicate registration " + id);
        SkillRegistry.registerSkill(id, getModifierIcon(id), tier, tree, posX, posY, parents);
        return RegistryObject.of(id, RegistryHandler.getSpellPartRegistry());
    }

    /**
     * Register a spell modifier
     * @param name    : Name of this modifier
     * @param tier    : Skill Point required to unlock
     * @param part    : the actual Component
     * @param tree    : Skill Tree
     * @param posX    : Position in the tree
     * @param posY    : Position in the tree
     * @param parents : Skills that need to be unlocked before this one (occulus only)
     */
    public static RegistryObject<SpellModifier> registerSpellModifier(String modid, String name, Supplier<SkillPoint> tier, Supplier<SpellModifier> part, Supplier<SkillTree> tree, int posX, int posY, String... parents) {
        return registerSpellModifier(new ResourceLocation(modid, name), tier, part, tree, posX, posY, parents);
    }

    /**
     * Register a spell modifier
     *
     * @param name    : Name of this modifier
     * @param tier    : Skill Point required to unlock
     * @param part    : the actual Component
     * @param tree    : Skill Tree
     * @param posX    : Position in the tree
     * @param posY    : Position in the tree
     * @param parents : Skills that need to be unlocked before this one (occulus only)
     */
    public static RegistryObject<SpellModifier> registerSpellModifier(String name, Supplier<SkillPoint> tier, Supplier<SpellModifier> part, Supplier<SkillTree> tree, int posX, int posY, String... parents) {
        return registerSpellModifier(ModLoadingContext.get().getActiveNamespace(), name, tier, part, tree, posX, posY, parents);
    }

    /**
     * Register a spell shape
     *
     * @param id      : Name of this shape
     * @param tier    : Skill Point required to unlock
     * @param part    : the actual Component
     * @param tree    : Skill Tree
     * @param posX    : Position in the tree
     * @param posY    : Position in the tree
     * @param parents : Skills that need to be unlocked before this one (occulus only)
     */
    public static <T extends AbstractSpellPart> RegistryObject<T> registerSpellShape(ResourceLocation id, Supplier<SkillPoint> tier, Supplier<SpellShape> part, Supplier<SkillTree> tree, int posX, int posY, String... parents) {
        RegistryObject<T> ret = RegistryObject.of(id, RegistryHandler.getSpellPartRegistry());
        if (parts.putIfAbsent((RegistryObject<AbstractSpellPart>) ret, () -> part.get().setRegistryName(id)) != null) throw new IllegalArgumentException("Duplicate registration " + id);
        SkillRegistry.registerSkill(id, getShapeIcon(id), tier, tree, posX, posY, parents);
        return RegistryObject.of(id, RegistryHandler.getSpellPartRegistry());
    }

    /**
     * Register a spell shape
     *
     * @param name    : Name of this shape
     * @param tier    : Skill Point required to unlock
     * @param part    : the actual Component
     * @param tree    : Skill Tree
     * @param posX    : Position in the tree
     * @param posY    : Position in the tree
     * @param parents : Skills that need to be unlocked before this one (occulus only)
     */
    public static RegistryObject<SpellShape> registerSpellShape(String modid, String name, Supplier<SkillPoint> tier, Supplier<SpellShape> part, Supplier<SkillTree> tree, int posX, int posY, String... parents) {
        return registerSpellShape(new ResourceLocation(modid, name), tier, part, tree, posX, posY, parents);
    }

    /**
     * Register a spell shape
     *
     * @param name    : Name of this shape
     * @param tier    : Skill Point required to unlock
     * @param part    : the actual Component
     * @param tree    : Skill Tree
     * @param posX    : Position in the tree
     * @param posY    : Position in the tree
     * @param parents : Skills that need to be unlocked before this one (occulus only)
     */
    public static RegistryObject<SpellShape> registerSpellShape(String name, Supplier<SkillPoint> tier, Supplier<SpellShape> part, Supplier<SkillTree> tree, int posX, int posY, String... parents) {
        return registerSpellShape(ModLoadingContext.get().getActiveNamespace(), name, tier, part, tree, posX, posY, parents);
    }

    private static ResourceLocation getComponentIcon(ResourceLocation id) {
        return new ResourceLocation(id.getNamespace(), "textures/icon/spell/component/" + id.getPath() + ".png");
    }

    private static ResourceLocation getModifierIcon(ResourceLocation id) {
        return new ResourceLocation(id.getNamespace(), "textures/icon/spell/modifier/" + id.getPath() + ".png");
    }

    private static ResourceLocation getShapeIcon(ResourceLocation id) {
        if (id.getPath().equals("null")) return null;
        return new ResourceLocation(id.getNamespace(), "textures/icon/spell/shape/" + id.getPath() + ".png");
    }

    /**
     * Gets the Skill associated with this Part
     *
     * @param part : the Part to get the Skill for
     * @return the associated Skill for the given Part
     */
    public static Skill getSkillFromPart(AbstractSpellPart part) {
        return RegistryHandler.getSkillRegistry().getValue(part.getRegistryName());
    }

    //TODO @minecraftschurli
//    public static AbstractSpellPart getPartByRecipe(List<ItemStack> currentAddedItems) {
//        for (AbstractSpellPart data : SPELL_PART_REGISTRY.getValues()) {
//            if (data != null && data.getRecipe() != null) {
//                List<ItemStack> convRecipe = RecipeUtils.getConvRecipe(data);
//                boolean match = currentAddedItems.size() == convRecipe.size();
//                if (!match) continue;
//                for (int i = 0; i < convRecipe.size(); i++) {
//                    match &= convRecipe.get(i).hasTag() || (!currentAddedItems.get(i).hasTag() && NBTUtils.contains(convRecipe.get(i).getTag(), currentAddedItems.get(i).getTag()));
//                    if (!match) break;
//                }
//                if (!match) ArsMagicaLegacy.LOGGER.debug("Part doesn't match {}", data.getRegistryName().toString());
//                if (!match) continue;
//                ArsMagicaLegacy.LOGGER.debug("Part matches : {}!", data.getRegistryName().toString());
//                return data;
//            }
//        }
//        return null;
//    }

    /**
     * Gets the {@link SpellShape} for the given registry name
     *
     * @param name : The String representation of a {@link ResourceLocation}
     * @return null if the spell shape doesn't exist
     */
    public static SpellShape getShapeFromName(String name) {
        ResourceLocation rl = ResourceLocation.tryCreate(name);
        AbstractSpellPart part = RegistryHandler.getSpellPartRegistry().getValue(rl == null ? MISSING_SHAPE : rl);
        return part instanceof SpellShape ? (SpellShape) part : null;
    }

    /**
     * Gets the {@link SpellModifier} for the given registry name
     *
     * @param name : The String representation of a {@link ResourceLocation}
     * @return null if the spell modifier doesn't exist
     */
    public static SpellModifier getModifierFromName(String name) {
        ResourceLocation rl = ResourceLocation.tryCreate(name);
        AbstractSpellPart part = RegistryHandler.getSpellPartRegistry().getValue(rl == null ? MISSING_SHAPE : rl);
        return part instanceof SpellModifier ? (SpellModifier) part : null;
    }

    /**
     * Gets the {@link SpellComponent} for the given registry name
     *
     * @param name : The String representation of a {@link ResourceLocation}
     * @return null if the spell component doesn't exist
     */
    public static SpellComponent getComponentFromName(String name) {
        ResourceLocation rl = ResourceLocation.tryCreate(name);
        AbstractSpellPart part = RegistryHandler.getSpellPartRegistry().getValue(rl == null ? MISSING_SHAPE : rl);
        return part instanceof SpellComponent ? (SpellComponent) part : null;
    }
}
