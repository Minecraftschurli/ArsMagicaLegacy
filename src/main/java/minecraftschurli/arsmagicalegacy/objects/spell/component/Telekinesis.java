package minecraftschurli.arsmagicalegacy.objects.spell.component;

import com.google.common.collect.Sets;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import minecraftschurli.arsmagicalegacy.api.affinity.Affinity;
import minecraftschurli.arsmagicalegacy.api.spell.SpellComponent;
import minecraftschurli.arsmagicalegacy.api.spell.SpellModifiers;
import minecraftschurli.arsmagicalegacy.init.ModAffinities;
import minecraftschurli.arsmagicalegacy.init.ModParticles;
import minecraftschurli.arsmagicalegacy.util.ParticleUtil;
import minecraftschurli.arsmagicalegacy.util.SpellUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ExperienceOrbEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public final class Telekinesis extends SpellComponent {
    @Override
    public boolean applyEffectBlock(ItemStack stack, World world, BlockPos blockPos, Direction blockFace, double impactX, double impactY, double impactZ, LivingEntity caster) {
        List<Entity> entities = new ArrayList<>();
        entities.addAll(world.getEntitiesWithinAABB(ItemEntity.class, new AxisAlignedBB(impactX - 16, impactY - 3, impactZ - 16, impactX + 16, impactY + 3, impactZ + 16)));
        entities.addAll(world.getEntitiesWithinAABB(ExperienceOrbEntity.class, new AxisAlignedBB(impactX - 16, impactY - 3, impactZ - 16, impactX + 16, impactY + 3, impactZ + 16)));
        for (Entity e : entities) {
            if (e.ticksExisted < 20) continue;
            Vec3d movement = new Vec3d(e.getPosX(), e.getPosY(), e.getPosZ()).subtract(new Vec3d(impactX, impactY, impactZ)).normalize();
            if (!world.isRemote) {
                float factor = 0.15f;
                if (movement.getY() > 0) movement = new Vec3d(movement.getX(), 0, movement.getZ());
                double x = -(movement.getX() * factor);
                double y = -(movement.getY() * factor);
                double z = -(movement.getZ() * factor);
                e.addVelocity(x, y, z);
                if (Math.abs(e.getMotion().getX()) > Math.abs(x * 2))
                    e.setMotion(x, e.getMotion().getY(), e.getMotion().getZ());
                if (Math.abs(e.getMotion().getY()) > Math.abs(y * 2))
                    e.setMotion(e.getMotion().getX(), y, e.getMotion().getZ());
                if (Math.abs(e.getMotion().getZ()) > Math.abs(z * 2))
                    e.setMotion(e.getMotion().getX(), e.getMotion().getY(), z);
            }
        }
        return true;
    }

    @Override
    public boolean applyEffectEntity(ItemStack stack, World world, LivingEntity caster, Entity target) {
        return SpellUtil.doBlockWithEntity(this, stack, world, caster, target);
    }

    @Override
    public Set<Affinity> getAffinity() {
        return Sets.newHashSet(ModAffinities.ARCANE.get());
    }

    @Override
    public float getAffinityShift(Affinity affinity) {
        return 0.001f;
    }

    @Override
    public float getManaCost(LivingEntity caster) {
        return 6;
    }

    @Override
    public EnumSet<SpellModifiers> getModifiers() {
        return EnumSet.noneOf(SpellModifiers.class);
    }

    @Override
    public void spawnParticles(ServerWorld world, double x, double y, double z, LivingEntity caster, Entity target, Random rand, int colorModifier) {
        ParticleUtil.addParticle(world, target, ModParticles.ARCANE, colorModifier, 0xcb4bb2, x, y, z);
    }
}
