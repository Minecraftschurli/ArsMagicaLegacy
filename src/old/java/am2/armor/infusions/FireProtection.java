package am2.armor.infusions;

import am2.api.items.armor.*;
import net.minecraft.block.*;
import net.minecraft.block.material.*;
import net.minecraft.entity.player.*;
import net.minecraft.init.*;
import net.minecraft.inventory.*;
import net.minecraft.item.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;

import java.util.*;

public class FireProtection extends ArmorImbuement{

	@Override
	public String getID(){
		return "fireprot";
	}

	@Override
	public ImbuementTiers getTier(){
		return ImbuementTiers.FOURTH;
	}

	@Override
	public EnumSet<ImbuementApplicationTypes> getApplicationTypes(){
		return EnumSet.of(ImbuementApplicationTypes.ON_TICK);
	}

	@Override
	public boolean applyEffect(EntityPlayer player, World world, ItemStack stack, ImbuementApplicationTypes matchedType, Object... params){

		if (world.isRemote)
			return false;

		boolean effectApplied = false;
		//EntityExtension.For(player).armorProcCooldowns[1] == 0
		if (true){
			if (player.isInsideOfMaterial(Material.LAVA)){
				player.motionY = 0;
				player.fallDistance = 0;
				for (int i = -1; i <= 1; ++i){
					for (int j = -2; j <= 2; ++j){
						for (int k = -1; k <= 1; ++k){
							BlockPos newPos = player.getPosition().add(i, j, k);
							Block block = world.getBlockState(newPos).getBlock();
							if (block == Blocks.FLOWING_LAVA){
								if (i == 0 && k == 0 && j != -2){
									world.setBlockToAir(newPos);
								}else{
									world.setBlockState(newPos, Blocks.COBBLESTONE.getDefaultState());
								}
							}else if (block == Blocks.LAVA){
								if (i == 0 && k == 0 && j != -2){
									world.setBlockToAir(newPos);
								}else{
									world.setBlockState(newPos, Blocks.OBSIDIAN.getDefaultState());
								}
							}
						}
					}
				}
				effectApplied = true;
			}
		}
		if (player.isBurning()){
			player.extinguish();
		}
		return effectApplied;
	}

	@Override
	public EntityEquipmentSlot[] getValidSlots(){
		return new EntityEquipmentSlot[]{EntityEquipmentSlot.LEGS};
	}

	@Override
	public boolean canApplyOnCooldown(){
		return true;
	}

	@Override
	public int getCooldown(){
		return 900;
	}

	@Override
	public int getArmorDamage(){
		return 40;
	}
}
