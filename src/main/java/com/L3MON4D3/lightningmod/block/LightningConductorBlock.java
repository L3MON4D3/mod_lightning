package com.L3MON4D3.lightningmod.block;

import java.util.stream.Stream;

import com.L3MON4D3.lightningmod.LightningMod;
import com.L3MON4D3.lightningmod.init.ModTileEntities;
import com.L3MON4D3.lightningmod.tileentity.LightningConductorTileEntity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class LightningConductorBlock extends Block {
    public static final Logger LOGGER = LogManager.getLogger(LightningMod.MODID);
    
    private static final VoxelShape SHAPE = Stream.of(
        Block.makeCuboidShape(7, 5, 7, 9, 10, 9),
        Block.makeCuboidShape(3, 3, 3, 13, 5, 13),
        Block.makeCuboidShape(0, 0, 0, 16, 3, 16)
    ).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get();

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
	public VoxelShape getShape(final BlockState state, final IBlockReader worldIn,
    final BlockPos pos, final ISelectionContext context) {
		return SHAPE;
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
