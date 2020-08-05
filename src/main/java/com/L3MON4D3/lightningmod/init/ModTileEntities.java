package com.L3MON4D3.lightningmod.init;

import com.L3MON4D3.lightningmod.LightningMod;
import com.L3MON4D3.lightningmod.tileentity.LightningConductorTileEntity;

import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModTileEntities {
	public static final DeferredRegister<TileEntityType<?>> TILE_ENTITY_TYPES =
        DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, LightningMod.MODID);

	public static final RegistryObject<TileEntityType<LightningConductorTileEntity>>
        LIGHTNING_CONDUCTOR = TILE_ENTITY_TYPES.register("lightning_conductor", () ->
            TileEntityType.Builder.create(LightningConductorTileEntity::new,
                ModBlocks.LIGHTNING_CONDUCTOR.get()).build(null)
	    );
}
