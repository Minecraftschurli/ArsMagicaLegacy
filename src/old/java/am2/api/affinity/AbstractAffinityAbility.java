package am2.api.affinity;

import am2.api.event.*;
import am2.api.extensions.*;
import am2.extensions.*;
import net.minecraft.client.settings.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.*;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.event.entity.living.LivingEvent.*;
import net.minecraftforge.fml.common.registry.*;

import javax.annotation.*;

public abstract class AbstractAffinityAbility extends IForgeRegistryEntry.Impl<AbstractAffinityAbility>{
	
	protected AbstractAffinityAbility(ResourceLocation identifier) {
		this.setRegistryName(identifier);
	}
	
	/**
	 * At which point does this ability enable ?
	 * 
	 * @return a depth.
	 */	
	public abstract float getMinimumDepth();
	
	/**
	 * At which point does this ability disables ?
	 * 
	 * @return a depth or any value under 0 or over 1 to ignore this.
	 */
	public float getMaximumDepth() {
		return -1F;
	}
	
	/**
	 * Setting this to null or NONE will make this class useless.
	 * 
	 * @return the ability that is required.
	 */
	public abstract Affinity getAffinity();
	
	/**
	 * If this Affinity Ability uses a key binding, return it, otherwise just return null
	 * 
	 * @return the key binding that this ability uses, or null.
	 */
	@Nullable
	public KeyBinding getKey() {
		return null;
	}
	
	/**
	 * Checks if the player can use this ability. Most of the time you won't need change this unless you are using toggle {@link KeyBinding}s, in that case consider using {@link AbstractToggledAffinityAbility}.
	 * 
	 * @param player : the current player.
	 * @return if the player can use this ability.
	 */
	public boolean canApply(EntityPlayer player) {
		return isEligible(player);
	}
	
	public boolean isEligible(EntityPlayer player) {
		Affinity aff = this.getAffinity();
		if (aff == Affinity.NONE || aff == null)
			return false;
		IAffinityData data = AffinityData.For(player);
		double depth = data.getAffinityDepth(aff);
		if (getMaximumDepth() < 0F || getMaximumDepth() > 1F || getMaximumDepth() < getMinimumDepth())
			return depth >= getMinimumDepth();
		return depth >= getMinimumDepth() && depth <= getMaximumDepth();
	}
	
	/**
	 * The thing that this ability does
	 * 
	 * @param player : the current player
	 */
	public void applyTick(EntityPlayer player) {}
	
	public void applyKeyPress(EntityPlayer player) {};
	
	public void applyHurt(EntityPlayer player, LivingHurtEvent event, boolean isAttacker) {}
	
	public void applyFall(EntityPlayer player, LivingFallEvent event) {}
	
	public void applySpellCast(EntityPlayer player, SpellCastEvent.Post event) {}
	
	public void applyPreSpellCast(EntityPlayer player, SpellCastEvent.Pre event) {}
	
	public void applyDeath(EntityPlayer player, LivingDeathEvent event) {}
	
	public void applyKill(EntityPlayer player, LivingDeathEvent event) {}
	
	public void applyJump(EntityPlayer player, LivingJumpEvent event) {}
	
	public void removeEffects(EntityPlayer player) {}
	/**
	 * For internal use
	 */
	public Runnable createRunnable(EntityPlayer player) {
		return new Apply(player, this);
	}
	
	public boolean hasMax() {
		return getMaximumDepth() >= 0F && getMaximumDepth() <= 1F && getMaximumDepth() > getMinimumDepth();
	}
	
	private static class Apply implements Runnable {
		
		private EntityPlayer player;
		private AbstractAffinityAbility ability;
		
		public Apply(EntityPlayer player, AbstractAffinityAbility ability) {
			this.player = player;
			this.ability = ability;
		}
		
		@Override
		public void run() {
			ability.applyKeyPress(player);
		}
	}
}
