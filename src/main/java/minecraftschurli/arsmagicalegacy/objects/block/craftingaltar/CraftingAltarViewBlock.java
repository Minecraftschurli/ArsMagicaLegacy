package minecraftschurli.arsmagicalegacy.objects.block.craftingaltar;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.block.AirBlock;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;

/**
 * @author Minecraftschurli
 * @version 2019-12-30
 */
public class CraftingAltarViewBlock extends AirBlock {
    public CraftingAltarViewBlock() {
        super(Properties.from(Blocks.AIR));
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new CraftingAltarViewTileEntity();
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nonnull
    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.ENTITYBLOCK_ANIMATED;
    }
}
