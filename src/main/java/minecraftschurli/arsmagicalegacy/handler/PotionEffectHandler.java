package minecraftschurli.arsmagicalegacy.handler;

import minecraftschurli.arsmagicalegacy.api.event.SpellCastEvent;
import minecraftschurli.arsmagicalegacy.init.ModEffects;
import minecraftschurli.arsmagicalegacy.objects.effect.AMEffect;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.EnderTeleportEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingJumpEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.PotionEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.lwjgl.opengl.GL11;

public class PotionEffectHandler {
    private static final float DEFAULT_HEIGHT = 1.8F;
    private static final float DEFAULT_WIDTH = 0.6F;

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void playerPreDeathEvent(LivingDeathEvent e) {
        EffectInstance effect = e.getEntityLiving().getActivePotionEffect(ModEffects.TEMPORAL_ANCHOR.get());
        if (effect != null) {
            e.getEntityLiving().removePotionEffect(ModEffects.TEMPORAL_ANCHOR.get());
            e.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void addPotionEffect(PotionEvent.PotionAddedEvent e) {
        if (e.getEntity().getEntityWorld().isRemote())
            return;
        if (e.getPotionEffect().getPotion() instanceof AMEffect)
            ((AMEffect) e.getPotionEffect().getPotion()).startEffect(e.getEntityLiving(), e.getPotionEffect());
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void removePotionEffect(PotionEvent.PotionExpiryEvent e) {
        if (e.getEntity().getEntityWorld().isRemote())
            return;
        if (e.getPotionEffect() == null) return;
        if (e.getPotionEffect().getPotion() instanceof AMEffect)
            ((AMEffect) e.getPotionEffect().getPotion()).stopEffect(e.getEntityLiving(), e.getPotionEffect());
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void removePotionEffect(PotionEvent.PotionRemoveEvent e) {
        if (e.getEntity().getEntityWorld().isRemote())
            return;
        if (e.getPotion() instanceof AMEffect)
            ((AMEffect) e.getPotion()).stopEffect(e.getEntityLiving(), e.getPotionEffect());
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void entityDamageEvent(LivingHurtEvent event) {
        if (event.isCanceled()) return;
        if (event.getSource().damageType.equals(DamageSource.OUT_OF_WORLD.damageType)) return;
        if (event.getEntityLiving().isPotionActive(ModEffects.MAGIC_SHIELD.get()))
            event.setAmount(event.getAmount() * 0.25f);
		/*float damage = EntityExtension.For(event.getEntityLiving()).protect(event.getAmount());
		event.setAmount(damage);*/
    }

    @SubscribeEvent
    public static void playerJumpEvent(LivingJumpEvent event) {
        if (event.getEntityLiving().isPotionActive(ModEffects.AGILITY.get()))
            event.getEntityLiving().setMotion(event.getEntityLiving().getMotion().x, event.getEntityLiving().getMotion().y * 1.5f, event.getEntityLiving().getMotion().z);
        if (event.getEntityLiving().isPotionActive(ModEffects.ENTANGLE.get()))
            event.getEntityLiving().setMotion(event.getEntityLiving().getMotion().x, 0, event.getEntityLiving().getMotion().z);
    }

    @SubscribeEvent
    public static void livingFall(LivingFallEvent e) {
        if (e.getEntityLiving().isPotionActive(ModEffects.AGILITY.get())) e.setDistance(e.getDistance() / 1.5F);
    }

    @SubscribeEvent
    public static void spellCast(SpellCastEvent.Pre e) {
        if (e.caster.isPotionActive(ModEffects.CLARITY.get())) {
            e.manaCost = 0;
            e.burnout = 0;
            EffectInstance effect = e.caster.getActivePotionEffect(ModEffects.CLARITY.get());
            e.caster.removePotionEffect(ModEffects.CLARITY.get());
            if (effect == null || effect.getAmplifier() <= 0)
                return;
            e.caster.addPotionEffect(new EffectInstance(effect.getPotion(), effect.getDuration(), effect.getAmplifier() - 1));
        }
    }

    public static void onRender(RenderPlayerEvent event) {
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            PlayerEntity player = event.player;
            if (player.isPotionActive(ModEffects.SHRINK.get())) {
                float scalingFactor = 0.5f;
                setEntitySize(player, DEFAULT_WIDTH * scalingFactor, DEFAULT_HEIGHT * scalingFactor);
//                player.eyeHeight = player.getDefaultEyeHeight() * scalingFactor;
            } else {
//                player.eyeHeight = player.getDefaultEyeHeight();
            }
        }
    }

    private static void setEntitySize(Entity entity, float width, float height) {
        if (width != entity.getWidth() || height != entity.getHeight()) {
            float oldWidth = entity.getWidth();
//			entity.getWidth() = width;
//			entity.height = height;
            double halfWidth = width / 2;
            if (entity.getWidth() < oldWidth) {
                entity.setBoundingBox(new AxisAlignedBB(
                        entity.getPosX() - halfWidth,
                        entity.getPosY(),
                        entity.getPosZ() - halfWidth,
                        entity.getPosX() + halfWidth,
                        entity.getPosY() + entity.getHeight(),
                        entity.getPosZ() + halfWidth));
                return;
            }
            AxisAlignedBB axisalignedbb = entity.getBoundingBox();
            entity.setBoundingBox(new AxisAlignedBB(
                    axisalignedbb.minX - halfWidth,
                    axisalignedbb.minY,
                    axisalignedbb.minZ - halfWidth,
                    axisalignedbb.minX + halfWidth,
                    axisalignedbb.minY + entity.getHeight(),
                    axisalignedbb.minZ + halfWidth));
            if (entity.getWidth() > oldWidth && (entity.ticksExisted > 1) && !entity.world.isRemote) {
                double val = oldWidth - entity.getWidth();
                entity.move(MoverType.SELF, new Vec3d((val), 0, (val)));
            }
        }
    }

    @SubscribeEvent
    public static void teleportEvent(EnderTeleportEvent e) {
//		List<Long> keystoneKeys = KeystoneUtilities.instance.GetKeysInInvenory(e.getEntityLiving());
//		TileEntityAstralBarrier blockingBarrier = DimensionUtilities.GetBlockingAstralBarrier(e.getEntityLiving().world, new BlockPos(e.getTargetX(), e.getTargetY(), e.getTargetZ()), keystoneKeys);
//		if (e.getEntityLiving().isPotionActive(ModEffects.ASTRAL_DISTORTION.get()) || blockingBarrier != null){
//			e.setCanceled(true);
//			if (blockingBarrier != null) blockingBarrier.onEntityBlocked(e.getEntityLiving());
//		}
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public void preRender(RenderLivingEvent.Pre<PlayerEntity, PlayerModel<PlayerEntity>> event) {
        if (event.getEntity() instanceof AbstractClientPlayerEntity) {
            PlayerEntity player = (PlayerEntity) event.getEntity();
            if (player.isPotionActive(ModEffects.SHRINK.get())) {
                GL11.glPushMatrix();
                GL11.glScalef(0.5f, 0.5f, 0.5f);
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public void postRender(RenderLivingEvent.Post<PlayerEntity, PlayerModel<PlayerEntity>> event) {
        if (event.getEntity() instanceof AbstractClientPlayerEntity) {
            PlayerEntity player = (PlayerEntity) event.getEntity();
            if (player.isPotionActive(ModEffects.SHRINK.get())) GL11.glPopMatrix();
        }
    }

    @SubscribeEvent
    public void playerRender(RenderPlayerEvent.Pre e) {
        if (e.getEntityLiving().isPotionActive(ModEffects.TRUE_SIGHT.get())) {
//			GL11.glPushMatrix();
//			GL11.glRotated(e.getPlayer().rotationYawHead, 0, -1, 0);
//			int[] runes = SelectionUtils.getRuneSet(e.getPlayer());
//			int numRunes = runes.length;
//			double start = ((double)numRunes - 1) / 8D;
//			GL11.glTranslated(-start, 2.2, 0);
//			for (int rune : runes) {
//				GL11.glPushMatrix();
//				GL11.glScaled(0.25, 0.25, 0.25);
//				Minecraft.getInstance().getItemRenderer().renderItem(e.getPlayer(), new ItemStack(ModItems.RUNE.get(), 1), ItemCameraTransforms.TransformType.GUI);
//				GL11.glPopMatrix();
//				GL11.glTranslated(0.25, 0, 0);
//			}
//			GL11.glPopMatrix();
        }
    }
}
