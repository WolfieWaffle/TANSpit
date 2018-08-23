package com.github.wolfiewaffle.tanspit.blocks;

import java.util.Random;

import com.github.wolfiewaffle.tanspit.TANSpit;
import com.github.wolfiewaffle.tanspit.tileentity.TileEntitySpit;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.items.IItemHandler;
import toughasnails.api.TANBlocks;

public class BlockSpit extends BlockContainer {

	public BlockSpit(String name) {
		super(Material.ANVIL);
		this.setCreativeTab(CreativeTabs.MISC);
		this.setTickRandomly(true);
		this.setRegistryName(name);
		this.setUnlocalizedName(name);
		this.setLightOpacity(0);
		this.fullBlock = false;
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 0.25, 1.0);
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return NULL_AABB;
	}

	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean hasTileEntity(IBlockState state) {
		return true;
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new TileEntitySpit();
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		boolean hasSoundPlayed = false;

		TileEntity tileEntity = worldIn.getTileEntity(pos);
		if (!(tileEntity instanceof TileEntitySpit) || playerIn.isSneaking()) {
			return false;
		}

		TileEntitySpit te = (TileEntitySpit) tileEntity;
		ItemStack item = playerIn.getHeldItem(hand);

		for (int i = 0; i < te.inventory().getSlots(); i++) {

			// If player is holding item
			if (!item.isEmpty()) {
				
				// If item allowed in config
				for (int z = 0; z < TANSpit.CONFIG.itemList.length; z++) {
					if (Item.REGISTRY.getObject(new ResourceLocation(TANSpit.CONFIG.itemList[z])) == item.getItem()) {
						if (TANSpit.CONFIG.isItemListBlacklist) {
							return true;
						}
					} else {
						if (!TANSpit.CONFIG.isItemListBlacklist && z == TANSpit.CONFIG.itemList.length - 1) {
							return true;
						}
					}
				}

				// Find an empty slot
				if (te.inventory().getStackInSlot(i).isEmpty()) {
					if (te.inventory().insertItem(i, new ItemStack(item.getItem(), 1, item.getItemDamage()), false).isEmpty()) {

						// Remove 1 item
						playerIn.getHeldItem(hand).shrink(1);
						worldIn.playSound((EntityPlayer) null, pos.getX() + 0.5, (double) pos.getY() + 0.5, pos.getZ() + 0.5, SoundEvents.ENTITY_ITEMFRAME_PLACE, SoundCategory.BLOCKS, 0.5F, worldIn.rand.nextFloat() * 0.1F + 0.9F);
					}
					return true;
				}
			} else {

				// If item is not empty
				if (!te.inventory().getStackInSlot(i).isEmpty()) {

					// Take item
					playerIn.inventory.addItemStackToInventory(te.inventory().getStackInSlot(i));
					te.inventory().extractItem(i, 1, false);

					// Play sound
					if (!hasSoundPlayed) {
						worldIn.playSound((EntityPlayer) null, pos.getX() + 0.5, (double) pos.getY() + 0.5, pos.getZ() + 0.5, SoundEvents.ENTITY_ITEMFRAME_REMOVE_ITEM, SoundCategory.BLOCKS, 0.5F, worldIn.rand.nextFloat() * 0.1F + 0.9F);
						hasSoundPlayed = true;
					}
				}
			}
		}
		return true;
	}

	@Override
	public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.CUTOUT_MIPPED;
	}

	@Override
	public boolean canPlaceBlockOnSide(World worldIn, BlockPos pos, EnumFacing side) {
		if (side != EnumFacing.UP || worldIn.getBlockState(pos.down()).getBlock() != TANBlocks.campfire) {
			return false;
		}
		return super.canPlaceBlockOnSide(worldIn, pos, side);
	}

	public boolean canBlockStay(World worldIn, BlockPos pos, IBlockState state) {
		if (worldIn.getBlockState(pos.down()).getBlock() != TANBlocks.campfire) {
			return false;
		}
		return true;
	}

	@Override
	public void observedNeighborChange(IBlockState observerState, World world, BlockPos observerPos, Block changedBlock, BlockPos changedBlockPos) {
		this.checkAndDropBlock(world, observerPos, observerState);
		super.observedNeighborChange(observerState, world, observerPos, changedBlock, changedBlockPos);
	}

	@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
		this.checkAndDropBlock(worldIn, pos, state);
	}

	protected void checkAndDropBlock(World worldIn, BlockPos pos, IBlockState state) {
		if (!this.canBlockStay(worldIn, pos, state)) {
			this.dropBlockAsItem(worldIn, pos, state, 0);
			worldIn.setBlockState(pos, Blocks.AIR.getDefaultState(), 3);
		}
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntitySpit();
	}

	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.MODEL;
	}

	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		TileEntity tileentity = worldIn.getTileEntity(pos);

		if (tileentity instanceof TileEntitySpit) {
			dropInventoryItems(worldIn, pos, ((TileEntitySpit) tileentity).inventory());
			worldIn.updateComparatorOutputLevel(pos, this);
		}

		super.breakBlock(worldIn, pos, state);
		worldIn.removeTileEntity(pos);
	}

	private static void dropInventoryItems(World worldIn, BlockPos pos, IItemHandler inventory) {
		for (int i = 0; i < inventory.getSlots(); ++i) {
			ItemStack itemstack = inventory.getStackInSlot(i);

			if (itemstack.getCount() > 0) {
				InventoryHelper.spawnItemStack(worldIn, (double) pos.getX(), (double) pos.getY(), (double) pos.getZ(), itemstack);
			}
		}
	}

	// Makes sure the TE isn't deleted before the block
	@Override
	public boolean removedByPlayer(IBlockState state, World world, BlockPos pos, EntityPlayer player, boolean willHarvest) {
		if (willHarvest) {
			return true; // If it will harvest, delay deletion of the block
							// until after getDrops
		}
		return super.removedByPlayer(state, world, pos, player, willHarvest);
	}

	@Override
	public void onBlockHarvested(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player) {
		super.onBlockHarvested(worldIn, pos, state, player);

		if (!player.capabilities.isCreativeMode) {
			this.dropBlockAsItem(worldIn, pos, state, 0);
		}
	}

	// Makes sure the block is deleted correctly
	@Override
	public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, TileEntity te, ItemStack stack) {
		super.harvestBlock(worldIn, player, pos, state, te, stack);
		worldIn.setBlockToAir(pos);
	}

}
