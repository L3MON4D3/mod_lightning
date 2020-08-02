package com.L3MON4D3.lightningmod.entity;

import com.L3MON4D3.lightningmod.init.ModEntityTypes;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.IPacket;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.NetworkHooks;

public class LightningArrowEntity extends AbstractArrowEntity {
    private boolean duringTick = false;
    private boolean lightningFired = false;

    public LightningArrowEntity(World worldIn, LivingEntity shooter) {
        super(ModEntityTypes.LIGHTNING_ARROW.get(), shooter, worldIn);
    }

    public LightningArrowEntity(EntityType<? extends LightningArrowEntity> type, World worldIn) {
        super(type, worldIn);
    }

    public LightningArrowEntity(World worldIn, double x, double y, double z) {
        super(ModEntityTypes.LIGHTNING_ARROW.get(), x, y, z, worldIn);
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
        if (!world.isRemote) {
            if (!lightningFired && !isInWater()) {
                BlockPos pos = getPosition();
                double x = pos.getX();
                double y = pos.getY();
                double z = pos.getZ();

                while (pos.getY() > 255) {
                    if (!world.isAirBlock(pos.up()))
                        if (pos.getY() - y < 8) {
                            y = pos.getY();
                            break;
                        } else {
                           return;
                        }
                }
                ((ServerWorld) world).addLightningBolt(new LightningBoltEntity(
                    world, x, y, z, false));
                remove();
            }
        }
    }

    @Override
    public void onHit(RayTraceResult res) {
        super.onHit(res);
        strike(res);
    }

    //Hackish way to turn off Crit-particles, may intefere with damage calc.
    @Override
    public void tick() {
        duringTick = true;
        super.tick();
        duringTick = false;

        if (world.isRemote) {
            Vec3d motion = getMotion();
            world.addParticle(ParticleTypes.POOF,
                getPosX(), getPosY(), getPosZ(),
                motion.x, motion.y, motion.z);
        }
    }

    @Override
    public boolean getIsCritical() {
        return !duringTick && super.getIsCritical();
    }

    @Override
    protected void onEntityHit(EntityRayTraceResult res) {
        super.onEntityHit(res);
        strike(res);
    }

    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
