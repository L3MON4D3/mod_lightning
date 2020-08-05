package com.L3MON4D3.lightningmod.container;

import java.util.Objects;

import com.L3MON4D3.lightningmod.init.ModBlocks;
import com.L3MON4D3.lightningmod.init.ModContainerTypes;
import com.L3MON4D3.lightningmod.tileentity.LightningConductorTileEntity;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IWorldPosCallable;
import net.minecraftforge.items.SlotItemHandler;

public class LightningConductorContainer extends Container {
    private IWorldPosCallable canInteractWithCallable;

    public LightningConductorContainer(final int windowId,
    final PlayerInventory playerInventory, final PacketBuffer data) {
        this(windowId, playerInventory, getTileEntity(playerInventory, data));
    }

    public LightningConductorContainer(final int windowId,
    final PlayerInventory playerInventory, final LightningConductorTileEntity tileEntity) {
        super(ModContainerTypes.LIGHTNING_CONDUCTOR.get(), windowId);
        this.canInteractWithCallable =
            IWorldPosCallable.of(tileEntity.getWorld(), tileEntity.getPos());
        this.addSlot(new SlotItemHandler(tileEntity.inventory,
            LightningConductorTileEntity.SLOT, 80, 34));
		final int playerInventoryStartX = 8;
		final int playerInventoryStartY = 84;
		final int slotSizePlus2 = 18; // slots are 16x16, plus 2 (for spacing/borders) is 18x18

		// Player Top Inventory slots
		for (int row = 0; row < 3; ++row) {
			for (int column = 0; column < 9; ++column) {
				this.addSlot(new Slot(playerInventory, 9 + (row * 9) + column, playerInventoryStartX + (column * slotSizePlus2), playerInventoryStartY + (row * slotSizePlus2)));
			}
		}

		final int playerHotbarY = playerInventoryStartY + slotSizePlus2 * 3 + 4;
		// Player Hotbar slots
		for (int column = 0; column < 9; ++column) {
			this.addSlot(new Slot(playerInventory, column, playerInventoryStartX + (column * slotSizePlus2), playerHotbarY));
		}
    }

    /**
     * Create new LightningConductorTileEntity.
     * @param pi Inventory.
     * @param data Data.
     * @return LightningConductorTileEntity.
     */
    private static LightningConductorTileEntity getTileEntity(PlayerInventory pi, PacketBuffer data) {
        Objects.requireNonNull(pi, "playerInventory cannot be null!");
		Objects.requireNonNull(data, "data cannot be null!");
		final TileEntity tileAtPos = pi.player.world.getTileEntity(data.readBlockPos());
		if (tileAtPos instanceof LightningConductorTileEntity)
			return (LightningConductorTileEntity) tileAtPos;
		throw new IllegalStateException("Tile entity is not correct! " + tileAtPos);
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return isWithinUsableDistance(canInteractWithCallable, playerIn,
            ModBlocks.LIGHTNING_CONDUCTOR.get());
	}

	@Override
	public ItemStack transferStackInSlot(final PlayerEntity player, final int index) {
		ItemStack returnStack = ItemStack.EMPTY;
		final Slot slot = this.inventorySlots.get(index);
		if (slot != null && slot.getHasStack()) {
			final ItemStack slotStack = slot.getStack();
			returnStack = slotStack.copy();

			final int containerSlots = this.inventorySlots.size() - player.inventory.mainInventory.size();
			if (index < containerSlots) {
				if (!mergeItemStack(slotStack, containerSlots, this.inventorySlots.size(), true)) {
					return ItemStack.EMPTY;
				}
			} else {
                if (slotStack.getItem() == Items.GLASS_BOTTLE) {
                    Slot lccSlot = inventorySlots.get(0);
                    if (lccSlot.getStack().getCount() == 0) {
                        //reduces slotStack by one.
                        lccSlot.putStack(slotStack.split(1));
                        slot.putStack(slotStack);
                    }
                }
                return ItemStack.EMPTY;
            }
			if (slotStack.getCount() == 0) {
				slot.putStack(ItemStack.EMPTY);
			} else {
				slot.onSlotChanged();
			}
			if (slotStack.getCount() == returnStack.getCount()) {
				return ItemStack.EMPTY;
			}
			slot.onTake(player, slotStack);
		}
		return returnStack;
	}
}
