package minecraftschurli.arsmagicalegacy.objects.spell.shape;

import java.util.EnumSet;
import minecraftschurli.arsmagicalegacy.api.spell.SpellCastResult;
import minecraftschurli.arsmagicalegacy.api.spell.SpellModifiers;
import minecraftschurli.arsmagicalegacy.api.spell.SpellShape;
import minecraftschurli.arsmagicalegacy.objects.spell.component.Attract;
import minecraftschurli.arsmagicalegacy.objects.spell.component.Repel;
import minecraftschurli.arsmagicalegacy.objects.spell.component.Telekinesis;
import minecraftschurli.arsmagicalegacy.util.SpellUtil;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.world.World;

public final class Channel extends SpellShape {
    @Override
    public SpellCastResult beginStackStage(Item item, ItemStack stack, LivingEntity caster, LivingEntity target, World world, double x, double y, double z, Direction side, boolean giveXP, int useCount) {
        if (useCount % 10 == 0 || SpellUtil.hasComponent(stack, Telekinesis.class) || SpellUtil.hasComponent(stack, Attract.class) || SpellUtil.hasComponent(stack, Repel.class))
            return SpellUtil.applyStageEntity(stack, caster, world, caster, giveXP);
        return SpellCastResult.SUCCESS;
    }

    @Override
    public EnumSet<SpellModifiers> getModifiers() {
        return EnumSet.noneOf(SpellModifiers.class);
    }

    @Override
    public boolean isChanneled() {
        return true;
    }

    @Override
    public boolean isPrincipumShape() {
        return false;
    }

    @Override
    public boolean isTerminusShape() {
        return true;
    }

    @Override
    public float manaCostMultiplier(ItemStack spellStack) {
        return 1;
    }
}
