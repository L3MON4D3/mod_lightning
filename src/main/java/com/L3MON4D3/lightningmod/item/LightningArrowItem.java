package com.L3MON4D3.lightningmod.item;

import com.L3MON4D3.lightningmod.entity.LightningArrowEntity;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LightningArrowItem extends ArrowItem {

    public LightningArrowItem(Properties builder) {
        super(builder);
    }

    @Override
    public AbstractArrowEntity createArrow(World worldIn, ItemStack stack, LivingEntity shooter) {
        return new LightningArrowEntity(worldIn, shooter);
    }
}
