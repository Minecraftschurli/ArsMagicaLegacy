package minecraftschurli.arsmagicalegacy.init;

import minecraftschurli.arsmagicalegacy.objects.entity.SpellProjectileEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraftforge.fml.RegistryObject;

/**
 * @author Minecraftschurli
 * @version 2019-11-25
 */
public class ModEntities implements IInit {
    public static final RegistryObject<EntityType<Entity>> SPELL_PROJECTILE = ENTITIES.register("spell_projectile", () -> EntityType.Builder.create(SpellProjectileEntity::new, EntityClassification.MISC).build("spell_projectile"));
}