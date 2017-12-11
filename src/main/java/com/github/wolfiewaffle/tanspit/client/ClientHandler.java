package com.github.wolfiewaffle.tanspit.client;

import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ClientHandler {

	public static void playSound(BlockPos pos, World world, SoundEvent sound, SoundCategory category, float volume, float pitch) {
		if (world.isRemote) {
			world.playSound(null, pos, sound, category, volume, pitch);
		}
	}

}
