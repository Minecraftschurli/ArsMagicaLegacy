package minecraftschurli.arsmagicalegacy.objects.particle;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.Locale;
import javax.annotation.Nonnull;
import net.minecraft.network.PacketBuffer;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleType;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.ForgeRegistries;

public class BeamParticleData implements IParticleData {
    public static final IParticleData.IDeserializer<BeamParticleData> DESERIALIZER = new IParticleData.IDeserializer<BeamParticleData>() {
        public BeamParticleData deserialize(ParticleType<BeamParticleData> particleTypeIn, StringReader reader) throws CommandSyntaxException {
            reader.expect(' ');
            float f1 = (float) reader.readDouble();
            reader.expect(' ');
            float f2 = (float) reader.readDouble();
            reader.expect(' ');
            float f3 = (float) reader.readDouble();
            reader.expect(' ');
            float f4 = (float) reader.readDouble();
            return new BeamParticleData(particleTypeIn, f1, f2, f3, f4);
        }

        public BeamParticleData read(ParticleType<BeamParticleData> particleTypeIn, PacketBuffer buffer) {
            return new BeamParticleData(particleTypeIn, buffer.readFloat(), buffer.readFloat(), buffer.readFloat(), buffer.readFloat());
        }
    };
    private final float red;
    private final float green;
    private final float blue;
    private final float alpha;
    private final ParticleType<BeamParticleData> type;

    public BeamParticleData(ParticleType<BeamParticleData> particleTypeIn, float red, float green, float blue, float alpha) {
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.alpha = MathHelper.clamp(alpha, 0.01F, 4);
        type = particleTypeIn;
    }

    public void write(PacketBuffer buffer) {
        buffer.writeFloat(red);
        buffer.writeFloat(green);
        buffer.writeFloat(blue);
        buffer.writeFloat(alpha);
    }

    @Nonnull
    public String getParameters() {
        return String.format(Locale.ROOT, "%s %.2f %.2f %.2f %.2f", ForgeRegistries.PARTICLE_TYPES.getKey(getType()), red, green, blue, alpha);
    }

    @Nonnull
    public ParticleType<BeamParticleData> getType() {
        return type;
    }

    @OnlyIn(Dist.CLIENT)
    public float getRed() {
        return red;
    }

    @OnlyIn(Dist.CLIENT)
    public float getGreen() {
        return green;
    }

    @OnlyIn(Dist.CLIENT)
    public float getBlue() {
        return blue;
    }

    @OnlyIn(Dist.CLIENT)
    public float getAlpha() {
        return alpha;
    }
}
