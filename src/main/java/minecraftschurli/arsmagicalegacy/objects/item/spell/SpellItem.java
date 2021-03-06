package minecraftschurli.arsmagicalegacy.objects.item.spell;

import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import minecraftschurli.arsmagicalegacy.ArsMagicaLegacy;
import minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import minecraftschurli.arsmagicalegacy.api.capability.CapabilityHelper;
import minecraftschurli.arsmagicalegacy.api.spell.SpellCastResult;
import minecraftschurli.arsmagicalegacy.api.spell.SpellShape;
import minecraftschurli.arsmagicalegacy.util.SpellUtil;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * @author Minecraftschurli
 * @version 2019-11-07
 */
public class SpellItem extends Item {
    public SpellItem() {
        //noinspection Convert2MethodRef
        super(new Properties().maxStackSize(1)/*.setISTER(() -> () -> new SpellISTER())*/);
    }

    @Override
    @Nonnull
    public ActionResult<ItemStack> onItemRightClick(@Nonnull World worldIn, @Nonnull PlayerEntity playerIn, @Nonnull Hand handIn) {
        ItemStack heldItem = playerIn.getHeldItem(handIn);
        if (CapabilityHelper.getCurrentLevel(playerIn) < 0 && !playerIn.isCreative()) {
            return ActionResult.resultFail(heldItem);
        }
        if (heldItem.hasTag()) {
            String icon = heldItem.getTag().getString("icon");
            if (!icon.isEmpty()) {
                ResourceLocation iconLoc = ResourceLocation.tryCreate(icon);
                if (iconLoc != null) {
                    playerIn.setActiveHand(handIn);
                    return new ActionResult<>(ActionResultType.SUCCESS, heldItem);
                }
            }
        }
        openIconPickGui(worldIn, playerIn, heldItem);
        return new ActionResult<>(ActionResultType.SUCCESS, heldItem);
    }

    private void openIconPickGui(World worldIn, PlayerEntity playerIn, ItemStack heldItem) {
        heldItem.getOrCreateTag().putString("icon", "arsmagicalegacy:shadow-spell");
    }

    @Override
    @Nonnull
    public ActionResultType onItemUse(ItemUseContext context) {
        if (CapabilityHelper.getCurrentLevel(context.getPlayer()) < 0 && !context.getPlayer().isCreative()) {
            return ActionResultType.FAIL;
        }
        context.getPlayer().setActiveHand(context.getHand());
        return ActionResultType.SUCCESS;
    }

    @Override
    public void onPlayerStoppedUsing(@Nonnull ItemStack stack, World worldIn, @Nonnull LivingEntity entityLiving, int timeLeft) {
        if (worldIn.isRemote)
            return;
        SpellShape shape = SpellUtil.getShape(stack, 0);
        if (!stack.hasTag()) return;
        if (shape != null) {
            if (!shape.isChanneled()) {
                SpellCastResult result = SpellUtil.applyStage(stack, entityLiving, null, entityLiving.getPosX(), entityLiving.getPosY(), entityLiving.getPosZ(), Direction.UP, worldIn, true, true, 0);
                ArsMagicaLegacy.LOGGER.debug(result);
            }
            /*if (worldIn.isRemote && shape.isChanneled()){
                //SoundHelper.instance.stopSound(shape.getSoundForAffinity(SpellUtils.instance.mainAffinityFor(stack), stack, null));
            }*/
        }
    }

    @Override
    public void onUsingTick(ItemStack stack, LivingEntity player, int count) {
        if (player.world.isRemote)
            return;
        SpellShape shape = SpellUtil.getShape(stack, 0);
        if (shape.isChanneled())
            SpellUtil.applyStage(stack, player, null, player.getPosX(), player.getPosY(), player.getPosZ(), Direction.UP, player.world, true, true, count - 1);
        super.onUsingTick(stack, player, count);
    }

    @Override
    public int getUseDuration(@Nonnull ItemStack stack) {
        return 72000;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, @Nonnull List<ITextComponent> tooltip, @Nonnull ITooltipFlag flagIn) {
        tooltip.add(new TranslationTextComponent(ArsMagicaAPI.MODID + ".spell.manaCost", SpellUtil.getMana(stack, ArsMagicaAPI.getLocalPlayer())));
    }
}
