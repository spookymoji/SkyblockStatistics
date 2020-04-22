package com.spookymoji.skyblockstatistics.utils;

import com.spookymoji.skyblockstatistics.utils.models.Profile;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

public class PlayerChatDisplay {

    ICommandSender sender;
    Profile profile;

    public PlayerChatDisplay(ICommandSender sender, Profile profile) {
        this.sender = sender;
        this.profile = profile;
    }

    public void displayStats() {

        sender.addChatMessage(new ChatComponentText(EnumChatFormatting.LIGHT_PURPLE + "" + EnumChatFormatting.BOLD + "General stats of " + EnumChatFormatting.AQUA + profile.getUsername()));
        sender.addChatMessage(new ChatComponentText("Status: " + profile.getStatus()));

        sender.addChatMessage(new ChatComponentText(EnumChatFormatting.LIGHT_PURPLE + "" + EnumChatFormatting.BOLD + "General"));

        sender.addChatMessage(new ChatComponentText("Fairy Souls: " + EnumChatFormatting.LIGHT_PURPLE + "" + EnumChatFormatting.BOLD + profile.getFairySouls() + "/194"));

        sender.addChatMessage(new ChatComponentText("Highest Crit Damage: " + EnumChatFormatting.YELLOW + "" + EnumChatFormatting.BOLD + String.format("%,d", profile.getHighestCrit())));
        sender.addChatMessage(new ChatComponentText("Zealot Kills: " + EnumChatFormatting.LIGHT_PURPLE + "" + EnumChatFormatting.BOLD + profile.getZealotKills()));

        sender.addChatMessage(new ChatComponentText("Total Kills: " + EnumChatFormatting.GREEN + "" + EnumChatFormatting.BOLD + profile.getKills()));
        sender.addChatMessage(new ChatComponentText("Total Deaths: " + EnumChatFormatting.RED + "" + EnumChatFormatting.BOLD + profile.getDeaths()));
        if(profile.getBankCoins()<0) {
            sender.addChatMessage(new ChatComponentText("Purse Coins: " + EnumChatFormatting.GOLD + "" + EnumChatFormatting.BOLD + String.format("%,d", profile.getCoins())));
        } else {
            sender.addChatMessage(new ChatComponentText("Purse Coins: " + EnumChatFormatting.GOLD + "" + EnumChatFormatting.BOLD + String.format("%,d", profile.getCoins()) + EnumChatFormatting.WHITE + " Bank Balance: " + EnumChatFormatting.GOLD + "" + EnumChatFormatting.BOLD + String.format("%,d", profile.getBankCoins())));
        }

        if(profile.getCombatXp() < 0) {
            sender.addChatMessage(new ChatComponentText(EnumChatFormatting.LIGHT_PURPLE + "" + EnumChatFormatting.BOLD + "Skills"));
            sender.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "Skills API not enabled"));
        } else {
            sender.addChatMessage(new ChatComponentText(EnumChatFormatting.LIGHT_PURPLE + "" + EnumChatFormatting.BOLD + "Skills ( Average " + String.format("%.2f", profile.getAverageSkillLevel()) + " )"));
            sender.addChatMessage(new ChatComponentText("Combat: " + this.displaySkillLevel(profile.getCombatXp())));
            sender.addChatMessage(new ChatComponentText("Mining: " + this.displaySkillLevel(profile.getMiningXp())));
            sender.addChatMessage(new ChatComponentText("Foraging: " + this.displaySkillLevel(profile.getForagingXp())));
            sender.addChatMessage(new ChatComponentText("Farming: " + this.displaySkillLevel(profile.getFarmingXp())));
            sender.addChatMessage(new ChatComponentText("Fishing: " + this.displaySkillLevel(profile.getFishingXp())));
            sender.addChatMessage(new ChatComponentText("Enchanting: " + this.displaySkillLevel(profile.getEnchantingXp())));
            sender.addChatMessage(new ChatComponentText("Alchemy: " + this.displaySkillLevel(profile.getAlchemyXp())));
        }

        sender.addChatMessage(new ChatComponentText(EnumChatFormatting.LIGHT_PURPLE + "" + EnumChatFormatting.BOLD + "Auctions"));
        sender.addChatMessage(new ChatComponentText("Auctions Won: " + EnumChatFormatting.GOLD + "" + EnumChatFormatting.BOLD + String.format("%,d", profile.getAuctionsWon())));
        sender.addChatMessage(new ChatComponentText("Highest Bid: " + EnumChatFormatting.GOLD + "" + EnumChatFormatting.BOLD + String.format("%,d", profile.getAuctionsHighestBid())));
        sender.addChatMessage(new ChatComponentText("Gold Spent: " + EnumChatFormatting.GOLD + "" + EnumChatFormatting.BOLD + String.format("%,d", profile.getAuctionsGoldSpent())));

    }

    private String displaySkillLevel(int value) {
        return EnumChatFormatting.GOLD + "" + EnumChatFormatting.BOLD + value;
    }

}
