package minecraftschurli.arsmagicalegacy.handler;

import com.mojang.blaze3d.systems.RenderSystem;
import java.util.List;
import minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import minecraftschurli.arsmagicalegacy.api.capability.CapabilityHelper;
import minecraftschurli.arsmagicalegacy.init.ModItems;
import minecraftschurli.arsmagicalegacy.objects.item.spellbook.SpellBookItem;
import minecraftschurli.arsmagicalegacy.util.ParticleUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec2f;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

/**
 * @author Minecraftschurli
 * @version 2019-11-12
 */
public class UIRender {
    public static final ResourceLocation BAR_TEXTURE = new ResourceLocation(ArsMagicaAPI.MODID, "textures/gui/bar.png");
    public static final ResourceLocation SPELL_BOOK_UI_TEXTURE = new ResourceLocation(ArsMagicaAPI.MODID, "textures/gui/spellbook_ui.png");
    public static final ResourceLocation MC_TEXTURE = new ResourceLocation("textures/gui/icons.png");
    private final Minecraft mc = Minecraft.getInstance();
    private float blitOffset;

    public static int getWidth(double d1, double d2) {
        return (int) Math.ceil(Math.max(80 * (d1 / d2), 0));
    }

    private FontRenderer fontRender() {
        return mc.fontRenderer;
    }

    @SubscribeEvent
    public void nudge(RenderGameOverlayEvent.Pre event) {
        if (event.getType() == RenderGameOverlayEvent.ElementType.EXPERIENCE ||
                event.getType() == RenderGameOverlayEvent.ElementType.HEALTH ||
                event.getType() == RenderGameOverlayEvent.ElementType.HEALTHMOUNT ||
                event.getType() == RenderGameOverlayEvent.ElementType.FOOD ||
                event.getType() == RenderGameOverlayEvent.ElementType.AIR ||
                event.getType() == RenderGameOverlayEvent.ElementType.ARMOR ||
                event.getType() == RenderGameOverlayEvent.ElementType.SUBTITLES) {
            RenderSystem.pushMatrix();
            PlayerEntity player = (PlayerEntity) mc.getRenderViewEntity();
            if (player == null) return;
            if (!player.getCapability(CapabilityHelper.getMagicCapability()).isPresent()) return;
            if (CapabilityHelper.getCurrentLevel(player) < 0) return;
            if (player.isCreative() || player.isSpectator()) return;
            RenderSystem.translatef(0, -6f, 0);
        }
    }

    @SubscribeEvent
    public void nudge(RenderGameOverlayEvent.Post event) {
        if (event.getType() == RenderGameOverlayEvent.ElementType.EXPERIENCE ||
                event.getType() == RenderGameOverlayEvent.ElementType.HEALTH ||
                event.getType() == RenderGameOverlayEvent.ElementType.HEALTHMOUNT ||
                event.getType() == RenderGameOverlayEvent.ElementType.FOOD ||
                event.getType() == RenderGameOverlayEvent.ElementType.AIR ||
                event.getType() == RenderGameOverlayEvent.ElementType.ARMOR ||
                event.getType() == RenderGameOverlayEvent.ElementType.SUBTITLES) {
            RenderSystem.popMatrix();
        }
    }

    @SubscribeEvent
    public void render(RenderGameOverlayEvent.Pre event) {
        if (event.getType() != RenderGameOverlayEvent.ElementType.ALL) {
            return;
        }
        Entity renderViewEntity = mc.getRenderViewEntity();
        if (renderViewEntity == null)
            return;
        PlayerEntity player = (PlayerEntity) renderViewEntity;
        if (!player.getCapability(CapabilityHelper.getMagicCapability()).isPresent()) return;
        if (CapabilityHelper.getCurrentLevel(player) < 0) return;
        if (player.getHeldItem(Hand.MAIN_HAND).getItem().equals(ModItems.SPELL_BOOK.get()))
            renderSpellBook(player, Hand.MAIN_HAND);
        else if (player.getHeldItem(Hand.OFF_HAND).getItem().equals(ModItems.SPELL_BOOK.get()))
            renderSpellBook(player, Hand.OFF_HAND);
        if (player.isCreative() || player.isSpectator()) return;
        renderManaBar(player);
        renderBurnoutBar(player);
        renderMagicXp(player);
    }

    private void renderSpellBook(PlayerEntity player, Hand hand) {
        double scaledWidth = mc.getMainWindow().getScaledWidth();
        int scaledHeight = mc.getMainWindow().getScaledHeight();
        int spellBookUIWidth = 148;
        int spellBookUIHeight = 22;
        int spellBookUIX = (int) Math.round(0.5 * scaledWidth - 230);
        int spellBookUIY = scaledHeight + 3 - spellBookUIHeight;
        int activeSpellSize = 15;
        ItemStack bookStack = player.getHeldItem(hand);
        int bookSlot = ((SpellBookItem) bookStack.getItem()).getActiveSlot(bookStack);
        int x = spellBookUIX + bookSlot * 13;
        int y = spellBookUIY;
        mc.getTextureManager().bindTexture(SPELL_BOOK_UI_TEXTURE);
        RenderSystem.color4f(1, 1, 1, 1);
        setBlitOffset(-5);
        drawTexturedModalRect(spellBookUIX, spellBookUIY, 0, 0, 106, 15, spellBookUIWidth, spellBookUIHeight);
        List<ItemStack> activeScrolls = ((SpellBookItem) bookStack.getItem()).getActiveInventory(bookStack);
        for (int n = 0; n < 8; n++) {
            float iconX = spellBookUIX + 1.5f + n * 12.9f;
            ItemStack stackItem = activeScrolls.get(n);
            if (stackItem == null) continue;
            RenderSystem.pushMatrix();
            RenderSystem.translatef(iconX, spellBookUIY + 1.5f, getBlitOffset());
            RenderSystem.scalef(12f / 16f, 12f / 16f, 12f / 16f);
            mc.getItemRenderer().renderItemIntoGUI(stackItem, 0, 0);
            RenderSystem.popMatrix();
        }
        mc.getTextureManager().bindTexture(SPELL_BOOK_UI_TEXTURE);
        setBlitOffset(1000);
        drawTexturedModalRect(x, y, 148, 0, activeSpellSize, activeSpellSize, 20, 20);
        setBlitOffset(0);
    }

    private void renderMagicXp(PlayerEntity player) {
        int scaledWidth = mc.getMainWindow().getScaledWidth();
        int scaledHeight = mc.getMainWindow().getScaledHeight();
        RenderSystem.pushMatrix();
        RenderSystem.enableBlend();
        float x = 0;
        float y = 0;
        Vec2f position = new Vec2f(x, y);
        Vec2f dimensions = new Vec2f(182, 5);
        Minecraft.getInstance().getTextureManager().bindTexture(MC_TEXTURE);
        RenderSystem.color4f(0.5f, 0.5f, 1, 1);
        RenderSystem.translatef((scaledWidth - dimensions.x) / 2f, scaledHeight - 28.5f, 0);
        drawTexturedModalRect((int) position.x, (int) position.y, 0, 64, (int) dimensions.x, (int) dimensions.y, (int) dimensions.x, (int) dimensions.y);
        if (CapabilityHelper.getCurrentXP(player) > 0) {
            float pctXP = CapabilityHelper.getCurrentXP(player) / CapabilityHelper.getMaxXP(player);
            if (pctXP > 1) pctXP = 1;
            int width = (int) ((dimensions.x + 1) * pctXP);
            RenderSystem.color3f(ParticleUtil.getRed(0x99EEFF), ParticleUtil.getGreen(0x99EEFF), ParticleUtil.getBlue(0x99EEFF));
            drawTexturedModalRect((int) position.x, (int) position.y, 0, 69, width, (int) dimensions.y, width, (int) dimensions.y);
        }
        if (CapabilityHelper.getCurrentLevel(player) > 0) {
            String xpStr = String.format("%d", CapabilityHelper.getCurrentLevel(player));
            int width = Minecraft.getInstance().fontRenderer.getStringWidth(xpStr);
            float x1 = (182 - width) / 2f;
            float y1 = -2;
            Minecraft.getInstance().fontRenderer.drawString(xpStr, x1 + 1, y1, 0);
            Minecraft.getInstance().fontRenderer.drawString(xpStr, x1 - 1, y1, 0);
            Minecraft.getInstance().fontRenderer.drawString(xpStr, x1, y1 + 1, 0);
            Minecraft.getInstance().fontRenderer.drawString(xpStr, x1, y1 - 1, 0);
            Minecraft.getInstance().fontRenderer.drawString(xpStr, x1, y1, 0x99FFFF);
        }
        RenderSystem.popMatrix();
    }

    private void renderBurnoutBar(PlayerEntity player) {
        if (!player.getCapability(CapabilityHelper.getBurnoutCapability()).isPresent()) return;
        int scaledWidth = mc.getMainWindow().getScaledWidth();
        int scaledHeight = mc.getMainWindow().getScaledHeight();
        int xStart = scaledWidth / 2 + 121;
        int yStart = scaledHeight - 13;
        double burnout = CapabilityHelper.getBurnout(player);
        double maxBurnout = CapabilityHelper.getMaxBurnout(player);
        renderBar(xStart, yStart, burnout, maxBurnout, 0x880000, "burnout");
    }

    private void renderManaBar(PlayerEntity player) {
        if (!player.getCapability(CapabilityHelper.getManaCapability()).isPresent()) return;
        int scaledWidth = mc.getMainWindow().getScaledWidth();
        int scaledHeight = mc.getMainWindow().getScaledHeight();
        int xStart = scaledWidth / 2 + 121;
        int yStart = scaledHeight - 23;
        double mana = CapabilityHelper.getMana(player);
        double maxMana = CapabilityHelper.getMaxMana(player);
        renderBar(xStart, yStart, mana, maxMana, 0x99FFFF, "mana");
    }

    private void renderBar(int x, int y, double value, double maxValue, int color, String name) {
        mc.getProfiler().startSection(name);
        RenderSystem.pushMatrix();
        RenderSystem.enableBlend();
        mc.getTextureManager().bindTexture(BAR_TEXTURE);
        drawTexturedModalRect(x, y, 0, 0, 81, 9);
        float r = (color >> 16 & 0xFF) / 255f;
        float g = (color >> 8 & 0xFF) / 255f;
        float b = (color & 0xFF) / 255f;
        RenderSystem.color3f(r, g, b);
        drawTexturedModalRect(x + 2, y + 2, 2, 11, getWidth(value, maxValue) - 1, 7);
        RenderSystem.color4f(1, 1, 1, 1);
        RenderSystem.disableBlend();
        RenderSystem.popMatrix();
        mc.getProfiler().endSection();
    }

    public void drawTexturedModalRect(int x, int y, int textureX, int textureY, int width, int height) {
        mc.ingameGUI.blit(x, y, textureX, textureY, width, height);
    }

    public void drawTexturedModalRect(int dst_x, int dst_y, float src_x, float src_y, int dst_width, int dst_height, int src_width, int src_height) {
//        AbstractGui.blit(dst_x, dst_y, src_x, src_y, dst_width, dst_height, src_width, src_height);
//        Tessellator tessellator = Tessellator.getInstance();
//        BufferBuilder bufferbuilder = tessellator.getBuffer();
//        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
//        bufferbuilder.pos(dst_x, dst_y + dst_height, getBlitOffset()).tex(src_x, src_y + src_height).endVertex();
//        bufferbuilder.pos(dst_x + dst_width, dst_y + dst_height, getBlitOffset()).tex(src_x + src_width, src_y + src_height).endVertex();
//        bufferbuilder.pos(dst_x + dst_width, dst_y, getBlitOffset()).tex(src_x + src_width, src_y).endVertex();
//        bufferbuilder.pos(dst_x, dst_y, getBlitOffset()).tex(src_x, src_y).endVertex();
//        tessellator.draw();
        float var7 = 0.00390625F;
        float var8 = 0.00390625F;
        Tessellator var9 = Tessellator.getInstance();
        var9.getBuffer().begin(7, DefaultVertexFormats.POSITION_TEX);
        var9.getBuffer().pos(dst_x, dst_y + dst_height, getBlitOffset()).tex((src_x) * var7, (src_y + src_height) * var8).endVertex();
        var9.getBuffer().pos(dst_x + dst_width, dst_y + dst_height, getBlitOffset()).tex((src_x + src_width) * var7, (src_y + src_height) * var8).endVertex();
        var9.getBuffer().pos(dst_x + dst_width, dst_y, getBlitOffset()).tex((src_x + src_width) * var7, (src_y) * var8).endVertex();
        var9.getBuffer().pos(dst_x, dst_y, getBlitOffset()).tex((src_x) * var7, (src_y) * var8).endVertex();
        var9.draw();
    }

    private float getBlitOffset() {
        return blitOffset;
    }

    private void setBlitOffset(float blitOffset) {
        this.blitOffset = blitOffset;
    }

    public int getStringLength(String s) {
        return fontRender().getStringWidth(s);
    }

    public void drawStringOnHUD(String string, int xOffset, int yOffset, int color) {
        xOffset += 2;
        yOffset += 2;
        fontRender().drawStringWithShadow(string, xOffset, yOffset, color);
    }
}
