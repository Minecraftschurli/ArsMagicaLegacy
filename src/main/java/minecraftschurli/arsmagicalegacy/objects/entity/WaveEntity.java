package minecraftschurli.arsmagicalegacy.objects.entity;

import java.util.ArrayList;
import javax.annotation.Nonnull;
import minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import minecraftschurli.arsmagicalegacy.init.ModEntities;
import minecraftschurli.arsmagicalegacy.util.SpellUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.boss.dragon.EnderDragonPartEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.network.play.server.SSpawnObjectPacket;
import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public final class WaveEntity extends Entity {
    private static final DataParameter<Integer> EFFECT = EntityDataManager.createKey(WaveEntity.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> OWNER = EntityDataManager.createKey(WaveEntity.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> TICKS = EntityDataManager.createKey(WaveEntity.class, DataSerializers.VARINT);
    private static final DataParameter<Float> DAMAGE = EntityDataManager.createKey(WaveEntity.class, DataSerializers.FLOAT);
    private static final DataParameter<Float> GRAVITY = EntityDataManager.createKey(WaveEntity.class, DataSerializers.FLOAT);
    private static final DataParameter<Float> RADIUS = EntityDataManager.createKey(WaveEntity.class, DataSerializers.FLOAT);
    private static final DataParameter<Float> SPEED = EntityDataManager.createKey(WaveEntity.class, DataSerializers.FLOAT);
    private static final DataParameter<ItemStack> STACK = EntityDataManager.createKey(WaveEntity.class, DataSerializers.ITEMSTACK);

    public WaveEntity(World world) {
        this(ModEntities.WAVE.get(), world);
    }

    public WaveEntity(EntityType<?> type, World world) {
        super(type, world);
        setBoundingBox(new AxisAlignedBB(getPosX() - 0.25, getPosY() - 0.25, getPosZ() - 0.25, getPosX() + 0.25, getPosY() + 0.25, getPosZ() + 0.25));
    }

    @Override
    protected void readAdditional(CompoundNBT nbt) {
        CompoundNBT tag = nbt.getCompound(ArsMagicaAPI.MODID);
        dataManager.set(EFFECT, tag.getInt("Effect"));
        dataManager.set(OWNER, tag.getInt("Owner"));
        dataManager.set(TICKS, tag.getInt("Ticks"));
        dataManager.set(DAMAGE, tag.getFloat("Damage"));
        dataManager.set(GRAVITY, tag.getFloat("Gravity"));
        dataManager.set(RADIUS, tag.getFloat("Radius"));
        dataManager.set(SPEED, tag.getFloat("Speed"));
        dataManager.set(STACK, ItemStack.read(tag.getCompound("Stack")));
    }

    @Override
    protected void writeAdditional(CompoundNBT nbt) {
        CompoundNBT tag = nbt.getCompound(ArsMagicaAPI.MODID);
        tag.putInt("Effect", dataManager.get(EFFECT));
        tag.putInt("Owner", dataManager.get(OWNER));
        tag.putInt("Ticks", dataManager.get(TICKS));
        tag.putFloat("Damage", dataManager.get(DAMAGE));
        tag.putFloat("Gravity", dataManager.get(GRAVITY));
        tag.putFloat("Radius", dataManager.get(RADIUS));
        tag.putFloat("Speed", dataManager.get(SPEED));
        CompoundNBT tmp = new CompoundNBT();
        dataManager.get(STACK).write(tmp);
        tag.put("Stack", tmp);
    }

    @Nonnull
    @Override
    public IPacket<?> createSpawnPacket() {
        return new SSpawnObjectPacket(this, getOwner() == null ? 0 : getOwner().getEntityId());
    }

    @Override
    protected void registerData() {
        dataManager.register(EFFECT, 20);
        dataManager.register(OWNER, 0);
        dataManager.register(TICKS, 100);
        dataManager.register(DAMAGE, 1f);
        dataManager.register(GRAVITY, 0f);
        dataManager.register(RADIUS, 3f);
        dataManager.register(SPEED, 0f);
        dataManager.register(STACK, ItemStack.EMPTY);
    }

    @Override
    public void tick() {
        if (world.isRemote) {
//            int color = 0xffffff;
//            if (SpellUtil.hasModifier(SpellModifiers.COLOR, dataManager.get(STACK)))
//                for (SpellModifier mod : SpellUtil.getModifiers(dataManager.get(STACK), -1))
//                    if (mod instanceof Color)
//                        color = (int) mod.getModifier(SpellModifiers.COLOR, null, null, null, dataManager.get(STACK).getTag());
//            for (float i = 0; i < dataManager.get(RADIUS); i += 0.5f) {
//                RenderUtil.addParticle(world, null, AffinityHelper.getParticleForAffinity(AffinityHelper.getAffinityForSpell(dataManager.get(STACK))), color, color, getPosX() - Math.cos(3.14159265358979 / 180 * (rotationYaw)) * i + rand.nextInt(2) - 1, getPosY() + rand.nextInt(2) - 1, getPosZ() - Math.sin(3.14159265358979 / 180 * (rotationYaw)) * i + rand.nextInt(2) - 1);
//                RenderUtil.addParticle(world, null, AffinityHelper.getParticleForAffinity(AffinityHelper.getAffinityForSpell(dataManager.get(STACK))), color, color, getPosX() - Math.cos(Math.toRadians(rotationYaw)) * -i + rand.nextInt(2) - 1, getPosY() + rand.nextInt(2) - 1, getPosZ() - Math.sin(Math.toRadians(rotationYaw)) * -i + rand.nextInt(2) - 1);
//            }
        } else {
            dataManager.set(EFFECT, dataManager.get(EFFECT) - 1);
            if (dataManager.get(EFFECT) <= 0) {
                dataManager.set(EFFECT, 5);
                for (Entity e : world.getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(getPosX() - dataManager.get(RADIUS), getPosY() - 1, getPosZ() - dataManager.get(RADIUS), getPosX() + dataManager.get(RADIUS), getPosY() + 3, getPosZ() + dataManager.get(RADIUS)))) {
                    if (e == this || e == getOwner()) continue;
                    if (e instanceof EnderDragonPartEntity && ((EnderDragonPartEntity) e).dragon != null)
                        e = ((EnderDragonPartEntity) e).dragon;
                    Vec3d a = new Vec3d(getPosX() - Math.cos(3.1415926f / 180 * (rotationYaw)) * dataManager.get(RADIUS), getPosY(), getPosZ() - Math.sin(3.1415926f / 180 * (rotationYaw)) * dataManager.get(RADIUS));
                    Vec3d b = new Vec3d(getPosX() - Math.cos(3.1415926f / 180 * (rotationYaw)) * -dataManager.get(RADIUS), getPosY(), getPosZ() - Math.sin(3.1415926f / 180 * (rotationYaw)) * -dataManager.get(RADIUS));
                    Vec3d target = new Vec3d(e.getPosX(), e.getPosY(), e.getPosZ());
                    Vec3d closest = SpellUtil.closestPointOnLine(target, a, b);
                    target = new Vec3d(target.x, 0, target.z);
                    closest = new Vec3d(closest.x, 0, closest.z);
                    if (e instanceof LivingEntity && closest.distanceTo(target) < 0.75f && Math.abs(getPosY() - e.getPosY()) < 2)
                        SpellUtil.applyStage(dataManager.get(STACK), getOwner(), (LivingEntity) e, getPosX(), getPosY(), getPosZ(), null, world, false, false, 0);
                }
            }
        }
        double dx = Math.cos(Math.toRadians(rotationYaw + 90));
        double dz = Math.sin(Math.toRadians(rotationYaw + 90));
        moveForced(dx * dataManager.get(SPEED), 0, dz * dataManager.get(SPEED));
        double dxH = Math.cos(Math.toRadians(rotationYaw));
        double dzH = Math.sin(Math.toRadians(rotationYaw));
        float radius = dataManager.get(RADIUS);
        for (int j = -1; j <= 1; j++) {
            Vec3d a = new Vec3d((getPosX() + dx) - dxH * radius, getPosY() + j, (getPosZ() + dz) - dzH * radius);
            Vec3d b = new Vec3d((getPosX() + dx) - dxH * -radius, getPosY() + j, (getPosZ() + dz) - dzH * -radius);
            double stepX = a.x < b.x ? 0.2f : -0.2f;
            double stepZ = a.z < b.z ? 0.2f : -0.2f;
            ArrayList<Vec3d> vecs = new ArrayList<>();
            Vec3d curPos = new Vec3d(a.x, a.y, a.z);
            for (int i = 0; i < getHeight(); ++i) vecs.add(new Vec3d(curPos.x, curPos.y + i, curPos.z));
            while (stepX != 0 || stepZ != 0) {
                if ((stepX < 0 && curPos.x <= b.x) || (stepX > 0 && curPos.x >= b.x))
                    stepX = 0;
                if ((stepZ < 0 && curPos.z <= b.z) || (stepZ > 0 && curPos.z >= b.z))
                    stepZ = 0;
                curPos = new Vec3d(curPos.x + stepX, curPos.y, curPos.z + stepZ);
                Vec3d tempPos = curPos.add(Vec3d.ZERO);
                if (!vecs.contains(tempPos))
                    for (int i = 0; i < getHeight(); ++i) vecs.add(new Vec3d(tempPos.x, tempPos.y + i, tempPos.z));
            }
            for (Vec3d vec : vecs)
                SpellUtil.applyStageBlock(dataManager.get(STACK), getOwner(), world, new BlockPos(vec), Direction.UP, vec.x + 0.5, vec.y + 0.5, vec.z + 0.5, false);
        }
        if (!world.isRemote && ticksExisted >= dataManager.get(TICKS)) remove();
    }

    public LivingEntity getOwner() {
        try {
            return (LivingEntity) world.getEntityByID(getDataManager().get(OWNER));
        } catch (RuntimeException e) {
            remove();
            return null;
        }
    }

    public void setOwner(LivingEntity owner) {
        dataManager.set(OWNER, owner.getEntityId());
    }

    public void setDamage(float damage) {
        dataManager.set(DAMAGE, damage);
    }

    public void setGravity(float gravity) {
        dataManager.set(GRAVITY, gravity);
    }

    public void setRadius(float radius) {
        dataManager.set(RADIUS, radius);
    }

    public void setSpeed(float speed) {
        dataManager.set(SPEED, speed);
    }

    public void setStack(ItemStack stack) {
        dataManager.set(STACK, stack);
    }

    public void setTicks(int ticks) {
        dataManager.set(TICKS, ticks);
    }
}
