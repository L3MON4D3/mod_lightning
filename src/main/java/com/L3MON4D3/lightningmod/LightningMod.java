package com.L3MON4D3.lightningmod;

import net.minecraftforge.fml.common.Mod;
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
    }
}
