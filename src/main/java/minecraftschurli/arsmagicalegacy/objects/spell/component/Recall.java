package minecraftschurli.arsmagicalegacy.objects.spell.component;

import com.google.common.collect.Sets;
import java.util.EnumSet;
import java.util.Random;
import java.util.Set;
import minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import minecraftschurli.arsmagicalegacy.api.affinity.Affinity;
import minecraftschurli.arsmagicalegacy.api.spell.SpellComponent;
import minecraftschurli.arsmagicalegacy.api.spell.SpellModifiers;
import minecraftschurli.arsmagicalegacy.init.ModAffinities;
import minecraftschurli.arsmagicalegacy.init.ModEffects;
import minecraftschurli.arsmagicalegacy.init.ModParticles;
import minecraftschurli.arsmagicalegacy.util.ParticleUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public final class Recall extends SpellComponent {
    @Override
    public boolean applyEffectBlock(ItemStack stack, World world, BlockPos pos, Direction blockFace, double impactX, double impactY, double impactZ, LivingEntity caster) {
        return false;
    }

    @Override
    public boolean applyEffectEntity(ItemStack stack, World world, LivingEntity caster, Entity target) {
        if (!(target instanceof LivingEntity)) return false;
        if (caster.isPotionActive(ModEffects.ASTRAL_DISTORTION.get()) || ((LivingEntity) target).isPotionActive(ModEffects.ASTRAL_DISTORTION.get()))
            if (caster instanceof PlayerEntity) {
                caster.sendMessage(new TranslationTextComponent(ArsMagicaAPI.MODID + ".chat.noRecall"));
                return false;
            }
//        if (RitualShapeHelper.instance.matchesRitual(this, world, target.getPosition())) {
//            ItemStack[] ritualRunes = RitualShapeHelper.instance.checkForRitual(this, world, target.getPosition());
//            if (ritualRunes != null) return handleRitualReagents(ritualRunes, world, target.getPosition(), caster, target);
//        }
        if (caster.dimension.getId() == -512) {
            if (caster instanceof PlayerEntity && !world.isRemote)
                caster.sendMessage(new TranslationTextComponent(ArsMagicaAPI.MODID + ".chat.noRecall"));
            return false;
        }
        if (!world.isRemote) target.setPositionAndUpdate(caster.getPosX(), caster.getPosY(), caster.getPosZ());
        return true;
    }

    @Override
    public Set<Affinity> getAffinity() {
        return Sets.newHashSet(ModAffinities.ARCANE.get());
    }

    @Override
    public float getAffinityShift(Affinity affinity) {
        return 0.1f;
    }

    @Override
    public float getManaCost(LivingEntity caster) {
        return 500;
    }

    @Override
    public EnumSet<SpellModifiers> getModifiers() {
        return EnumSet.noneOf(SpellModifiers.class);
    }

    /*TODO move to ritual
    @Override
    public Set<ItemStack> getReagents(LivingEntity caster) {
        return new ItemStack[]{
                new ItemStack(ModItems.RUNE.get(), 3)
        };
    }*/

    //    @Override
//    public float getReagentSearchRadius() {
//        return RitualShapeHelper.instance.ringedCross.getWidth();
//    }
//
//    @Override
//    public MultiblockStructureDefinition getRitualShape() {
//        return RitualShapeHelper.instance.ringedCross;
//    }
//
    @Override
    public void spawnParticles(ServerWorld world, double x, double y, double z, LivingEntity caster, Entity target, Random rand, int colorModifier) {
        for (int i = 0; i < 25; i++)
            ParticleUtil.addParticle(world, target, ModParticles.ARCANE, colorModifier, 0, x + rand.nextInt(2) - 1, y, z + rand.nextInt(2) - 1);
    }
}
