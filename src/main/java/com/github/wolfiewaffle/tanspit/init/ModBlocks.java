package com.github.wolfiewaffle.tanspit.init;

import com.github.wolfiewaffle.tanspit.TANSpit;
import com.github.wolfiewaffle.tanspit.blocks.BlockSpit;

import net.minecraft.block.Block;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

@Mod.EventBusSubscriber(modid = TANSpit.MOD_ID)
public final class ModBlocks {

	// Lanterns
	public static BlockSpit spit;

	@SubscribeEvent
	public static void createBlocks(RegistryEvent.Register<Block> event) {
		IForgeRegistry<Block> blockRegistry = event.getRegistry();

		spit = new BlockSpit("spit");
		blockRegistry.register(spit);
	}

}
