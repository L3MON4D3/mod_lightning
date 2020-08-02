package com.L3MON4D3.lightningmod.init;

import com.L3MON4D3.lightningmod.LightningMod;
import com.L3MON4D3.lightningmod.block.LightningConductorBlock;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
        DeferredRegister.create(ForgeRegistries.BLOCKS, LightningMod.MODID);

    public static final RegistryObject<Block> LIGHTNING_CONDUCTOR = 
        BLOCKS.register("lightning_conductor",
            () -> new LightningConductorBlock(Block.Properties.create(Material.IRON)));
}
