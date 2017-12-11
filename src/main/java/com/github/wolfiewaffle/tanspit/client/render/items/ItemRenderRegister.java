package com.github.wolfiewaffle.tanspit.client.render.items;

import com.github.wolfiewaffle.tanspit.TANSpit;
import com.github.wolfiewaffle.tanspit.init.ModItems;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;

public class ItemRenderRegister {
	public static String modid = TANSpit.MOD_ID;

	public static void registerItemRenderer() {
		reg(ModItems.spit);
	}

	public static void reg(Item item) {
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, 0, new ModelResourceLocation(TANSpit.MOD_ID + ":" + item.getUnlocalizedName().substring(5), "inventory"));
	}

}
