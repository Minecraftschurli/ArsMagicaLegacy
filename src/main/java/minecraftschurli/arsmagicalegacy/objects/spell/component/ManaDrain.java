package minecraftschurli.arsmagicalegacy.spell.component;

import com.google.common.collect.*;
import minecraftschurli.arsmagicalegacy.api.affinity.*;
import minecraftschurli.arsmagicalegacy.api.extensions.*;
import minecraftschurli.arsmagicalegacy.api.spell.*;
import minecraftschurli.arsmagicalegacy.extensions.*;
import minecraftschurli.arsmagicalegacy.init.*;
import minecraftschurli.arsmagicalegacy.items.*;
import minecraftschurli.arsmagicalegacy.particles.*;
import net.minecraft.entity.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;

import java.util.*;

public class ManaDrain extends SpellComponent {
    @Override
    public boolean applyEffectEntity(ItemStack stack, World world, LivingEntity caster, Entity target) {
        if (!(target instanceof LivingEntity)) return false;
        double manaStolen = 250;
        IEntityExtension targetProperties = EntityExtension.For((LivingEntity) target);
        if (manaStolen > targetProperties.getCurrentMana()) {
            manaStolen = targetProperties.getCurrentMana();
        }
        targetProperties.setCurrentMana((float) (targetProperties.getCurrentMana() - manaStolen));
        IEntityExtension casterProperties = EntityExtension.For(caster);
        casterProperties.setCurrentMana((float) (casterProperties.getCurrentMana() + manaStolen));
        return true;
    }

    @Override
    public EnumSet<SpellModifiers> getModifiers() {
        return EnumSet.noneOf(SpellModifiers.class);
    }

    @Override
    public float getManaCost(LivingEntity caster) {
        return 20;
    }

    @Override
    public ItemStack[] reagents(LivingEntity caster) {
        return null;
    }

    @Override
    public void spawnParticles(World world, double x, double y, double z, LivingEntity caster, Entity target, Random rand, int colorModifier) {
        for (int i = 0; i < 15; ++i) {
            AMParticle particle = (AMParticle) ArsMagica2.proxy.particleManager.spawn(world, "sparkle2", x, y, z);
            if (particle != null) {
                particle.addRandomOffset(1, 1, 1);
                particle.setIgnoreMaxAge(true);
                particle.AddParticleController(new ParticleArcToEntity(particle, 1, caster, false).SetSpeed(0.03f).generateControlPoints());
                particle.setRGBColorF(0, 0.4f, 1);
                if (colorModifier > -1) {
                    particle.setRGBColorF(((colorModifier >> 16) & 0xFF) / 255.0f, ((colorModifier >> 8) & 0xFF) / 255.0f, (colorModifier & 0xFF) / 255.0f);
                }
            }
        }
    }

    @Override
    public Set<Affinity> getAffinity() {
        return Sets.newHashSet(Affinity.ARCANE);
    }

    @Override
    public ISpellIngredient[] getRecipe() {
        return new ISpellIngredient[]{
                new ItemStack(ModItems.BLACK_RUNE.get()),
                new ItemStack(ModItems.itemOre, 1, ItemOre.META_MOONSTONE),
                new ItemStack(ModItems.itemOre, 1, ItemOre.META_VINTEUM),
        };
    }

    @Override
    public float getAffinityShift(Affinity affinity) {
        return 0.01f;
    }

    @Override
    public void encodeBasicData(CompoundNBT tag, Object[] recipe) {
    }

    @Override
    public boolean applyEffectBlock(ItemStack stack, World world, BlockPos blockPos, Direction blockFace,
                                    double impactX, double impactY, double impactZ, LivingEntity caster) {
        return false;
    }
}