package com.github.wolfiewaffle.tanspit;

import java.io.File;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class ConfigHandler {

	private static Configuration config;

	//public static int duration;

	public static void init(File file) {
		config = new Configuration(file);
		syncConfig();
		config.load();
	}

	// Event to create config file
	public static void syncConfig() {
		//duration = config.getFloat("smolder percent", Configuration.CATEGORY_GENERAL, 0.25F, Float.MIN_NORMAL, Float.MAX_VALUE, "The percent of fuel at which torches will dim. 0.25 is 25%.");		

		config.save();
	}

	// For config
	@SubscribeEvent
	public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
		System.out.println("Config Changed");
		if (event.getModID().equals(TANSpit.MOD_ID)) {
			syncConfig();
		}
	}

	public static Configuration getConfig() {
		return config;
	}
}