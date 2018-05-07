package com.github.wolfiewaffle.tanspit;

import java.util.Set;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.fml.client.IModGuiFactory;
import net.minecraftforge.fml.client.config.GuiConfig;

public class ConfigGuiFactory implements IModGuiFactory {

    
    protected String modid, title;
    protected Minecraft minecraft;
    
    protected ConfigGuiFactory(String modid, String title)
    {
        this.modid = modid;
        this.title = title;
    }

    @Override
    public boolean hasConfigGui()
    {
        return true;
    }

    @Override
    public void initialize(Minecraft minecraftInstance)
    {
        this.minecraft = minecraftInstance;
    }

    @Override
    public GuiScreen createConfigGui(GuiScreen parentScreen)
    {  
        return new GuiConfig(parentScreen, modid, title);
    }

    @Override
    public Set<RuntimeOptionCategoryElement> runtimeGuiCategories()
    {
        return null;
    }

	/*
	@Override
	public void initialize(Minecraft minecraftInstance) {

	}

	@Override
	public Class<? extends GuiScreen> mainConfigGuiClass() {
		return ConfigGui.class;
	}

	@Override
	public Set<RuntimeOptionCategoryElement> runtimeGuiCategories() {
		return null;
	}

	@SuppressWarnings("deprecation")
	@Override
	public RuntimeOptionGuiHandler getHandlerFor(RuntimeOptionCategoryElement element) {
		return null;
	}

	@Override
	public boolean hasConfigGui() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public GuiScreen createConfigGui(GuiScreen parentScreen) {
		// TODO Auto-generated method stub
		return null;
	}*/

}
