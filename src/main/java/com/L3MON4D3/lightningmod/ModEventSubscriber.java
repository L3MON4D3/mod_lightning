package com.L3MON4D3.lightningmod;

import com.L3MON4D3.lightningmod.init.ModBlocks;
import com.L3MON4D3.lightningmod.init.ModItemGroups;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.registries.IForgeRegistry;

@EventBusSubscriber(modid = LightningMod.MODID, bus = EventBusSubscriber.Bus.MOD)
public class ModEventSubscriber {
    private static final Logger LOGGER = LogManager.getLogger(LightningMod.MODID);

    /**
	 * This method will be called by Forge when it is time for the mod to register its Items.
	 * This method will always be called after the Block registry method.
	 */
    @SubscribeEvent
	public static void onRegisterItems(final RegistryEvent.Register<Item> event) {
        LOGGER.debug("Registering BlockItems.");
        final IForgeRegistry<Item> registry = event.getRegistry();
		// Automatically register BlockItems for all our Blocks
		ModBlocks.BLOCKS.getEntries().stream()
				.map(RegistryObject::get)
				.forEach(block -> {
					final Item.Properties properties =
                        new Item.Properties().group(ModItemGroups.MOD_ITEM_GROUP);
					// Create the new BlockItem with the block and it's properties
					final BlockItem blockItem = new BlockItem(block, properties);
					// Set the new BlockItem's registry name to the block's registry name
					blockItem.setRegistryName(block.getRegistryName());
					// Register the BlockItem
					registry.register(blockItem);
				});
	}
}
