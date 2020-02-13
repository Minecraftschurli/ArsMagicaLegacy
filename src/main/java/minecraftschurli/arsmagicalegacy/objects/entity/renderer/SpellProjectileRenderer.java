package minecraftschurli.arsmagicalegacy.objects.entity.renderer;

import com.mojang.blaze3d.matrix.MatrixStack;
import minecraftschurli.arsmagicalegacy.objects.entity.SpellProjectileEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.ResourceLocation;

/**
 * @author Minecraftschurli
 * @version 2020-02-10
 */
public class SpellProjectileRenderer extends EntityRenderer<SpellProjectileEntity> {
    protected SpellProjectileRenderer(EntityRendererManager manager) {
        super(manager);
    }

    @Override
    public ResourceLocation getEntityTexture(SpellProjectileEntity entity) {
        return null;
    }
}