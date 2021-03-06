package minecraftschurli.arsmagicalegacy.api.network;

import minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import minecraftschurli.arsmagicalegacy.api.capability.CapabilityHelper;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

/**
 * @author Minecraftschurli
 * @version 2019-11-14
 */
public class SyncManaPacket implements IPacket {
    private float mana;
    private float maxMana;

    public SyncManaPacket() {}

    public SyncManaPacket(float mana, float maxMana) {
        this.maxMana = maxMana;
        this.mana = mana;
    }

    @Override
    public void serialize(PacketBuffer buf) {
        buf.writeFloat(maxMana);
        buf.writeFloat(mana);
    }

    @Override
    public void deserialize(PacketBuffer buf) {
        maxMana = buf.readFloat();
        mana = buf.readFloat();
    }

    @Override
    public boolean handle(NetworkEvent.Context ctx) {
        ArsMagicaAPI.getLocalPlayer().getCapability(CapabilityHelper.getManaCapability()).ifPresent(iManaStorage -> {
            iManaStorage.setMaxMana(maxMana);
            iManaStorage.setMana(mana);
        });
        return true;
    }
}
