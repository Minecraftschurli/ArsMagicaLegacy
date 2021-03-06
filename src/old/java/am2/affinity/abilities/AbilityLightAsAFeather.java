package am2.affinity.abilities;

import am2.api.affinity.AbstractAffinityAbility;
import am2.api.affinity.Affinity;
import am2.api.event.SpellCastEvent.Pre;
import am2.defs.PotionEffectsDefs;
import am2.packet.AMNetHandler;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

public class AbilityLightAsAFeather extends AbstractAffinityAbility {

	public AbilityLightAsAFeather() {
		super(new ResourceLocation("arsmagica2", "lightasafeather"));
	}

	@Override
	public float getMinimumDepth() {
		return 0.5f;
	}
	
	@Override
	public float getMaximumDepth() {
		return 0.85f;
	}

	@Override
	public Affinity getAffinity() {
		return Affinity.AIR;
	}
	
	@Override
	public void applyPreSpellCast(EntityPlayer ent, Pre event) {
		if (ent.worldObj.isRaining() && !ent.worldObj.isRemote && ent.getEntityWorld().getBiome(ent.getPosition()).canRain() && !ent.worldObj.isRemote && ent.worldObj.rand.nextInt(100) < 10){
			if (!ent.isSneaking() && !ent.isPotionActive(PotionEffectsDefs.gravityWell) && !ent.isInsideOfMaterial(Material.WATER) && ent.isWet()){
				double velX = ent.worldObj.rand.nextDouble() - 0.5;
				double velY = ent.worldObj.rand.nextDouble() - 0.5;
				double velZ = ent.worldObj.rand.nextDouble() - 0.5;
				ent.addVelocity(velX, velY, velZ);
				AMNetHandler.INSTANCE.sendVelocityAddPacket(ent.worldObj, ent, velX, velY, velZ);
			}
		}
	}
}
