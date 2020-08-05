package com.L3MON4D3.lightningmod;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import com.L3MON4D3.lightningmod.init.ModBlocks;
import com.L3MON4D3.lightningmod.init.ModContainerTypes;
import com.L3MON4D3.lightningmod.init.ModEntityTypes;
import com.L3MON4D3.lightningmod.init.ModItems;
import com.L3MON4D3.lightningmod.init.ModTileEntities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(LightningMod.MODID)
public class LightningMod {
    public static final String MODID = "lightningmod";
    public static final Logger LOGGER = LogManager.getLogger(MODID);

    /**
     * Constructor, register all Entities, Items and Blocks.
     */
    public LightningMod() {
        LOGGER.debug("Hello from LightningMod");
        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModItems.ITEMS.register(modEventBus);
        ModBlocks.BLOCKS.register(modEventBus);
        ModEntityTypes.ENTITY_TYPES.register(modEventBus);
        ModContainerTypes.CONTAINER_TYPES.register(modEventBus);
        ModTileEntities.TILE_ENTITY_TYPES.register(modEventBus);
    }
}
