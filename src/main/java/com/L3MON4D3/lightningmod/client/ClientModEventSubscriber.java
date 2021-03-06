package com.L3MON4D3.lightningmod.client;

import com.L3MON4D3.lightningmod.LightningMod;
import com.L3MON4D3.lightningmod.client.gui.LightningConductorScreen;
import com.L3MON4D3.lightningmod.client.render.LightningArrowRender;
import com.L3MON4D3.lightningmod.init.ModContainerTypes;
import com.L3MON4D3.lightningmod.init.ModEntityTypes;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.client.gui.ScreenManager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DeferredWorkQueue;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@EventBusSubscriber(modid = LightningMod.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientModEventSubscriber {
    private static final Logger LOGGER =
        LogManager.getLogger(LightningMod.MODID);

    /**
     * Link Entities to Render.
     * @param FMLClientSetupEvent 
     */
    @SubscribeEvent
    public static void onFMLClientSetupEvent(final FMLClientSetupEvent event) {
        RenderingRegistry.registerEntityRenderingHandler(
            ModEntityTypes.LIGHTNING_ARROW.get(), LightningArrowRender::new);
        LOGGER.debug("Registered Renderer for Entities.");

        DeferredWorkQueue.runLater(() -> {
            ScreenManager.registerFactory(ModContainerTypes.LIGHTNING_CONDUCTOR.get(),
                LightningConductorScreen::new);
            LOGGER.debug("Register GUI.");
        });
    }
}
