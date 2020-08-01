package com.L3MON4D3.lightningmod.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class LightningArrowEntity extends ArrowEntity {
	private ServerWorld world;
    private boolean lightningFired = false;

    public LightningArrowEntity(World worldIn, LivingEntity shooter) {
        super(worldIn, shooter);
        world = (ServerWorld) worldIn;
    }

    public LightningArrowEntity(EntityType<? extends ArrowEntity> type, World worldIn) {
        super(type, worldIn);
    }

    @Override
    protected ItemStack getArrowStack() {
        return new ItemStack(Items.AIR, 0);
    }

    /**
     * Strike position of res with Lightning.
     * @param res RayTraceResult that describes position of hit, obtain via on(Entity)Hit.
     **/
    private void strike(RayTraceResult res) {
        if (!lightningFired) {
            Vec3d hitVec = res.getHitVec();
            world.addLightningBolt(new LightningBoltEntity(
                world, hitVec.x, hitVec.y, hitVec.z, false));
        }
        lightningFired = true;
        remove();
    }

    public void onHit(RayTraceResult res) {
        super.onHit(res);
        strike(res);
    }

    @Override
    protected void onEntityHit(EntityRayTraceResult res) {
        super.onEntityHit(res);
        strike(res);
    }
}
