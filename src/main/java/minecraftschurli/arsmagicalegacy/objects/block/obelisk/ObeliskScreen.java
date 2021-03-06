package minecraftschurli.arsmagicalegacy.objects.block.obelisk;

import minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import org.lwjgl.opengl.GL14;

/**
 * @author Minecraftschurli
 * @version 2020-04-21
 */
public class ObeliskScreen extends ContainerScreen<ObeliskContainer> {
    private static final ResourceLocation background = new ResourceLocation(ArsMagicaAPI.MODID, "textures/gui/obelisk_gui.png");

    public ObeliskScreen(ObeliskContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
        super(screenContainer, inv, titleIn);
        xSize = 176;
        ySize = 165;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        this.renderBackground(0);
        Minecraft.getInstance().getTextureManager().bindTexture(background);
        GL14.glColor4f(1, 1, 1, 1);
        int i = (width - xSize) / 2;
        int j = (height - ySize) / 2;
        blit(i, j, 0, 0, xSize, ySize);
        int overlayHeight = this.container.getCookProgressScaled(14);
        if (overlayHeight > 0)
            this.blit(i + 79, j + 30 + 14 - overlayHeight, 176, 14 - overlayHeight, 14, overlayHeight + 2);
    }
}
