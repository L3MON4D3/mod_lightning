package com.L3MON4D3.lightningmod.server;

import com.L3MON4D3.lightningmod.LightningMod;
import com.L3MON4D3.lightningmod.ModUtil;
import com.L3MON4D3.lightningmod.entity.ArtificialLightningBoltEntity;
import com.L3MON4D3.lightningmod.init.ModBlocks;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(modid = LightningMod.MODID)
public class ServerModEventSubscriber {
    private static final Logger LOGGER = LogManager.getLogger(LightningMod.MODID);

    /**
     * Redirect lightning if a LightningConductorBlock is near.
     * @param event Event.
     */
    @SubscribeEvent
    public static void onLightningStrike(EntityJoinWorldEvent event) {
        Entity en = event.getEntity();
        if (en instanceof LightningBoltEntity &&
            ! (en instanceof ArtificialLightningBoltEntity)) {
            World w = en.world;
            LightningBoltEntity lb = (LightningBoltEntity) en;
            BlockPos pos = lb.getPosition();
            LOGGER.debug(pos);
        
            BlockPos bp = ModUtil.getOneBlockHighestZ(
                pos, 10, ModBlocks.LIGHTNING_CONDUCTOR.get(), w);
            LOGGER.debug(bp);
            if (bp != null) {
                //hit middle of Block.
                lb.setPosition(bp.getX()+.5, bp.getY()+.5, bp.getZ()+.5);
            }
        }
    }
}
