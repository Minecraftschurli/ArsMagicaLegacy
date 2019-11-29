package minecraftschurli.arsmagicalegacy.objects.spell.modifier;

import minecraftschurli.arsmagicalegacy.api.spellsystem.*;
import minecraftschurli.arsmagicalegacy.init.*;
import net.minecraft.entity.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.world.*;

import java.util.*;

public class Solar extends SpellModifier {
    @Override
    public EnumSet<SpellModifiers> getAspectsModified() {
        return EnumSet.of(SpellModifiers.RANGE, SpellModifiers.RADIUS, SpellModifiers.DAMAGE, SpellModifiers.DURATION, SpellModifiers.HEALING);
    }

    @SuppressWarnings("incomplete-switch")
    @Override
    public float getModifier(SpellModifiers type, LivingEntity caster, Entity target, World world, CompoundNBT nbt) {
        switch (type) {
            case RANGE:
                return modifyValueOnInverseLunarCycle(world, 3f);
            case RADIUS:
                return modifyValueOnInverseLunarCycle(world, 3f);
            case DAMAGE:
                return modifyValueOnTime(world, 2.4f);
            case DURATION:
                return modifyValueOnTime(world, 5f);
            case HEALING:
                return modifyValueOnTime(world, 2f);
        }
        return 1.0f;
    }

    @Override
    public float getManaCostMultiplier(ItemStack spellStack, int stage, int quantity) {
        return 0;
    }

    @Override
    public ISpellIngredient[] getRecipe() {
        return new ISpellIngredient[]{
                new ItemStackSpellIngredient(new ItemStack(ModItems.SUNSTONE.get())),
                new ItemStackSpellIngredient(new ItemStack(ModItems.NATURE_ESSENCE.get())),
                new ItemStackSpellIngredient(new ItemStack(Items.CLOCK))
        };
    }

    @Override
    public void encodeBasicData(CompoundNBT tag, ISpellIngredient[] recipe) {

    }

    private float modifyValueOnTime(World world, float value){
        long x = world.getGameTime() % 24000;
        float multiplierFromTime = (float)(Math.cos(((x / 3800f) * (x / 24000f) - 13000f) * (180f / Math.PI)) * 1.5f) + 1;
        if (multiplierFromTime < 0)
            multiplierFromTime *= -0.5f;
        return value * multiplierFromTime;
    }

    private float modifyValueOnInverseLunarCycle(World world, float value){
        long boundedTime = world.getGameTime() % 24000;
        int phase = 8 - (8 - world.getMoonPhase());
        if (boundedTime > 23500 && boundedTime < 12500) return value + (phase / 2);
        return Math.abs(value - 1);
    }
}