package minecraftschurli.arsmagicalegacy.objects.spell.component;

import minecraftschurli.arsmagicalegacy.api.spell.*;
import minecraftschurli.arsmagicalegacy.api.spell.crafting.*;
import minecraftschurli.arsmagicalegacy.init.*;
import minecraftschurli.arsmagicalegacy.objects.item.spellbook.*;
import minecraftschurli.arsmagicalegacy.util.*;
import net.minecraft.block.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.tileentity.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;

import java.util.*;

public class Appropriation extends SpellComponent {
    private static final String storageKey = "stored_data";
    private static final String storageType = "storage_type";

    @Override
    public boolean applyEffectBlock(ItemStack stack, World world, BlockPos blockPos, Direction blockFace, double impactX, double impactY, double impactZ, LivingEntity caster) {
        if (!(caster instanceof PlayerEntity)) return false;
        ItemStack originalSpellStack = getOriginalSpellStack((PlayerEntity)caster);
        if (originalSpellStack == null) return false;
        if (originalSpellStack.getTag() == null) return false;
        Block block = world.getBlockState(blockPos).getBlock();
        if (!world.isRemote){
            if (originalSpellStack.getTag().get(storageKey) != null){
                if (world.getBlockState(blockPos).equals(Blocks.AIR.getDefaultState())) blockFace = null;
                if (blockFace != null) blockPos = blockPos.add(blockFace.getDirectionVec());
                if (world.isAirBlock(blockPos) || !world.getBlockState(blockPos).getMaterial().isSolid()){
                    CompoundNBT nbt = null;
                    if (stack.getTag() != null) nbt = stack.getTag().copy();
//                    PlayerEntity casterPlayer = PlayerEntity.fromEntityLiving(caster);
                    world.captureBlockSnapshots = true;
                    restore((PlayerEntity)caster, world, originalSpellStack, blockPos, impactX, impactY, impactZ);
                    world.captureBlockSnapshots = false;
                    CompoundNBT newNBT = null;
                    if (stack.getTag() != null) newNBT = stack.getTag().copy();
                    net.minecraftforge.event.world.BlockEvent.EntityPlaceEvent placeEvent = null;
                    @SuppressWarnings("unchecked")
                    List<net.minecraftforge.common.util.BlockSnapshot> blockSnapshots = (List<net.minecraftforge.common.util.BlockSnapshot>) world.capturedBlockSnapshots.clone();
                    world.capturedBlockSnapshots.clear();
                    if (nbt != null) stack.setTag(nbt);
//                    if (blockSnapshots.size() > 1) placeEvent = ForgeEventFactory.onPlayerMultiBlockPlace(casterPlayer, blockSnapshots, blockFace);
//                    else if (blockSnapshots.size() == 1) placeEvent = ForgeEventFactory.onPlayerBlockPlace(casterPlayer, blockSnapshots.get(0), blockFace);
                    if (placeEvent != null && (placeEvent.isCanceled())){ 
                        for (net.minecraftforge.common.util.BlockSnapshot blocksnapshot : blockSnapshots){
                            world.restoringBlockSnapshots = true;
                            blocksnapshot.restore(true, false);
                            world.restoringBlockSnapshots = false;
                        }
                        return false;
                    } else {
                        if (nbt != null) stack.setTag(newNBT);
                        for (net.minecraftforge.common.util.BlockSnapshot blocksnapshot : blockSnapshots){
                            BlockPos pos = blocksnapshot.getPos();
                            int updateFlag = blocksnapshot.getFlag();
                            BlockState oldBlock = blocksnapshot.getReplacedBlock();
                            BlockState newBlock = world.getBlockState(pos);
                            if (!newBlock.getBlock().hasTileEntity(newBlock)) newBlock.getBlock();
                            world.markAndNotifyBlock(pos, null, oldBlock, newBlock, updateFlag);
                        }
                    }
                    world.capturedBlockSnapshots.clear();
                }
            }else{
                if (block.getBlockHardness(world.getBlockState(blockPos), world, blockPos) == -1.0f) return false;
                CompoundNBT data = new CompoundNBT();
                data.putString(storageType, "block");
//                data.putInt("blockID", Block.getIdFromBlock(block));
//                int meta = world.getBlockState(blockPos).getBlock().getMetaFromState(world.getBlockState(blockPos));
//                data.putInt("meta", meta);
//                PlayerEntity casterPlayer = (PlayerEntity)DummyPlayerEntity.fromEntityLiving(caster);
//                if (!ForgeEventFactory.doPlayerHarvestCheck(casterPlayer, world.getBlockState(blockPos), true)) return false;
//                int event = ForgeHooks.onBlockBreakEvent(world, casterPlayer.interactionManager.getGameType(), casterPlayer, blockPos);
//                if (event == -1) return false;
                TileEntity te = world.getTileEntity(blockPos);
                if (te != null){
                    CompoundNBT teData = new CompoundNBT();
                    te.write(teData);
                    data.put("tileEntity", teData);
                    try{
                        world.removeTileEntity(blockPos);
                    }catch (Throwable exception){
                        exception.printStackTrace();
                    }
                }
                originalSpellStack.getTag().put(storageKey, data);
                setOriginalSpellStackData((PlayerEntity)caster, originalSpellStack);
                world.setBlockState(blockPos, Blocks.AIR.getDefaultState());
            }
        }
        return true;
    }

    @Override
    public boolean applyEffectEntity(ItemStack stack, World world, LivingEntity caster, Entity target) {
        if (target instanceof PlayerEntity) return false;
        if (!(target instanceof LivingEntity)) return false;
        if (!(caster instanceof PlayerEntity)) return false;
        ItemStack originalSpellStack = getOriginalSpellStack((PlayerEntity)caster);
        if (originalSpellStack == null) return false;
        if (!world.isRemote){
            if (originalSpellStack.getTag().get(storageKey) != null) restore((PlayerEntity)caster, world, originalSpellStack, target.getPosition(), target.posX, target.posY + target.getEyeHeight(), target.posZ);
            else {
                CompoundNBT data = new CompoundNBT();
                data.putString("class", target.getClass().getName());
                data.putString(storageType, "ent");
                CompoundNBT targetData = new CompoundNBT();
//                target.writeToNBT(targetData);
                data.put("targetNBT", targetData);
                originalSpellStack.getTag().put(storageKey, data);
                setOriginalSpellStackData((PlayerEntity)caster, originalSpellStack);
                target.remove();
            }
        }
        return true;
    }

    @Override
    public float getManaCost(LivingEntity caster) {
        return 415;
    }

    @Override
    public ItemStack[] getReagents(LivingEntity caster) {
        return new ItemStack[0];
    }

    @Override
    public void spawnParticles(World world, double x, double y, double z, LivingEntity caster, Entity target, Random rand, int colorModifier) {

    }

    @Override
    public ISpellIngredient[] getRecipe() {
        return new ISpellIngredient[]{
                new ItemStackSpellIngredient(new ItemStack(ModItems.CHIMERITE.get())),
                new ItemStackSpellIngredient(new ItemStack(Items.ENDER_PEARL)),
                new ItemStackSpellIngredient(new ItemStack(Items.CHEST))
        };
    }

    @Override
    public void encodeBasicData(CompoundNBT tag, ISpellIngredient[] recipe) {

    }

    @Override
    public EnumSet<SpellModifiers> getModifiers() {
        return EnumSet.noneOf(SpellModifiers.class);
    }

    private void setOriginalSpellStackData(PlayerEntity caster, ItemStack modifiedStack){
        ItemStack originalSpellStack = caster.getHeldItemMainhand();
/*        if (originalSpellStack.getItem() instanceof SpellBookItem) ((SpellBookItem)originalSpellStack.getItem()).replaceAciveItemStack(originalSpellStack, modifiedStack);
        else*/ caster.inventory.setInventorySlotContents(caster.inventory.currentItem, modifiedStack);
    }

    private void restore(PlayerEntity player, World world, ItemStack stack, BlockPos pos, double hitX, double hitY, double hitZ){
        if (stack.getTag().get(storageKey) != null) {
            CompoundNBT storageCompound = stack.getTag().getCompound(storageKey);
            String type = storageCompound.getString(storageType);
            if (type.equals("ent")){
                String clazz = storageCompound.getString("class");
                CompoundNBT entData = storageCompound.getCompound("targetNBT");
                try{
                    Entity ent = (Entity)Class.forName(clazz).getConstructor(World.class).newInstance(world);
                    ent.read(entData);
                    ent.setPosition(hitX, hitY, hitZ);
                    world.addEntity(ent);
                }catch (Throwable t){
                    t.printStackTrace();
                }
            }else if (type.equals("block")){
                int blockID = storageCompound.getInt("blockID");
                Block block = Block.getStateById(blockID).getBlock();
                world.setBlockState(pos, block.getDefaultState(), 2);
                if (storageCompound.get("tileEntity") != null) {
                    TileEntity te = world.getTileEntity(pos);
                    if (te != null){
                        te.read(storageCompound.getCompound("tileEntity"));
                        te.setPos(pos);
                        te.setWorld(world);
                    }
                }
            }
            stack.getTag().remove(storageKey);
            setOriginalSpellStackData(player, stack);
        }
    }

    private ItemStack getOriginalSpellStack(PlayerEntity caster){
        ItemStack originalSpellStack = caster.getHeldItemMainhand();
        if (originalSpellStack.getItem() instanceof SpellBookItem){
            originalSpellStack = ((SpellBookItem)originalSpellStack.getItem()).getActiveItemStack(originalSpellStack);
            boolean hasAppropriation = false;
            for (int i = 0; i < SpellUtils.numStages(originalSpellStack); ++i) if (SpellUtils.componentIsPresent(originalSpellStack, Appropriation.class)){
                hasAppropriation = true;
                break;
            }
            if (!hasAppropriation) return null;
        }
        return originalSpellStack;
    }
}