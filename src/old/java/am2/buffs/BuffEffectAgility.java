package am2.buffs;

import am2.defs.*;
import net.minecraft.entity.*;

public class BuffEffectAgility extends BuffEffect{

	public BuffEffectAgility(int duration, int amplifier){
		super(PotionEffectsDefs.agility, duration, amplifier);
	}

	@Override
	public void applyEffect(EntityLivingBase entityliving){
	}

	@Override
	public void stopEffect(EntityLivingBase entityliving){
	}

	@Override
	public void performEffect(EntityLivingBase entityliving){
		entityliving.setAIMoveSpeed(entityliving.getAIMoveSpeed() * 1.2f);
	}

	@Override
	protected String spellBuffName(){
		return "Agility";
	}

}
