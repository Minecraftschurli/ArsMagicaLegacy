package minecraftschurli.arsmagicalegacy.objects.spell.shape;

import java.util.EnumSet;
import minecraftschurli.arsmagicalegacy.api.spell.SpellCastResult;
import minecraftschurli.arsmagicalegacy.api.spell.SpellModifiers;
import minecraftschurli.arsmagicalegacy.api.spell.SpellShape;
import minecraftschurli.arsmagicalegacy.api.spell.crafting.EtheriumSpellIngredient;
import minecraftschurli.arsmagicalegacy.api.spell.crafting.ISpellIngredient;
import minecraftschurli.arsmagicalegacy.api.spell.crafting.ItemStackSpellIngredient;
import minecraftschurli.arsmagicalegacy.api.spell.crafting.ItemTagSpellIngredient;
import minecraftschurli.arsmagicalegacy.init.ModItems;
import minecraftschurli.arsmagicalegacy.init.ModTags;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.world.World;

public class Wave extends SpellShape {
    @Override
    public SpellCastResult beginStackStage(Item item, ItemStack stack, LivingEntity caster, LivingEntity target, World world, double x, double y, double z, Direction side, boolean giveXP, int useCount) {
//        if (world.isRemote) return SpellCastResult.SUCCESS;
//        EntitySpellEffect wave = new EntitySpellEffect(world);
//        wave.setRadius((float)SpellUtils.getModifiedDoubleAdd(1, stack, caster, target, world, SpellModifiers.RADIUS));
//        wave.setTicksToExist(SpellUtils.getModifiedIntMul(20, stack, caster, target, world, SpellModifiers.DURATION));
//        wave.SetCasterAndStack(caster, stack);
//        wave.setPosition(x, y + 1, z);
//        wave.setWave(caster.rotationYaw, (float)SpellUtils.getModifiedDoubleAdd(1f, stack, caster, target, world, SpellModifiers.SPEED) * 0.5f);
//        wave.noClip = SpellUtils.modifierIsPresent(SpellModifiers.PIERCING, stack);
//        wave.setGravity(SpellUtils.countModifiers(SpellModifiers.GRAVITY, stack) * 0.5f);
//        world.spawnEntityInWorld(wave);
        return SpellCastResult.SUCCESS;
    }

    @Override
    public EnumSet<SpellModifiers> getModifiers() {
        return EnumSet.of(SpellModifiers.RADIUS, SpellModifiers.GRAVITY, SpellModifiers.DURATION, SpellModifiers.COLOR, SpellModifiers.SPEED, SpellModifiers.PIERCING, SpellModifiers.TARGET_NONSOLID_BLOCKS);
    }

    @Override
    public ISpellIngredient[] getRecipe() {
        return new ISpellIngredient[]{
                new ItemTagSpellIngredient(ModTags.Items.DUSTS_VINTEUM),
                new ItemStackSpellIngredient(new ItemStack(ModItems.MAGIC_WALL.get())),
                new EtheriumSpellIngredient(2500)
        };
    }

    @Override
    public boolean isChanneled() {
        return false;
    }

    @Override
    public boolean isPrincipumShape() {
        return true;
    }

    @Override
    public boolean isTerminusShape() {
        return false;
    }

    @Override
    public float manaCostMultiplier(ItemStack spellStack) {
        return 3;
    }
}
