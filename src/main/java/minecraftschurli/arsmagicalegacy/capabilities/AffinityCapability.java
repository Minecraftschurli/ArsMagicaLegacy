package minecraftschurli.arsmagicalegacy.capabilities;

import java.util.Map;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import minecraftschurli.arsmagicalegacy.api.capability.CapabilityHelper;
import minecraftschurli.arsmagicalegacy.api.capability.IAffinityStorage;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.LazyOptional;

/**
 * @author Minecraftschurli
 * @version 2020-02-13
 */
public class AffinityCapability implements ICapabilitySerializable<INBT> {
    private final LazyOptional<IAffinityStorage> instance = LazyOptional.of(CapabilityHelper.getAffinityCapability()::getDefaultInstance);

    public static void register() {
        CapabilityManager.INSTANCE.register(IAffinityStorage.class, new Capability.IStorage<IAffinityStorage>() {
            @Override
            public INBT writeNBT(Capability<IAffinityStorage> capability, IAffinityStorage instance, Direction side) {
                CompoundNBT compoundNBT = new CompoundNBT();
                ListNBT list = new ListNBT();
                for (Map.Entry<ResourceLocation, Double> entry : instance.getAffinitiesInternal().entrySet()) {
                    CompoundNBT compound = new CompoundNBT();
                    compound.putString("key", entry.getKey().toString());
                    compound.putDouble("value", entry.getValue());
                    list.add(compound);
                }
                compoundNBT.put("data", list);
                compoundNBT.putBoolean("locked", instance.isLocked());
                return compoundNBT;
            }

            @Override
            public void readNBT(Capability<IAffinityStorage> capability, IAffinityStorage instance, Direction side, INBT nbt) {
                for (INBT entry : ((CompoundNBT) nbt).getList("data", Constants.NBT.TAG_COMPOUND)) {
                    CompoundNBT compound = (CompoundNBT) entry;
                    instance.setAffinityDepth(ResourceLocation.tryCreate(compound.getString("key")), compound.getDouble("value"));
                }
                instance.setLocked(((CompoundNBT) nbt).getBoolean("locked"));
            }
        }, AffinityStorage::new);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        return cap == CapabilityHelper.getAffinityCapability() ? instance.cast() : LazyOptional.empty();
    }

    @Override
    public INBT serializeNBT() {
        return CapabilityHelper.getAffinityCapability().getStorage().writeNBT(CapabilityHelper.getAffinityCapability(), instance.orElseThrow(() -> new IllegalArgumentException("LazyOptional must not be empty!")), null);
    }

    @Override
    public void deserializeNBT(INBT nbt) {
        CapabilityHelper.getAffinityCapability().getStorage().readNBT(CapabilityHelper.getAffinityCapability(), instance.orElseThrow(() -> new IllegalArgumentException("LazyOptional must not be empty!")), null, nbt);
    }
}
