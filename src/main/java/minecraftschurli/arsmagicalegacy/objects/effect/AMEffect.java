package minecraftschurli.arsmagicalegacy.objects.effect;

import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectType;

/**
 * @author Minecraftschurli
 * @version 2019-12-04
 */
public class AMEffect extends Effect {
    public AMEffect(EffectType type, int liquidColor) {
        super(type, liquidColor);
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        return true;
    }

    @Override
    public void affectEntity(@Nullable Entity source, @Nullable Entity indirectSource, LivingEntity entityLivingBaseIn, int amplifier, double health) {
        performEffect(entityLivingBaseIn, amplifier);
    }

    @Override
    public void performEffect(LivingEntity livingEntity, int amplifier) {
    }

    public void stopEffect(LivingEntity livingEntity, EffectInstance potionEffect) {
    }

    public void startEffect(LivingEntity livingEntity, EffectInstance potionEffect) {
    }
}
