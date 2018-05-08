package com.github.wolfiewaffle.tanspit.items;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemBlock;

public class ItemSpit extends ItemBlock {

	public ItemSpit(Block block) {
		super(block);
		this.setRegistryName(block.getRegistryName());
		this.setUnlocalizedName(block.getUnlocalizedName());
		this.setCreativeTab(CreativeTabs.MISC);
	}

}
