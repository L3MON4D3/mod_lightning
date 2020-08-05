package com.L3MON4D3.lightningmod.client.gui;

import com.L3MON4D3.lightningmod.LightningMod;
import com.L3MON4D3.lightningmod.container.LightningConductorContainer;
import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class LightningConductorScreen extends ContainerScreen<LightningConductorContainer> {
    private static final ResourceLocation BACKGROUND_TEXTURE = new ResourceLocation(LightningMod.MODID, "textures/gui/container/lightning_conductor.png");


    public LightningConductorScreen(final LightningConductorContainer container,
    final PlayerInventory inventory, final ITextComponent title) {
        super(container, inventory, title);
    }

    @Override
    public void render(final int mouseX, final int mouseY, final float partialTicks) {
		this.renderBackground();
		super.render(mouseX, mouseY, partialTicks);
		this.renderHoveredToolTip(mouseX, mouseY);
    }

	@Override
	protected void drawGuiContainerBackgroundLayer(
    final float partialTicks, final int mouseX, final int mouseY) {
		RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		getMinecraft().getTextureManager().bindTexture(BACKGROUND_TEXTURE);

		int startX = this.guiLeft;
		int startY = this.guiTop;

		// Screen#blit draws a part of the current texture (assumed to be 256x256) to the screen
		// The parameters are (x, y, u, v, width, height)

		this.blit(startX, startY, 0, 0, this.xSize, this.ySize);
    }
}
