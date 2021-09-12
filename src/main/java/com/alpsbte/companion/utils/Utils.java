package com.alpsbte.companion.utils;

import com.alpsbte.companion.Companion;
import com.alpsbte.companion.core.config.ConfigPaths;
import me.arcaniax.hdb.api.HeadDatabaseAPI;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.inventory.ItemStack;

public class Utils {

    // Get custom head
    public static ItemStack getItemHead(CustomHead head) {
        return head != null ? head.getAsItemStack() : new ItemBuilder(Material.SKULL_ITEM, 1, (byte) 3).build();
    }

    // Sounds
    public static Sound TeleportSound = Sound.ENTITY_ENDERMEN_TELEPORT;
    public static Sound ErrorSound = Sound.ENTITY_ITEM_BREAK;
    public static Sound Done = Sound.ENTITY_EXPERIENCE_ORB_PICKUP;

    // Player Messages
    private static final String messagePrefix =  Companion.getPlugin().getConfigManager().getConfig().getString(ConfigPaths.MESSAGE_PREFIX) + " ";

    public static String getInfoMessageFormat(String info) {
        return messagePrefix + Companion.getPlugin().getConfigManager().getConfig().getString(ConfigPaths.MESSAGE_INFO_COLOUR) + info;
    }

    public static String getErrorMessageFormat(String error) {
        return messagePrefix + Companion.getPlugin().getConfigManager().getConfig().getString(ConfigPaths.MESSAGE_ERROR_COLOUR) + error;
    }

    // Integer Try Parser
    public static Integer tryParseInt(String someText) {
        try {
            return Integer.parseInt(someText);
        } catch (NumberFormatException ex) {
            return null;
        }
    }


    public static class CustomHead {
        private final ItemStack headItem;

        public CustomHead(String headID) {
            this.headItem = headDatabaseAPI != null && headID != null && tryParseInt(headID) != null
                    ? headDatabaseAPI.getItemHead(headID) : new ItemBuilder(Material.SKULL_ITEM, 1, (byte) 3).build();
        }

        public ItemStack getAsItemStack() {
            return headItem;
        }

        private static HeadDatabaseAPI headDatabaseAPI;

        public static CustomHead BACK_BUTTON;
        public static CustomHead NEXT_BUTTON;
        public static CustomHead PREVIOUS_BUTTON;

        public static void loadHeadsAsync(HeadDatabaseAPI api) {
            headDatabaseAPI = api;
            Bukkit.getScheduler().runTaskAsynchronously(Companion.getPlugin(), () -> {
                BACK_BUTTON = new CustomHead("9226");
                NEXT_BUTTON = new CustomHead("9223");
                PREVIOUS_BUTTON = new CustomHead("9226");
            });
        }
    }
}
