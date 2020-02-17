package minecraftschurli.arsmagicalegacy.objects.particle;

import com.mojang.blaze3d.platform.*;
import com.mojang.blaze3d.systems.*;
import com.mojang.blaze3d.vertex.*;
import minecraftschurli.arsmagicalegacy.util.*;
import net.minecraft.client.*;
import net.minecraft.client.particle.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;
import org.lwjgl.opengl.*;

import javax.annotation.*;

public class BeamParticle extends SpriteTexturedParticle {
    private double dX, dY, dZ, uX, uY, uZ;
    private float yaw, pitch, prevYaw, prevPitch;
    private boolean change;
    private boolean fppc;
    private int maxLengthAge;
    private BeamType type;
    private float length;
    private float rotate;

    public BeamParticle(World world, double x, double y, double z, double destX, double destY, double destZ, BeamParticleData data, TextureAtlasSprite sprite) {
        super(world, x, y, z);
        dX = destX;
        dY = destY;
        dZ = destZ;
        motionX = motionY = motionZ = 0;
        particleAlpha = data.getAlpha();
        particleRed = data.getRed();
        particleGreen = data.getGreen();
        particleBlue = data.getBlue();
        change = false;
        fppc = false;
        setSize(0.2f, 0.2f);
        lengthRotation();
        maxLengthAge = 10;
        type = BeamType.NORMAL;
        rotate = 30;
        maxAge = 10;
        this.sprite = sprite;
    }

    private void lengthRotation() {
        double deltaX = posX - dX;
        double deltaY = posY - dY;
        double deltaZ = posZ - dZ;
        length = MathHelper.sqrt(deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ);
        yaw = ((float) (Math.atan2(deltaX, deltaZ) * 180 / Math.PI));
        pitch = ((float) (Math.atan2(deltaY, MathHelper.sqrt(deltaX * deltaX + deltaZ * deltaZ)) * 180 / Math.PI));
    }

    public BeamParticle instant() {
        maxLengthAge = 1;
        return this;
    }

    public void setMaxAge(int maxAge) {
        this.maxAge = maxAge;
    }

    public void setLocationAndTarget(double pX, double pY, double pZ, double tX, double tY, double tZ) {
        uX = pX;
        uY = pY;
        uZ = pZ;
        dX = tX;
        dY = tY;
        dZ = tZ;
        if(age > maxAge - 5) maxAge = age + 5;
        change = true;
    }

    private void storePrev() {
        prevPosX = posX;
        prevPosY = posY;
        prevPosZ = posZ;
        if(change) {
            posX = uX;
            posY = uY;
            posZ = uZ;
            if(fppc) {
                PlayerEntity player = Minecraft.getInstance().player;
                if(player != null) {
                    double rotYaw = player.rotationYaw * Math.PI / 180;
                    posX -= Math.cos(rotYaw) * 0.06f;
                    posZ -= Math.sin(rotYaw) * 0.06f;
                    posY += 0.06f;
                    prevPosX = posX;
                    prevPosY = posY;
                    prevPosZ = posZ;
                }
            }
            change = false;
        }
        prevYaw = yaw;
        prevPitch = pitch;
    }

    public BeamParticle setFPPC() {
        fppc = true;
        return this;
    }

    private void beamAging() {
        age++;
        if(age >= maxAge) setExpired();
    }

    public void setType(BeamType type) {
        this.type = type;
    }

    @Override
    public void tick() {
        storePrev();
        lengthRotation();
        beamAging();
    }

    @Override
    public void renderParticle(IVertexBuilder iVertexBuilder, ActiveRenderInfo activeRenderInfo, float v) {
        RenderSystem.pushMatrix();
        RenderSystem.alphaFunc(GL11.GL_GREATER, 0.003921569F);
        RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA.param, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA.param);
        RenderSystem.texParameter(3553, 10242, 10497);
        RenderSystem.texParameter(3553, 10243, 10497);
        RenderSystem.translated(prevPosX + (posX - prevPosX) * v - posX, prevPosY + (posY - prevPosY) * v - posY, prevPosZ + (posZ - prevPosZ) * v - posZ);
        RenderSystem.rotatef(90, 1, 0, 0);
        RenderSystem.rotatef(180 + prevYaw + Math.abs(yaw) - Math.abs(prevYaw), 0, 0, -1);
        RenderSystem.rotatef(pitch, 1, 0, 0);
        float size = Math.min(age / maxLengthAge, 1);
        double offset = fppc ? 0.15d * size * 0.3f : 0.15d * size;
        RenderSystem.rotatef((world.getDayTime() % (360 / rotate) + 1) * rotate, 0, 1, 0);
        for (int t = 0; t < 5; t++){
            Tessellator.getInstance().draw();
            iVertexBuilder.lightmap(7);
            double l = length * size * particleScale;
            RenderSystem.rotatef(45, 0, 1, 0);
            RenderSystem.clearCurrentColor();
			if (t % 2 == 0) RenderSystem.color4f(particleRed, particleGreen, particleBlue, 0.4f);
			else RenderSystem.color4f(1, 1, 1, 0.4f);
            iVertexBuilder.pos(-offset, l, 0).tex(sprite.getMaxU(), sprite.getMinV()).color(particleRed, particleGreen, particleBlue, 0.4f).endVertex();
            iVertexBuilder.pos(-offset, 0, 0).tex(sprite.getMaxU(), sprite.getMaxV()).color(particleRed, particleGreen, particleBlue, 0.4f).endVertex();
            iVertexBuilder.pos(offset, 0, 0).tex(sprite.getMinU(), sprite.getMaxV()).color(particleRed, particleGreen, particleBlue, 0.4f).endVertex();
            iVertexBuilder.pos(offset, l, 0).tex(sprite.getMinU(), sprite.getMinV()).color(particleRed, particleGreen, particleBlue, 0.4f).endVertex();
            Tessellator.getInstance().draw();
            iVertexBuilder.lightmap(7);
        }
        RenderSystem.popMatrix();
    }

    @Override
    public IParticleRenderType getRenderType() {
        return IParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    public enum BeamType {
        NORMAL,
        DARK,
        LIGHT
    }

    public static class Factory implements IParticleFactory<BeamParticleData> {
        private TextureAtlasSprite sprite;
        public Factory(TextureAtlasSprite sprite) {
            this.sprite = sprite;
        }
        @Nullable
        @Override
        public Particle makeParticle(BeamParticleData beamParticleData, World world, double v, double v1, double v2, double v3, double v4, double v5) {
            return new BeamParticle(world, v, v1, v2, v3, v4, v5, beamParticleData, sprite);
        }
    }

    public void setColor(int color){
        particleRed = RenderUtils.getRed(color);
        particleGreen = RenderUtils.getGreen(color);
        particleBlue = RenderUtils.getBlue(color);
    }

    public void setColor(float r, float g, float b) {
        particleRed = r;
        particleGreen = g;
        particleBlue = b;
    }
}