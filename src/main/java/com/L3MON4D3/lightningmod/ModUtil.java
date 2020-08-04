package com.L3MON4D3.lightningmod;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ModUtil {
    /**
     * Search all HighestBlocks in Circle around center, if one is block, return it.
     * @param center Center of Circle.
     * @param radius Radius of Search circle.
     * @param block Which Block to search for.
     * @param w World.
     * @return Found block, if not there null.
     * */
    public static BlockPos getOneBlockHighestZ(
    BlockPos center, int radius, Block block, World w) {
        int searchStartX = center.getX() - 10;
        int searchStopX = center.getX() + 10;
        int searchStartZ = center.getZ() - 10;
        int searchStopZ = center.getZ() + 10;

        for (int x = searchStartX; x != searchStopX+1; x++)
            for (int z = searchStartZ; z != searchStopZ+1; z++) {
                //Find highest Block
                BlockPos pos = new BlockPos(x, 255, z);
                while (w.isAirBlock(pos))
                    pos = pos.down();
                BlockState bs = w.getBlockState(pos);
                if (bs.getBlock() == block)
                    return pos;
            }
        return null;
    }
}
