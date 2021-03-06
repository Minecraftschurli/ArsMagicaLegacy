package minecraftschurli.arsmagicalegacy.objects.spell.component;

import com.google.common.collect.Sets;
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
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public final class Storm extends SpellComponent {
    @Override
    public boolean applyEffectBlock(ItemStack stack, World world, BlockPos blockPos, Direction blockFace, double impactX, double impactY, double impactZ, LivingEntity caster) {
        if (world.getRainStrength(1) > 0.9) {
            if (!world.isRemote) {
                int random = world.rand.nextInt(100);
                if (random < 20) {
                    int randPosX = (int) caster.getPosX() + world.rand.nextInt(100) - 50;
                    int randPosZ = (int) caster.getPosZ() + world.rand.nextInt(100) - 50;
                    int posY = (int) caster.getPosY();
                    while (!world.canBlockSeeSky(new BlockPos(randPosX, posY, randPosZ))) posY++;
                    while (world.getBlockState(new BlockPos(randPosX, posY - 1, randPosZ)).getBlock().equals(Blocks.AIR))
                        posY--;
                    LightningBoltEntity bolt = new LightningBoltEntity(world, randPosX, posY, randPosZ, false);
                    world.addEntity(bolt);
                } else if (random < 80) {
                    List<Entity> entities = world.getEntitiesWithinAABB(Entity.class, caster.getBoundingBox().expand(50, 10, 50));
                    if (entities.size() <= 0) return true;
                    Entity target = entities.get(world.rand.nextInt(entities.size()));
                    if (target != null && world.canBlockSeeSky(target.getPosition())) {
                        if (caster instanceof PlayerEntity)
                            target.attackEntityFrom(DamageSource.causePlayerDamage((PlayerEntity) caster), 1);
                        LightningBoltEntity bolt = new LightningBoltEntity(world, target.getPosX(), target.getPosY(), target.getPosZ(), false);
                        world.addEntity(bolt);
                    }
                }
            }
        } else if (!world.isRemote) world.getWorldInfo().setRaining(true);
        return true;
    }

    @Override
    public boolean applyEffectEntity(ItemStack stack, World world, LivingEntity caster, Entity target) {
        return SpellUtil.doBlockWithEntity(this, stack, world, caster, target);
    }

    @Override
    public Set<Affinity> getAffinity() {
        return Sets.newHashSet(ModAffinities.LIGHTNING.get(), ModAffinities.NATURE.get());
    }

    @Override
    public float getAffinityShift(Affinity affinity) {
        return 0.00001f;
    }

    @Override
    public float getManaCost(LivingEntity caster) {
        return 15;
    }

    @Override
    public EnumSet<SpellModifiers> getModifiers() {
        return EnumSet.noneOf(SpellModifiers.class);
    }

    @Override
    public void spawnParticles(ServerWorld world, double x, double y, double z, LivingEntity caster, Entity target, Random rand, int colorModifier) {
        ParticleUtil.addParticle(world, target, ModParticles.SYMBOL, colorModifier, 0, x, y, z);
    }
}
