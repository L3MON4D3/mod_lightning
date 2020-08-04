package com.L3MON4D3.lightningmod.entity;

import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.world.World;

public class ArtificialLightningBoltEntity extends LightningBoltEntity {

    public ArtificialLightningBoltEntity(World w, double x, double y, double z, boolean effectOnlyIn) {
        super(w, x, y, z, effectOnlyIn);
    }
}
