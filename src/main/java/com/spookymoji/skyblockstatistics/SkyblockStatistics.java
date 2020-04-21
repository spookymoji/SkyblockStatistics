package com.spookymoji.skyblockstatistics;

import com.spookymoji.skyblockstatistics.commands.SetupAPIKeyCommand;
import com.spookymoji.skyblockstatistics.commands.ViewStatsCommand;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import org.lwjgl.Sys;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

@Mod(modid = SkyblockStatistics.MODID, name= SkyblockStatistics.MOD_NAME, version = SkyblockStatistics.VERSION)
public class SkyblockStatistics
{
    public static final String MODID = "skyblockstatistics";
    public static final String MOD_NAME = "SkyblockStatistics";
    public static final String VERSION = "1.0";

    public static String SB_API_KEY = "";
    
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        this.loadAPIKey();
        ClientCommandHandler.instance.registerCommand(new ViewStatsCommand(this));
        ClientCommandHandler.instance.registerCommand(new SetupAPIKeyCommand(this));
    }

    private void loadAPIKey() {
        File apiFile = new File(Minecraft.getMinecraft().mcDataDir.getAbsolutePath() + "/sbapikey.config");
        if(apiFile.exists()) {
            try {
                String content = new Scanner(apiFile).useDelimiter("\\Z").next();
                if(! content.isEmpty()) {
                    SB_API_KEY = content;
                }
            } catch(FileNotFoundException e) {

            }
        }

    }

    public String getApiKey() {
        return SB_API_KEY;
    }

    public void setApiKey(String key) {
        SB_API_KEY = key;
    }

}
