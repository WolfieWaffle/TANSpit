package com.github.wolfiewaffle.tanspit.tileentity;

import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModTileEntities {

	public static void init() {
		GameRegistry.registerTileEntity(TileEntitySpit.class, "tileentity_spit");
	}

}
