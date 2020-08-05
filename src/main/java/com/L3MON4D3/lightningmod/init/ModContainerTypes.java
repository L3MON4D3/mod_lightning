package com.L3MON4D3.lightningmod.init;

import com.L3MON4D3.lightningmod.LightningMod;
import com.L3MON4D3.lightningmod.container.LightningConductorContainer;

import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public final class ModContainerTypes {

	public static final DeferredRegister<ContainerType<?>> CONTAINER_TYPES = DeferredRegister.create(ForgeRegistries.CONTAINERS, LightningMod.MODID);

	public static final RegistryObject<ContainerType<LightningConductorContainer>> LIGHTNING_CONDUCTOR =
        CONTAINER_TYPES.register("lightning_conductor", () -> IForgeContainerType.create(LightningConductorContainer::new));
}

