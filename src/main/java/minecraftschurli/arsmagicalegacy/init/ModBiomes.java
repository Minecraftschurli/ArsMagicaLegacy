package minecraftschurli.arsmagicalegacy.init;

import minecraftschurli.arsmagicalegacy.worldgen.biomes.WitchwoodForestBiome;
import net.minecraftforge.fml.RegistryObject;

/**
 * @author Minecraftschurli
 * @version 2019-11-22
 */
public final class ModBiomes implements IInit {
    public static final RegistryObject<WitchwoodForestBiome> WITCHWOOD_FOREST = BIOMES.register("witchwood_forest", WitchwoodForestBiome::new);

    public static void register() {
    }
}
