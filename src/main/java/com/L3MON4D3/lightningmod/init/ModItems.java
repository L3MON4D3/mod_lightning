package com.L3MON4D3.lightningmod.init;

import com.L3MON4D3.lightningmod.LightningMod;
import com.L3MON4D3.lightningmod.item.LightningArrowItem;

import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
        DeferredRegister.create(ForgeRegistries.ITEMS, LightningMod.MODID);
    public static final String LIGHTNING_ARROW_NAME = "lightning_arrow";
    public static final RegistryObject<LightningArrowItem> LIGHTNING_ARROW =
        ITEMS.register("lightning_arrow",
            () -> new LightningArrowItem(
                new Item.Properties().group(ModItemGroups.MOD_ITEM_GROUP)));
}
