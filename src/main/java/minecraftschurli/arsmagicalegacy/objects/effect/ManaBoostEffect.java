package minecraftschurli.arsmagicalegacy.objects.effect;

import minecraftschurli.arsmagicalegacy.*;
import minecraftschurli.arsmagicalegacy.util.*;
import net.minecraft.entity.*;
import net.minecraft.nbt.*;
import net.minecraft.potion.*;

/**
 * @author Minecraftschurli
 * @version 2019-12-07
 */
public class ManaBoostEffect extends AMEffect {
    public ManaBoostEffect() {
        super(EffectType.BENEFICIAL, 0x0093ff);
    }

    @Override
    public void startEffect(LivingEntity livingEntity, EffectInstance potionEffect) {
        if (!livingEntity.getPersistentData().hasUniqueId(ArsMagicaLegacy.MODID))
            livingEntity.getPersistentData().put(ArsMagicaLegacy.MODID, new CompoundNBT());
        float maxMana = MagicHelper.getMaxMana(livingEntity);
        livingEntity.getPersistentData().getCompound(ArsMagicaLegacy.MODID).put("mana_boost", new FloatNBT(maxMana));
        MagicHelper.getManaCapability(livingEntity).setMaxMana(maxMana + 10 * potionEffect.getAmplifier());
    }

    @Override
    public void stopEffect(LivingEntity livingEntity, EffectInstance potionEffect) {
        MagicHelper.getManaCapability(livingEntity).setMaxMana(livingEntity.getPersistentData().getCompound(ArsMagicaLegacy.MODID).getFloat("mana_boost"));
    }
}
