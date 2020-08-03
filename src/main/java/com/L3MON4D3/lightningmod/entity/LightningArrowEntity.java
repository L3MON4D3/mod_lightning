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
import net.minecraft.util.math.MathHelper;
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
     * Strike position of vec with Lightning.
     * @param vec Coords to hit.
     **/
    private void strike(Vec3d vec) {
        if (vec != null) {
            LOGGER.debug(vec);
            ((ServerWorld) world).addLightningBolt(new LightningBoltEntity(
                world, vec.x, vec.y, vec.z, false));
            remove();
        }
    }

    @Override
    public void onHit(RayTraceResult res) {
        super.onHit(res);
        if (!world.isRemote)
            if (!lightningFired && !isInWater()) {
                Vec3d strikePos = null;
                Vec3d vec = res.getHitVec();
                if(res.getType() == RayTraceResult.Type.BLOCK)
                    //move Arrow slightly inside Block so that the correct Block is found later on.
                    strikePos =
                        getStrikePosBlock(vec.add(getMotion().normalize().mul(.1, .1, .1)));
                else
                    strikePos = getStrikePosEntity(vec);
                strike(strikePos);
            }
    }

    /**
     * Calculate Block to Actually be struck with Lightning; eg. if Blocks are
     * above the hit Block, hit those (Or, if too far away, dont hit at all).
     * @param res RayTraceResult, pass from onHit().
     * @return Vec3d to strike at.
     */
    public Vec3d getStrikePosBlock(Vec3d vec) {
        BlockPos pos = new BlockPos(vec);

        double origY = vec.y;
        //test all Blocks above hit block.
        while (pos.getY() < 255) {
            pos = pos.up();
            if (world.getBlockState(pos).isSolid()) {
                double diff = pos.getY() - origY;
                if (diff < 8) {
                    //set new target Block, +1 so lightning strikes roof of block.
                    vec = new Vec3d(vec.x, pos.getY(), vec.z);
                } else {
                   return null;
                }
            }
        }
        //Hit top of Block:
        //If new Block was calc in loop(+1).
        //If the original vec was used(floor(y+1)).
        return new Vec3d(vec.x, MathHelper.floor(vec.y+1), vec.z);
    }

    /**
     * Calculate Block to Actually be struck with Lightning;
     * Seach first Block under Entity that is solid, then apply
     * getStrikePosBlock()  to those coords.
     * @param res RayTraceResult, pass from onHit().
     * @return Vec3d to strike at.
     */
    public Vec3d getStrikePosEntity(Vec3d vec) {
        //no need to move vec, pos should be well inside block.
        BlockPos pos = new BlockPos(vec);

        int offset = 0;
        //Find first solid block beneath target
        while (!world.getBlockState(pos).isSolid()) {
            pos = pos.down();
            offset--;
        }

        //Use Block-logic on First solid block under target.
        return getStrikePosBlock(vec.add(0, offset, 0));
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
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
