package com.github.wolfiewaffle.tanspit.proxy;

import java.io.File;

import com.github.wolfiewaffle.tanspit.ConfigHandler;
import com.github.wolfiewaffle.tanspit.tileentity.ModTileEntities;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy {
	public void preInit(FMLPreInitializationEvent e) {
		ConfigHandler.init(new File(e.getModConfigurationDirectory(), "tanspit.cfg"));
		ModTileEntities.init();
	}

	public void init(FMLInitializationEvent e) {

	}

	public void postInit(FMLPostInitializationEvent e) {

	}
}
