package com.L3MON4D3.lightningmod.block;

import com.L3MON4D3.lightningmod.LightningMod;
import com.L3MON4D3.lightningmod.init.ModTileEntities;
import com.L3MON4D3.lightningmod.tileentity.LightningConductorTileEntity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class LightningConductorBlock extends HorizontalBlock {
    public static final Logger LOGGER = LogManager.getLogger(LightningMod.MODID);

   public LightningConductorBlock(Block.Properties builder) {
      super(builder);
   }

    @Override
    public ActionResultType onBlockActivated(final BlockState state, final World worldIn,
    final BlockPos pos, final PlayerEntity player, final Hand handIn,
    final BlockRayTraceResult hit) {
        if (!worldIn.isRemote) {
			final TileEntity tileEntity = worldIn.getTileEntity(pos);
			if (tileEntity instanceof LightningConductorTileEntity)
			    NetworkHooks.openGui((ServerPlayerEntity) player, (LightningConductorTileEntity) tileEntity, pos);
		}
		return ActionResultType.SUCCESS;
    }

    @Override
    public boolean hasTileEntity(final BlockState state) {
        return true;
    }

    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return ModTileEntities.LIGHTNING_CONDUCTOR.get().create();
    }
}
