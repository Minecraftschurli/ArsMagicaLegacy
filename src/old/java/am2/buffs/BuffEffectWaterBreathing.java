package am2.buffs;

import am2.defs.*;
import net.minecraft.entity.*;

public class BuffEffectWaterBreathing extends BuffEffect{

	int breath;

	public BuffEffectWaterBreathing(int duration, int amplifier){
		super(PotionEffectsDefs.waterBreathing, duration, amplifier);
	}

	@Override
	public void applyEffect(EntityLivingBase entityliving){
		breath = entityliving.getAir();
	}

	@Override
	public void performEffect(EntityLivingBase entityliving){
		if (entityliving.isInWater()){
			entityliving.setAir(breath);
		}else{
			breath = entityliving.getAir();
		}
	}

	@Override
	public void stopEffect(EntityLivingBase entityliving){

	}

	@Override
	public String spellBuffName(){
		return "Water Breathing";
	}

}
