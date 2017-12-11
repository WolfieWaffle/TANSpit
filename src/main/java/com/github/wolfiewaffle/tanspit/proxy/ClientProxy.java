package com.github.wolfiewaffle.tanspit.proxy;

import com.github.wolfiewaffle.tanspit.client.render.blocks.RenderSpit;
import com.github.wolfiewaffle.tanspit.client.render.items.ItemRenderRegister;
import com.github.wolfiewaffle.tanspit.tileentity.TileEntitySpit;

import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

//@Mod.EventBusSubscriber()
public class ClientProxy extends CommonProxy {

	public void preInit(FMLPreInitializationEvent e) {
		super.preInit(e);

		//MinecraftForge.EVENT_BUS.register(this); // so forge knows about your
													// modelRegistryEvent that
													// is in this clientproxy
													// class
	}

	//@SubscribeEvent
	//public void modelRegistryEvent(ModelRegistryEvent e) {
	//}

	public void init(FMLInitializationEvent e) {
		super.init(e);

		ItemRenderRegister.registerItemRenderer();
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntitySpit.class, new RenderSpit());
	}

	public void postInit(FMLPostInitializationEvent e) {
		super.postInit(e);
	}

}
