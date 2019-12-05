package minecraftschurli.arsmagicalegacy.spell.component;

import com.google.common.collect.*;
import minecraftschurli.arsmagicalegacy.api.affinity.*;
import minecraftschurli.arsmagicalegacy.api.spell.*;
import minecraftschurli.arsmagicalegacy.extensions.*;
import minecraftschurli.arsmagicalegacy.utils.*;
import net.minecraft.enchantment.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;

import java.util.*;

public class Dig extends SpellComponent {
    @Override
    public ISpellIngredient[] getRecipe() {
        return new Object[]{
                new ItemStack(Items.IRON_SHOVEL),
                new ItemStack(Items.IRON_AXE),
                new ItemStack(Items.IRON_PICKAXE)
        };
    }

    @Override
    public boolean applyEffectBlock(ItemStack stack, World world, BlockPos blockPos, Direction blockFace, double impactX, double impactY, double impactZ, LivingEntity caster) {
        if (!(caster instanceof PlayerEntity))
            return false;
        if (world.isRemote)
            return true;
        if (SpellUtils.modifierIsPresent(SpellModifiers.SILKTOUCH_LEVEL, stack)) {
            if (EnchantmentHelper.getEnchantmentLevel(Enchantments.SILK_TOUCH, stack) <= 0) {
                stack.addEnchantment(Enchantments.SILK_TOUCH, 1);
            }
        } else if (SpellUtils.modifierIsPresent(SpellModifiers.FORTUNE_LEVEL, stack)) {
            if (EnchantmentHelper.getEnchantmentLevel(Enchantments.FORTUNE, stack) <= 0) {
                stack.addEnchantment(Enchantments.FORTUNE, SpellUtils.countModifiers(SpellModifiers.FORTUNE_LEVEL, stack));
            }
        }
        BlockState state = world.getBlockState(blockPos);
        float hardness = state.getBlockHardness(world, blockPos);
        if (hardness != -1 && state.getBlock().getHarvestLevel(state) <= SpellUtils.getModifiedIntAdd(2, stack, caster, null, world, SpellModifiers.MINING_POWER)) {
            state.getBlock().harvestBlock(world, (PlayerEntity) caster, blockPos, state, null, stack);
            world.destroyBlock(blockPos, false);
            EntityExtension.For(caster).deductMana(hardness * 1.28f);
        }
        return true;
    }

    @Override
    public EnumSet<SpellModifiers> getModifiers() {
        return EnumSet.of(SpellModifiers.FORTUNE_LEVEL, SpellModifiers.MINING_POWER);
    }

    @Override
    public boolean applyEffectEntity(ItemStack stack, World world,
                                     LivingEntity caster, Entity target) {
        return false;
    }

    @Override
    public float getManaCost(LivingEntity caster) {
        return 10;
    }

    @Override
    public ItemStack[] reagents(LivingEntity caster) {
        return null;
    }

    @Override
    public void spawnParticles(World world, double x, double y, double z,
                               LivingEntity caster, Entity target, Random rand,
                               int colorModifier) {
    }

    @Override
    public Set<Affinity> getAffinity() {
        return Sets.newHashSet(Affinity.EARTH);
    }

    @Override
    public float getAffinityShift(Affinity affinity) {
        return 0.001F;
    }

    @Override
    public void encodeBasicData(CompoundNBT tag, Object[] recipe) {
    }
}