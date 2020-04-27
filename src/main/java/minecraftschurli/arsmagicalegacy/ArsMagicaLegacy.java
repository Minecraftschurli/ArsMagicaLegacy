package minecraftschurli.arsmagicalegacy;

import minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import minecraftschurli.arsmagicalegacy.api.IMCHandler;
import minecraftschurli.arsmagicalegacy.api.config.Config;
import minecraftschurli.arsmagicalegacy.api.network.NetworkHandler;
import minecraftschurli.arsmagicalegacy.api.registry.RegistryHandler;
import minecraftschurli.arsmagicalegacy.api.registry.SkillPointRegistry;
import minecraftschurli.arsmagicalegacy.capabilities.*;
import minecraftschurli.arsmagicalegacy.compat.patchouli.PatchouliMultiblocks;
import minecraftschurli.arsmagicalegacy.handler.*;
import minecraftschurli.arsmagicalegacy.init.*;
import minecraftschurli.arsmagicalegacy.objects.block.blackaurem.BlackAuremTER;
import minecraftschurli.arsmagicalegacy.objects.block.craftingaltar.CraftingAltarViewTER;
import minecraftschurli.arsmagicalegacy.objects.block.etheriumgenerator.EtheriumGeneratorContainer;
import minecraftschurli.arsmagicalegacy.objects.block.etheriumgenerator.EtheriumGeneratorScreen;
import minecraftschurli.arsmagicalegacy.objects.block.inscriptiontable.InscriptionTableContainer;
import minecraftschurli.arsmagicalegacy.objects.block.inscriptiontable.InscriptionTableScreen;
import minecraftschurli.arsmagicalegacy.objects.item.AffinityTomeModel;
import minecraftschurli.arsmagicalegacy.objects.item.InfinityOrbItem;
import minecraftschurli.arsmagicalegacy.objects.item.spellbook.SpellBookContainer;
import minecraftschurli.arsmagicalegacy.objects.item.spellbook.SpellBookScreen;
import minecraftschurli.arsmagicalegacy.worldgen.WorldGenerator;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.IDyeableArmorItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModContainer;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.server.FMLServerAboutToStartEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.forgespi.language.IModInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;

/**
 * @author Minecraftschurli
 * @version 2019-11-07
 */
@Mod(ArsMagicaAPI.MODID)
public final class ArsMagicaLegacy {
    public static final ItemGroup ITEM_GROUP = new ItemGroup(ArsMagicaAPI.MODID) {
        @Nonnull
        @Override
        @OnlyIn(Dist.CLIENT)
        public ItemStack createIcon() {
            return ArsMagicaAPI.getCompendium();
        }
    };
    public static final Logger LOGGER = LogManager.getLogger(ArsMagicaAPI.MODID);
    public static ArsMagicaLegacy instance;

    public ArsMagicaLegacy() {
        instance = this;

        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::clientSetup);
        modEventBus.addListener(IMCHandler::processIMC);
        modEventBus.addListener(this::registerItemColorHandler);
        modEventBus.addListener(EventPriority.LOW, this::onRegistrySetupFinish);
        modEventBus.register(Config.class);

        MinecraftForge.EVENT_BUS.addListener(this::onAttachPlayerCapabilities);
        MinecraftForge.EVENT_BUS.addListener(this::onServerLoad);
        MinecraftForge.EVENT_BUS.addListener(this::beforeServerLoad);
        MinecraftForge.EVENT_BUS.register(TickHandler.class);
        MinecraftForge.EVENT_BUS.register(PotionEffectHandler.class);
        MinecraftForge.EVENT_BUS.register(LevelUpHandler.class);

        preInit();
    }

    //region =========LIFECYCLE=========

    private void preInit() {
        ArsMagicaAPI.setup();
        Config.setup();
        //noinspection Convert2MethodRef
        DistExecutor.runWhenOn(Dist.CLIENT, () -> () -> preInitClient());
        IInit.setEventBus(FMLJavaModLoadingContext.get().getModEventBus());
    }

    private void preInitClient() {
        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::registerModels);
        modEventBus.addListener(this::replaceModel);
        MinecraftForge.EVENT_BUS.register(new UIRender());
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        ArsMagicaAPI.init();
        WorldGenerator.setupBiomeGen();
        WorldGenerator.setupModFeatures();
        NetworkHandler.registerMessages();
        ManaCapability.register();
        BurnoutCapability.register();
        ResearchCapability.register();
        MagicCapability.register();
        RiftStorageCapability.register();
        AffinityCapability.register();
        AbilityCapability.register();
        ContingencyCapability.register();
        EtheriumCapability.register();
        /*ForgeRegistries.BIOMES.getValues()
                .stream()
                .filter(Predicates.instanceOf(ICustomFeatureBiome.class))
                .map(ICustomFeatureBiome.class::cast)
                .forEach(ICustomFeatureBiome::init);*/
        ModBiomes.WITCHWOOD_FOREST.get().init();
        AffinityAbilityHelper.registerListeners();
        PatchouliMultiblocks.register();
    }

    @SuppressWarnings("RedundantCast")
    private void clientSetup(final FMLClientSetupEvent event) {
        ScreenManager.registerFactory(ModContainers.SPELLBOOK.get(), (ScreenManager.IScreenFactory<SpellBookContainer, SpellBookScreen>) SpellBookScreen::new);
        ScreenManager.registerFactory(ModContainers.INSCRIPTION_TABLE.get(), (ScreenManager.IScreenFactory<InscriptionTableContainer, InscriptionTableScreen>) InscriptionTableScreen::new);
        ScreenManager.registerFactory(ModContainers.ETHERIUM_GENERATOR.get(), (ScreenManager.IScreenFactory<EtheriumGeneratorContainer, EtheriumGeneratorScreen>) EtheriumGeneratorScreen::new);

        ClientRegistry.bindTileEntityRenderer(ModTileEntities.ALTAR_VIEW.get(), CraftingAltarViewTER::new);
        ClientRegistry.bindTileEntityRenderer(ModTileEntities.BLACK_AUREM.get(), BlackAuremTER::new);
        RenderTypeLookup.setRenderLayer(ModBlocks.AUM.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(ModBlocks.CERUBLOSSOM.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(ModBlocks.DESERT_NOVA.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(ModBlocks.TARMA_ROOT.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(ModBlocks.WAKEBLOOM.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(ModBlocks.WITCHWOOD_SAPLING.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(ModBlocks.OCCULUS.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(ModBlocks.INSCRIPTION_TABLE.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(ModBlocks.WITCHWOOD_DOOR.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(ModBlocks.WITCHWOOD_TRAPDOOR.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(ModBlocks.MAGIC_WALL.get(), RenderType.getTranslucent());
        RenderTypeLookup.setRenderLayer(ModBlocks.REDSTONE_INLAY.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(ModBlocks.IRON_INLAY.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(ModBlocks.GOLD_INLAY.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(ModBlocks.OBELISK.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(ModBlocks.CELESTIAL_PRISM.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(ModBlocks.BLACK_AUREM.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(ModFluids.LIQUID_ESSENCE_FLOWING.get(), RenderType.getTranslucent());
        RenderTypeLookup.setRenderLayer(ModFluids.LIQUID_ESSENCE.get(), RenderType.getTranslucent());
    }
    //endregion
    //region =========EVENTS=========

    private void onAttachPlayerCapabilities(final AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof PlayerEntity) {
            event.addCapability(new ResourceLocation(ArsMagicaAPI.MODID, "mana"), new ManaCapability());
            event.addCapability(new ResourceLocation(ArsMagicaAPI.MODID, "burnout"), new BurnoutCapability());
            event.addCapability(new ResourceLocation(ArsMagicaAPI.MODID, "research"), new ResearchCapability());
            event.addCapability(new ResourceLocation(ArsMagicaAPI.MODID, "magic"), new MagicCapability());
            event.addCapability(new ResourceLocation(ArsMagicaAPI.MODID, "rift_storage"), new RiftStorageCapability());
            event.addCapability(new ResourceLocation(ArsMagicaAPI.MODID, "affinity"), new AffinityCapability());
            event.addCapability(new ResourceLocation(ArsMagicaAPI.MODID, "affinity_ability"), new AbilityCapability());
            event.addCapability(new ResourceLocation(ArsMagicaAPI.MODID, "contingency"), new ContingencyCapability());
        }
    }

    private void registerModels(final ModelRegistryEvent event) {
        RegistryHandler.getAffinityRegistry().forEach(affinity -> {
            ResourceLocation rl = affinity.getTextureLocation();
            if (rl == null) return;
            ModelLoader.addSpecialModel(new ModelResourceLocation(rl, "inventory"));
        });
    }

    private void replaceModel(final ModelBakeEvent event) {
        ModelResourceLocation key = new ModelResourceLocation(ModItems.AFFINITY_TOME.getId(), "inventory");
        IBakedModel oldModel = event.getModelRegistry().get(key);
        event.getModelRegistry().put(key, new AffinityTomeModel(oldModel));
    }

    private void onServerLoad(final FMLServerStartingEvent event) {
        ModCommands.register(event.getCommandDispatcher());
    }

    private void beforeServerLoad(final FMLServerAboutToStartEvent event) {
        minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI.beforeServerLoad(event);
    }
    //endregion
    //region =========UTIL=========

    private void registerItemColorHandler(final ColorHandlerEvent.Item event) {
        event.getItemColors().register((stack, index) -> index == 0 ? ((IDyeableArmorItem) stack.getItem()).getColor(stack) : -1, ModItems.SPELL_BOOK.get());
        //noinspection ConstantConditions
        event.getItemColors().register((stack, index) -> index == 0 && stack.hasTag() ? SkillPointRegistry.getSkillPointFromTier(stack.getTag().getInt(InfinityOrbItem.TYPE_KEY)).getColor() : -1, ModItems.INFINITY_ORB.get());
        event.getItemColors().register((stack, index) -> index > 0 ? -1 : PotionUtils.getColor(stack), ModItems.POTION_BUNDLE.get());
    }

    private void onRegistrySetupFinish(final RegistryEvent.NewRegistry event) {
        ModBlocks.register();
        ModFluids.register();
        ModItems.register();
        ModEntities.register();
        ModTileEntities.register();
        ModParticles.register();
        ModEffects.register();
        ModContainers.register();
        ModFeatures.register();
        ModBiomes.register();
        ModSkillTrees.register();
        ModAffinities.register();
        ModSpellParts.register();
    }

    public IModInfo getModInfo() {
        //noinspection OptionalGetWithoutIsPresent
        return ModList.get().getModContainerById(ArsMagicaAPI.MODID).map(ModContainer::getModInfo).get();
    }

    public String getVersion() {
        return getModInfo().getVersion().getQualifier();
    }
    //endregion
}
