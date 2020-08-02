package com.L3MON4D3.lightningmod.init;

import com.L3MON4D3.lightningmod.LightningMod;
import com.L3MON4D3.lightningmod.entity.LightningArrowEntity;

import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModEntityTypes {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
        DeferredRegister.create(ForgeRegistries.ENTITIES, LightningMod.MODID);
    public static final RegistryObject<EntityType<LightningArrowEntity>> LIGHTNING_ARROW =
        ENTITY_TYPES.register(ModItems.LIGHTNING_ARROW_NAME, () -> 
            EntityType.Builder
                .<LightningArrowEntity>create(LightningArrowEntity::new, EntityClassification.MISC)
                .size(.5f, .5f)
                .build(LightningMod.MODID + ModItems.LIGHTNING_ARROW_NAME));
}
