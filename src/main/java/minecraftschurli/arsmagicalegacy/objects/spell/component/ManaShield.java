package minecraftschurli.arsmagicalegacy.objects.spell.component;

import com.google.common.collect.Sets;
import java.util.EnumSet;
import java.util.Set;
import minecraftschurli.arsmagicalegacy.api.affinity.Affinity;
import minecraftschurli.arsmagicalegacy.api.capability.CapabilityHelper;
import minecraftschurli.arsmagicalegacy.api.spell.SpellComponent;
import minecraftschurli.arsmagicalegacy.api.spell.SpellModifiers;
import minecraftschurli.arsmagicalegacy.init.ModAffinities;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public final class ManaShield extends SpellComponent {
    @Override
    public boolean applyEffectBlock(ItemStack stack, World world, BlockPos blockPos, Direction blockFace, double impactX, double impactY, double impactZ, LivingEntity caster) {
        return false;
    }

    @Override
    public boolean applyEffectEntity(ItemStack stack, World world, LivingEntity caster, Entity target) {
        float consumed = CapabilityHelper.getMana(caster);
        CapabilityHelper.decreaseMana(caster, consumed);
//        CapabilityHelper.addMagicShield((consumed / 250) * SpellUtils.getModifiedIntAdd(1, stack, caster, target, world, SpellModifiers.BUFF_POWER));
        return true;
    }

    @Override
    public Set<Affinity> getAffinity() {
        return Sets.newHashSet(ModAffinities.ARCANE.get());
    }

    @Override
    public float getAffinityShift(Affinity affinity) {
        return 0.01f;
    }

    @Override
    public float getBurnout(LivingEntity caster) {
        return 0;
    }

    @Override
    public float getManaCost(LivingEntity caster) {
        return 0;
    }

    @Override
    public EnumSet<SpellModifiers> getModifiers() {
        return EnumSet.of(SpellModifiers.BUFF_POWER);
    }
}
