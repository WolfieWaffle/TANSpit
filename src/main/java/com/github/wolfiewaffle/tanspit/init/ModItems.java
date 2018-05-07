package com.github.wolfiewaffle.tanspit.init;

import com.github.wolfiewaffle.tanspit.TANSpit;
import com.github.wolfiewaffle.tanspit.items.ItemSpit;

import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

@Mod.EventBusSubscriber(modid = TANSpit.MOD_ID)
public class ModItems {

	public static ItemSpit spit;

	@SubscribeEvent
	public static void registerItems(RegistryEvent.Register<Item> event) {
		IForgeRegistry<Item> itemRegistry = event.getRegistry();

		spit = new ItemSpit(ModBlocks.spit);
		itemRegistry.register(spit);
	}

}
