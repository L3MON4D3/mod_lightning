package com.L3MON4D3.lightningmod.tileentity;

import javax.annotation.Nonnull;

import com.L3MON4D3.lightningmod.container.LightningConductorContainer;
import com.L3MON4D3.lightningmod.init.ModBlocks;
import com.L3MON4D3.lightningmod.init.ModItems;
import com.L3MON4D3.lightningmod.init.ModTileEntities;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.items.ItemStackHandler;

public class LightningConductorTileEntity extends TileEntity implements INamedContainerProvider {
    public static final int SLOT = 0;
    public static final String INVENTORY_TAG= "inventory";
    public final ItemStackHandler inventory = new ItemStackHandler(1) {
		@Override
		public boolean isItemValid(final int slot, @Nonnull final ItemStack stack) {
            Item stackItem = stack.getItem();
            return stackItem == Items.GLASS_BOTTLE || stackItem == ModItems.LIGHTNING_BOTTLE.get();
		}

        @Override
        protected int getStackLimit(int slot, ItemStack stack) {
            //Only one Slot, can only add GlassBottles.
            return 1;
        }

		@Override
		protected void onContentsChanged(final int slot) {
			super.onContentsChanged(slot);
			// Mark the tile entity as having changed whenever its inventory changes.
			// "markDirty" tells vanilla that the chunk containing the tile entity has
			// changed and means the game will save the chunk to disk later.
			markDirty();
		}
	};

    public LightningConductorTileEntity() {
        super(ModTileEntities.LIGHTNING_CONDUCTOR.get());
    }

    @Override
    public Container createMenu(int winId, PlayerInventory inv, PlayerEntity arg2) {
        return new LightningConductorContainer(winId, inv, this);
    }

    /**
     * If Entity holds GlassBottle (<=> isnt empty), replace it with LightningBottleItem.
     */
    public void charge() {
        if (!inventory.getStackInSlot(0).isEmpty()) {
            inventory.extractItem(0, 1, false);
            inventory.insertItem(0, new ItemStack(ModItems.LIGHTNING_BOTTLE.get(), 1), false);
        }
    }

	@Override
	public ITextComponent getDisplayName() {
		return new TranslationTextComponent(ModBlocks.LIGHTNING_CONDUCTOR.get().getTranslationKey());
	}

	/**
	 * Read saved data from disk into the tile.
	 */
	@Override
	public void read(final CompoundNBT compound) {
		super.read(compound);
		this.inventory.deserializeNBT(compound.getCompound(INVENTORY_TAG));
	}

	/**
	 * Write data from the tile into a compound tag for saving to disk.
	 */
	@Nonnull
	@Override
	public CompoundNBT write(final CompoundNBT compound) {
		super.write(compound);
		compound.put(INVENTORY_TAG, this.inventory.serializeNBT());
		return compound;
	}
}
