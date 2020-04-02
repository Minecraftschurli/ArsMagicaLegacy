package minecraftschurli.arsmagicalegacy.objects.spell.modifier;

import minecraftschurli.arsmagicalegacy.api.spell.SpellModifier;
import minecraftschurli.arsmagicalegacy.api.spell.SpellModifiers;
import minecraftschurli.arsmagicalegacy.api.spell.crafting.ISpellIngredient;
import minecraftschurli.arsmagicalegacy.api.spell.crafting.ItemStackSpellIngredient;
import minecraftschurli.arsmagicalegacy.api.spell.crafting.ItemTagSpellIngredient;
import minecraftschurli.arsmagicalegacy.init.ModItems;
import minecraftschurli.arsmagicalegacy.init.ModTags;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.World;

import java.util.EnumSet;

public final class Lunar extends SpellModifier {
    @Override
    public EnumSet<SpellModifiers> getAspectsModified() {
        return EnumSet.of(SpellModifiers.RANGE, SpellModifiers.RADIUS, SpellModifiers.DAMAGE, SpellModifiers.DURATION, SpellModifiers.HEALING);
    }

    @Override
    public float getManaCostMultiplier(ItemStack spellStack, int stage, int quantity) {
        return 0;
    }

    @Override
    public float getModifier(SpellModifiers type, LivingEntity caster, Entity target, World world, CompoundNBT nbt) {
        switch (type) {
            case DAMAGE:
                return modifyValueOnTime(world, 2.4f);
            case DURATION:
                return modifyValueOnTime(world, 5);
            case HEALING:
                return modifyValueOnTime(world, 2);
            case RADIUS:
            case RANGE:
                if (world.getGameTime() % 24000 > 12500 && world.getGameTime() % 24000 < 23500) return 3 + ((8 - world.getMoonPhase()) / 2f);
                return 2;
        }
        return 1;
    }

    @Override
    public ISpellIngredient[] getRecipe() {
        return new ISpellIngredient[]{
                new ItemTagSpellIngredient(ModTags.Items.GEMS_MOONSTONE),
                new ItemStackSpellIngredient(new ItemStack(Items.CLOCK)),
                new ItemStackSpellIngredient(new ItemStack(ModItems.NATURE_ESSENCE.get()))
        };
    }

    private float modifyValueOnTime(World world, float value) {
        long x = world.getGameTime() % 24000;
        float multiplierFromTime = (float) (Math.sin(((x / 4600f) * (x / 21000f) - 900) * (180 / Math.PI)) * 3) + 1;
        if (multiplierFromTime < 0) multiplierFromTime *= -0.5f;
        return value * multiplierFromTime;
    }
}
