package com.L3MON4D3.lightningmod.tileentity;

import javax.annotation.Nonnull;

import com.L3MON4D3.lightningmod.container.LightningConductorContainer;
import com.L3MON4D3.lightningmod.init.ModBlocks;
import com.L3MON4D3.lightningmod.init.ModTileEntities;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.items.ItemStackHandler;

public class LightningConductorTileEntity extends TileEntity implements INamedContainerProvider {
    public static final int SLOT = 0;
    public final ItemStackHandler inventory = new ItemStackHandler(1) {
		@Override
		public boolean isItemValid(final int slot, @Nonnull final ItemStack stack) {
            return stack.getItem() == Items.GLASS_BOTTLE;
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

	@Override
	public ITextComponent getDisplayName() {
		return new TranslationTextComponent(ModBlocks.LIGHTNING_CONDUCTOR.get().getTranslationKey());
	}

}
