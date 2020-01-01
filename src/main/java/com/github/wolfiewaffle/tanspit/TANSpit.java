package com.github.wolfiewaffle.tanspit;

import com.github.wolfiewaffle.tanspit.proxy.CommonProxy;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Config.Comment;
import net.minecraftforge.common.config.Config.LangKey;
import net.minecraftforge.common.config.Config.RangeDouble;
import net.minecraftforge.common.config.Config.RangeInt;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = TANSpit.MOD_ID, name = TANSpit.MODNAME, version = TANSpit.VERSION, dependencies = "required-after:toughasnails")
public class TANSpit {

	// This is the 1.9.4 branch
	public static final String MOD_ID = "tanspit";
	public static final String MODNAME = "Campfire Spit";
	public static final String VERSION = "1.12.2-1.3";

	// Proxy
	@SidedProxy(clientSide = "com.github.wolfiewaffle.tanspit.proxy.ClientProxy", serverSide = "com.github.wolfiewaffle.tanspit.proxy.ServerProxy")
	public static CommonProxy proxy;

	// Configuration
	public static Configuration config;

	// Instance so we can refer to the mod later
	@Instance(MOD_ID)
	public static TANSpit instance = new TANSpit();

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		TANSpit.proxy.preInit(event);
	}

	@EventHandler
	public void Init(FMLInitializationEvent event) {
		TANSpit.proxy.init(event);
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		TANSpit.proxy.postInit(event);
	}

	@Config(modid = MOD_ID)
	public static class CONFIG {
		@Comment({ "If this is true, no items in itemList can be used in the campfire. If false, only items listed can be used." })
		public static boolean isItemListBlacklist = true;

		@Comment({ "This is the item list used with isItemBlacklist to determine what items can be placed onto the spit." })
		public static String[] itemList = { "minecraft:example" };
	}

}
