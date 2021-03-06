package minecraftschurli.arsmagicalegacy.objects.effect;

import java.util.UUID;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.potion.EffectType;

/**
 * @author Minecraftschurli
 * @version 2019-12-05
 */
public class SwiftSwimEffect extends AMEffect {
    private static final UUID swim_speed = UUID.fromString("A5B6CF2A-2F7C-51EF-9022-7C3E7D5E6AAC");

    public SwiftSwimEffect() {
        super(EffectType.BENEFICIAL, 0x3b3bff);
        addAttributesModifier(LivingEntity.SWIM_SPEED, swim_speed.toString(), 1.133f, AttributeModifier.Operation.MULTIPLY_TOTAL);
    }

    @Override
    public double getAttributeModifierAmount(int amplifier, AttributeModifier modifier) {
        if (modifier.getID() == swim_speed) return modifier.getAmount() + 0.03 * amplifier;
        return super.getAttributeModifierAmount(amplifier, modifier);
    }
}
