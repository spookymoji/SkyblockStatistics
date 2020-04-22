package com.spookymoji.skyblockstatistics.utils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.spookymoji.skyblockstatistics.SkyblockStatistics;
import com.spookymoji.skyblockstatistics.utils.models.Profile;

import java.io.IOException;
import java.math.BigInteger;
import java.util.*;


public class PlayerSkyblockStatsFetcher {

    private SkyblockStatistics skyblockStatistics;
    private List<String> profiles;
    private String uuid;
    private String status;
    private String username;

    public PlayerSkyblockStatsFetcher(List<String> profiles, String username, String uuid, String status, SkyblockStatistics skyblockStatistics) {
        this.skyblockStatistics = skyblockStatistics;
        this.uuid = uuid;
        this.profiles = profiles;
        this.status = status;
        this.username = username;
    }

    public Profile getProfileObject() throws IOException {

        Map<String, Long> profileLogins = new HashMap<String, Long>();

        //Populate hash map with all latest logins to profiles
        for(int i = 0; i < profiles.size(); i++) {
            JsonObject profileData = this.getProfileData(profiles.get(i));
            if(profileData != null) {
                profileLogins.put(profiles.get(i), profileData.get("members").getAsJsonObject().get(this.uuid).getAsJsonObject().get("last_save").getAsLong());
            }
        }

        Long max = Collections.max(profileLogins.values());
        String latestProfile = profiles.get(0);
        for (Map.Entry<String, Long> entry : profileLogins.entrySet()) {
            if (entry.getValue() == max) {
                latestProfile = entry.getKey();
            }
        }

        Profile profile = this.buildProfile(this.getProfileData(latestProfile));

        return profile;
    }

    private JsonObject getProfileData(String profileId) throws IOException {
        
        HTTPClient jsonUtil = new HTTPClient("https://api.hypixel.net/skyblock/profile?key=" + skyblockStatistics.getApiKey() + "&profile=" + profileId);

        String data = jsonUtil.getRawResponse();
        JsonParser parser = new JsonParser();
        JsonObject memberObject = parser.parse(data).getAsJsonObject().get("profile").getAsJsonObject();
        if(memberObject == null) {
            return null;
        }
        return memberObject;
    }

    private Profile buildProfile(JsonObject profileObject) {
        Profile profile = new Profile(this.username, this.uuid, this.status);
        //populate generals
        profile.setFairySouls(this.safeIntGet(profileObject, "fairy_souls_collected"));
        profile.setCoins(this.safeLongGet(profileObject, "coin_purse"));
        profile.setBankCoins(this.safeBankBalanceGet(profileObject));
        profile.setKills(profileObject.get("members").getAsJsonObject().get(uuid).getAsJsonObject().get("stats").getAsJsonObject().get("kills").getAsLong());
        profile.setDeaths(profileObject.get("members").getAsJsonObject().get(uuid).getAsJsonObject().get("stats").getAsJsonObject().get("deaths").getAsLong());
        profile.setZealotKills(this.safeStatLongGet(profileObject, "kills_zealot_enderman"));
        profile.setHighestCrit(this.safeStatLongGet(profileObject, "highest_critical_damage"));
        //populate skills
        profile.setCombatXp(this.safeLongGet(profileObject, "experience_skill_combat"));
        profile.setMiningXp(this.safeLongGet(profileObject, "experience_skill_mining"));
        profile.setForagingXp(this.safeLongGet(profileObject, "experience_skill_foraging"));
        profile.setEnchantingXp(this.safeLongGet(profileObject, "experience_skill_enchanting"));
        profile.setAlchemyXp(this.safeLongGet(profileObject, "experience_skill_alchemy"));
        profile.setFishingXp(this.safeLongGet(profileObject, "experience_skill_fishing"));
        profile.setFarmingXp(this.safeLongGet(profileObject, "experience_skill_farming"));
        //populate auctions
        profile.setAuctionsWon(this.safeStatLongGet(profileObject, "auctions_won"));
        profile.setAuctionsHighestBid(this.safeStatLongGet(profileObject, "auctions_highest_bid"));
        profile.setAuctionsGoldSpent(this.safeStatLongGet(profileObject, "auctions_gold_spent"));

        return profile;
    }

    private long safeLongGet(JsonObject profileObject, String key) {
        if(profileObject.get("members").getAsJsonObject().get(uuid).getAsJsonObject().get(key) != null) {
            return profileObject.get("members").getAsJsonObject().get(uuid).getAsJsonObject().get(key).getAsLong();
        } else {
            return -1;
        }
    }

    private int safeIntGet(JsonObject profileObject, String key) {
        if(profileObject.get("members").getAsJsonObject().get(uuid).getAsJsonObject().get(key) != null) {
            return profileObject.get("members").getAsJsonObject().get(uuid).getAsJsonObject().get(key).getAsInt();
        } else {
            return -1;
        }
    }

    private long safeStatLongGet(JsonObject profileObject, String key) {
        if(profileObject.get("members").getAsJsonObject().get(uuid).getAsJsonObject().get("stats").getAsJsonObject().get(key) != null) {
            return profileObject.get("members").getAsJsonObject().get(uuid).getAsJsonObject().get("stats").getAsJsonObject().get(key).getAsLong();
        } else {
            return 0;
        }
    }

    private long safeBankBalanceGet(JsonObject profileObject) {
        if(profileObject.get("banking") != null) {
            return profileObject.get("banking").getAsJsonObject().get("balance").getAsLong();
        } else {
            return -1;
        }
    }

}
