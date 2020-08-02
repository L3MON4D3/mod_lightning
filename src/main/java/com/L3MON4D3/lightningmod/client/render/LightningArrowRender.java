package com.L3MON4D3.lightningmod.client.render;

import com.L3MON4D3.lightningmod.LightningMod;
import com.L3MON4D3.lightningmod.entity.LightningArrowEntity;

import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;

public class LightningArrowRender extends ArrowRenderer<LightningArrowEntity> {
    private static final ResourceLocation LIGHTNING_ARROW_TEXTURE =
        new ResourceLocation(LightningMod.MODID, "textures/entity/lightning_arrow.png");

    public LightningArrowRender(final EntityRendererManager man) {
        super(man);
    }

    @Override
    public ResourceLocation getEntityTexture(final LightningArrowEntity entity) {
        return LIGHTNING_ARROW_TEXTURE;
    }
}
