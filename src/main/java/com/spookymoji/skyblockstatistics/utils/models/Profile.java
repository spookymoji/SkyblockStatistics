package com.spookymoji.skyblockstatistics.utils.models;

public class Profile {

    private String username;
    private String status;
    private String uuid;

    private long coins, combatXp, miningXp, foragingXp, enchantingXp, farmingXp, fishingXp, alchemyXp, kills, deaths, zealotKills, highestCrit, auctionsWon, auctionsHighestBid, auctionsGoldSpent;

    private int fairySouls;

    public Profile(String username, String uuid, String status) {
        this.username = username;
        this.uuid = uuid;
        this.status = status;
    }

    public long getKills() {
        return kills;
    }

    public void setKills(long kills) {
        this.kills = kills;
    }

    public long getDeaths() {
        return deaths;
    }

    public void setDeaths(long deaths) {
        this.deaths = deaths;
    }

    public int getFairySouls() {
        return fairySouls;
    }

    public void setFairySouls(int fairySouls) {
        this.fairySouls = fairySouls;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public int getCombatXp() {
        return this.getSkillLevel(this.combatXp);
    }

    public void setCombatXp(long combatXp) {
        this.combatXp = combatXp;
    }

    public int getMiningXp() {
        return this.getSkillLevel(this.miningXp);
    }

    public void setMiningXp(long miningXp) {
        this.miningXp = miningXp;
    }

    public int getForagingXp() {
        return this.getSkillLevel(this.foragingXp);
    }

    public void setForagingXp(long foragingXp) {
        this.foragingXp = foragingXp;
    }

    public int getEnchantingXp() {
        return this.getSkillLevel(this.enchantingXp);
    }

    public void setEnchantingXp(long enchantingXp) {
        this.enchantingXp = enchantingXp;
    }

    public int getFarmingXp() {
        return this.getSkillLevel(this.farmingXp);
    }

    public void setFarmingXp(long farmingXp) {
        this.farmingXp = farmingXp;
    }

    public int getFishingXp() {
        return this.getSkillLevel(this.fishingXp);
    }

    public void setFishingXp(long fishingXp) {
        this.fishingXp = fishingXp;
    }

    public int getAlchemyXp() {
        return this.getSkillLevel(this.alchemyXp);
    }

    public void setAlchemyXp(long alchemyXp) {
        this.alchemyXp = alchemyXp;
    }

    public void setCoins(long coins) {
        this.coins = coins;
    }

    public long getCoins() {
        return this.coins;
    }

    public long getZealotKills() {
        return zealotKills;
    }

    public void setZealotKills(long zealotKills) {
        this.zealotKills = zealotKills;
    }

    public long getHighestCrit() {
        return highestCrit;
    }

    public void setHighestCrit(long highestCrit) {
        this.highestCrit = highestCrit;
    }

    public long getAuctionsWon() {
        return auctionsWon;
    }

    public void setAuctionsWon(long auctionsWon) {
        this.auctionsWon = auctionsWon;
    }

    public long getAuctionsHighestBid() {
        return auctionsHighestBid;
    }

    public void setAuctionsHighestBid(long auctionsHighestBid) {
        this.auctionsHighestBid = auctionsHighestBid;
    }

    public long getAuctionsGoldSpent() {
        return auctionsGoldSpent;
    }

    public void setAuctionsGoldSpent(long auctionsGoldSpent) {
        this.auctionsGoldSpent = auctionsGoldSpent;
    }

    private int getSkillLevel(long totalXp) {

        if(totalXp<0)
            return -1;

        long[] skillLevels = {0, 50, 175, 375, 675, 1175, 1925, 2925, 4425, 6425, 9925, 14925, 22425, 32425, 47425, 67425, 97425, 147425, 222425, 322425, 522425, 822425, 1222425, 1722425, 2322425, 3022425, 3822425, 4722425, 5722425, 6822425, 8022425, 9322425, 10722425, 12222425, 13822425, 15522425, 17322425, 19222425, 21222425, 23322425, 25522425, 27822425, 30222425, 32722425, 35322425, 38072425, 40972425, 44072425, 47472425, 51172425, 55172425};

        for(int i = 0; i<skillLevels.length; i++) {
            if(i == skillLevels.length-1) {
                break;
            }
            if(totalXp >= skillLevels[i] && totalXp < skillLevels[i+1]) {
                return i;
            }
        }

        return 50;
    }
}
