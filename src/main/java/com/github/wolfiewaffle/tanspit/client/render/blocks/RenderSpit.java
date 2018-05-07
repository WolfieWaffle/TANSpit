package com.github.wolfiewaffle.tanspit.client.render.blocks;

import com.github.wolfiewaffle.tanspit.init.ModBlocks;
import com.github.wolfiewaffle.tanspit.tileentity.TileEntitySpit;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class RenderSpit extends TileEntitySpecialRenderer<TileEntitySpit> {

	@Override
	public void render(TileEntitySpit te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {

		IBlockState state = te.getWorld().getBlockState(te.getPos());

		if (state.getBlock() != ModBlocks.spit) {
			return;
		}

		bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);

		GlStateManager.disableLighting();

		IItemHandler inv = te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
		assert inv != null;

		GlStateManager.pushMatrix();

		// float angle = state.getValue(BlockRack.FACING).getHorizontalAngle();
		GlStateManager.translate(x + 0.5, y - 0.2, z + 0.5);
		GlStateManager.rotate(45, 0, 1, 0);

		for (int i = 0; i < 3; i++) {
			ItemStack stack = inv.getStackInSlot(i);
			if (stack.getCount() > 0) {
				GlStateManager.pushMatrix();

				float zz = (i - 1f) * 0.1875f;

				GlStateManager.translate(0, 0, zz);

				GlStateManager.color(1f, 1f, 1f, 1f);

				Minecraft mc = Minecraft.getMinecraft();
				mc.renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
				mc.getRenderItem().renderItem(stack, ItemCameraTransforms.TransformType.GROUND);
				// System.out.println(stack);

				GlStateManager.popMatrix();
			}
		}

		GlStateManager.popMatrix();

		GlStateManager.enableLighting();
	}
}