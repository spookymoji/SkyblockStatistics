package com.spookymoji.skyblockstatistics.utils;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.spookymoji.skyblockstatistics.SkyblockStatistics;
import com.spookymoji.skyblockstatistics.utils.models.Profile;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class PlayerFetcherThread implements Runnable{

    SkyblockStatistics skyblockStatistics;
    ICommandSender sender;
    String username;

    public PlayerFetcherThread(ICommandSender sender, String username, SkyblockStatistics skyblockStatistics) {
        this.sender = sender;
        this.username = username;
        this.skyblockStatistics = skyblockStatistics;
    }

    private String getPlayerUUID(String name) throws IOException {

        HTTPClient jsonUtil = new HTTPClient("https://api.mojang.com/users/profiles/minecraft/" + name);
        String data = jsonUtil.getRawResponse();
        Map<String, Object> jsonMap;
        Gson gson = new Gson();
        Type outputType = new TypeToken<Map<String, Object>>(){}.getType();
        jsonMap = gson.fromJson(data, outputType);
        if(jsonMap != null) {
            return jsonMap.get("id").toString();
        } else {
            return null;
        }
    }

    private String getPlayerStatus(String uuid) throws IOException {

        if(uuid == null) {
            return null;
        }

        HTTPClient jsonUtil = new HTTPClient("https://api.hypixel.net/status?key=" + skyblockStatistics.getApiKey() + "&uuid=" + uuid);

        String data = jsonUtil.getRawResponse();
        JsonParser parser = new JsonParser();
        Boolean success = parser.parse(data).getAsJsonObject().get("success").getAsBoolean();
        if(success == null || (! success)) {
            return null;
        } else {
            Boolean online = parser.parse(data).getAsJsonObject().get("session").getAsJsonObject().get("online").getAsBoolean();
            if(online) {
                return EnumChatFormatting.GREEN + "Online";
            } else {
                return EnumChatFormatting.RED + "Offline";
            }
        }
    }

    private List<String> getPlayerSkyblockProfiles(String uuid) throws IOException {

        HTTPClient jsonUtil = new HTTPClient("https://api.hypixel.net/player?key=" + skyblockStatistics.getApiKey() + "&uuid=" + uuid);

        String data = jsonUtil.getRawResponse();
        JsonParser parser = new JsonParser();
        JsonObject playerObject = parser.parse(data).getAsJsonObject().get("player").getAsJsonObject();
        if(playerObject.get("stats").getAsJsonObject().get("SkyBlock") == null) {
            return null;
        }
        JsonObject skyblockObject = playerObject.get("stats").getAsJsonObject().get("SkyBlock").getAsJsonObject();
        Set<Map.Entry<String, JsonElement>> profileSet = skyblockObject.get("profiles").getAsJsonObject().entrySet();
        List<String> profiles = new ArrayList<String>();
        for (Map.Entry<String, JsonElement> entry: profileSet) {
            profiles.add(entry.getKey());
        }
        return profiles;
    }

    @Override
    public void run() {
        try {

            String uuid = this.getPlayerUUID(this.username);
            String status = this.getPlayerStatus(uuid);
            List<String> profiles = this.getPlayerSkyblockProfiles(uuid);

            if(uuid == null || status == null) {
                this.sender.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "[SkyblockStats] User does not exist. Check if the username is right"));
            }
            if(profiles == null) {
                this.sender.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "[SkyblockStats] User has no active SkyBlock profiles"));
            } else {
                PlayerSkyblockStatsFetcher fetcher = new PlayerSkyblockStatsFetcher(profiles, username, uuid, status, this.skyblockStatistics);
                Profile profileObject = fetcher.getProfileObject();
                PlayerChatDisplay chatDisplay = new PlayerChatDisplay(sender, profileObject);
                //show results
                chatDisplay.displayStats();
            }
        } catch (IOException e) {
            this.sender.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "[SkyblockStats] Error retrieving the information"));
        }
    }
}
