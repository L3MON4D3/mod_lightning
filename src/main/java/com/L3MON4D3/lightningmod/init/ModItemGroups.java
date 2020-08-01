package com.L3MON4D3.lightningmod.init;

import java.util.function.Supplier;

import com.L3MON4D3.lightningmod.LightningMod;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class ModItemGroups {
    public static final ItemGroup MOD_ITEM_GROUP =
        new ModItemGroup(LightningMod.MODID, () -> new ItemStack(ModItems.LIGHTNING_ARROW.get()));

    public static class ModItemGroup extends ItemGroup {
        Supplier<ItemStack> iconSupplier;

        public ModItemGroup(String name, Supplier<ItemStack> iconSupplier) {
            super(name);
            this.iconSupplier = iconSupplier;
        }

        @Override
        public ItemStack createIcon() {
            return iconSupplier.get();
        }
    }
}
