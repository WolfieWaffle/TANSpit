package com.github.wolfiewaffle.tanspit.tileentity;

import javax.annotation.Nullable;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import toughasnails.api.TANBlocks;
import toughasnails.block.BlockTANCampfire;

public class TileEntitySpit extends TileEntity implements ITickable {
	public int cookTimeRemaining = 700;
	private static final int totalTime = 700;

	public ItemStackHandler items = new ItemStackHandler(3) {
		@Override
		protected int getStackLimit(int slot, ItemStack stack) {
			return 1;
		}

		// @formatter:off
		@Override
		public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
			if ((stack != null) && ((stack.getItem() instanceof ItemFood)) && 
					(FurnaceRecipes.instance().getSmeltingResult(stack) != null) && 
					(FurnaceRecipes.instance().getSmeltingResult(stack).getItem() instanceof ItemFood)) {
				return super.insertItem(slot, stack, simulate);
			}
			return stack;
		}
		// @formatter:on

		// Unsure what this is for
		@Override
		protected void onContentsChanged(int slot) {
			super.onContentsChanged(slot);
			TileEntitySpit.this.markDirty();

			IBlockState state = world.getBlockState(pos);
			world.notifyBlockUpdate(pos, state, state, 3);
		}
	};

	// Unsure what this is for
	@Override
	public SPacketUpdateTileEntity getUpdatePacket() {
		NBTTagCompound tag = new NBTTagCompound();
		writeToNBT(tag);
		return new SPacketUpdateTileEntity(pos, 0, tag);
	}

	// Unsure what this is for
	@Override
	public NBTTagCompound getUpdateTag() {
		return this.writeToNBT(new NBTTagCompound());
	}

	// Unsure what this is for
	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity packet) {
		super.onDataPacket(net, packet);
		readFromNBT(packet.getNbtCompound());

		IBlockState state = world.getBlockState(pos);
		world.notifyBlockUpdate(pos, state, state, 3);
	}

	private boolean isCooking() {
		IBlockState down = world.getBlockState(pos.down());

		if (down.getBlock() == TANBlocks.campfire) {
			if (down.getValue(BlockTANCampfire.BURNING)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void update() {

		// Only update on server
		if (!world.isRemote) {
			if (isCooking() && canCookAnything()) {
				cookTimeRemaining--;

				// Time has reached 0
				if (cookTimeRemaining <= 0) {
					for (int i = 0; i < inventory().getSlots(); i++) {

						// Attempt to cook items
						ItemStack stack = items.getStackInSlot(i);

						if (!getCookingResult(stack).isEmpty() && !stack.isEmpty()) {
							items.setStackInSlot(i, getCookingResult(stack));
						}
					}
					cookTimeRemaining = totalTime;
				}
				markDirty();
			} else {

				// Nothing to cook, cool down
				if (cookTimeRemaining < totalTime) {
					cookTimeRemaining++;
					markDirty();
				}
			}
		}
	}

	public static ItemStack getCookingResult(ItemStack stack) {
		return FurnaceRecipes.instance().getSmeltingResult(stack).copy();
	}

	private boolean canCookAnything() {
		for (int i = 0; i < inventory().getSlots(); i++) {
			if (!FurnaceRecipes.instance().getSmeltingResult(items.getStackInSlot(i)).copy().isEmpty()) {
				return true;
			}
		}
		return false;
	}

	// Capabilities
	@Override
	public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
		if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
			return true;
		return super.hasCapability(capability, facing);
	}

	// Capabilities
	@SuppressWarnings("unchecked")
	@Override
	public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
		if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
			return (T) items;
		return super.getCapability(capability, facing);
	}

	/*public boolean isUseableByPlayer(EntityPlayer player) {
		return world.getTileEntity(pos) == this
				&& player.getDistanceSq(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D) <= 64.0D;
	}*/

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound = super.writeToNBT(compound);
		compound.setTag("Items", items.serializeNBT());
		compound.setInteger("CookTime", (short)this.cookTimeRemaining);
		return compound;
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		items.deserializeNBT(compound.getCompoundTag("Items"));
		cookTimeRemaining = compound.getInteger("CookTime");
	}

	public IItemHandler inventory() {
		return items;
	}
	
}