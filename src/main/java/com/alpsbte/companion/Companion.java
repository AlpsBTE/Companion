package com.alpsbte.companion;

import com.alpsbte.companion.commands.*;
import com.alpsbte.companion.core.config.ConfigManager;
import com.alpsbte.companion.core.config.ConfigNotImplementedException;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import com.alpsbte.companion.core.EventListener;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.ipvp.canvas.MenuFunctionListener;
import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class Companion extends JavaPlugin {

    private static final String VERSION = "2.0";

    private static Companion plugin;
    private ConfigManager configManager;

    private boolean pluginEnabled = false;

    @Override
    public void onEnable() {
        plugin = this;

        String successPrefix = ChatColor.DARK_GRAY + "[" + ChatColor.DARK_GREEN + "✔" + ChatColor.DARK_GRAY + "] " + ChatColor.GRAY;
        String errorPrefix = ChatColor.DARK_GRAY + "[" + ChatColor.RED + "X" + ChatColor.DARK_GRAY + "] " + ChatColor.GRAY;

        Bukkit.getConsoleSender().sendMessage(ChatColor.GOLD + "------------------- Companion V" + VERSION + " -------------------");
        Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_GREEN + "Starting plugin...");
        Bukkit.getConsoleSender().sendMessage("");

        // Check for required dependencies, if it returns false disable plugin
        if (!DependencyManager.checkForRequiredDependencies()) {
            Bukkit.getConsoleSender().sendMessage(errorPrefix + "Could not load required dependencies.");
            Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "Missing Dependencies:");
            DependencyManager.missingDependencies.forEach(dependency -> Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + " - " + dependency));

            this.getServer().getPluginManager().disablePlugin(this);
            return;
        }
        Bukkit.getConsoleSender().sendMessage(successPrefix + "Successfully loaded required dependencies.");

        // Load config, if it throws an exception disable plugin
        try {
            configManager = new ConfigManager();
            Bukkit.getConsoleSender().sendMessage(successPrefix + "Successfully loaded configuration file.");
        } catch (ConfigNotImplementedException ex) {
            Bukkit.getConsoleSender().sendMessage(errorPrefix + "Could not load configuration file.");
            Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "The config file must be configured!");

            this.getServer().getPluginManager().disablePlugin(this);
            return;
        }

        // Register event listeners
        try {
            this.getServer().getPluginManager().registerEvents(new EventListener(), plugin);
            this.getServer().getPluginManager().registerEvents(new MenuFunctionListener(), plugin);
            this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
            Bukkit.getConsoleSender().sendMessage(successPrefix + "Successfully registered event listeners.");
        } catch (Exception ex) {
            Bukkit.getConsoleSender().sendMessage(errorPrefix + "Could not register event listeners.");
            Bukkit.getLogger().log(Level.SEVERE, ex.getMessage(), ex);

            this.getServer().getPluginManager().disablePlugin(this);
            return;
        }

        // Register commands
        try {
            this.getCommand("setspawn").setExecutor(new CMD_SetSpawn());
            this.getCommand("creload").setExecutor(new CMD_ReloadConfig());
            this.getCommand("map").setExecutor(new CMD_Map());
            this.getCommand("speed").setExecutor(new CMD_Speed());
            this.getCommand("hub").setExecutor(new CMD_Hub());
            this.getCommand("companion").setExecutor(new CMD_Companion());
            this.getCommand("tpp").setExecutor(new CMD_Tpp());
            this.getCommand("ptime").setExecutor(new CMD_SetTime());
            Bukkit.getConsoleSender().sendMessage(successPrefix + "Successfully registered commands.");
        } catch (Exception ex) {
            Bukkit.getConsoleSender().sendMessage(errorPrefix + "Could not register commands.");
            Bukkit.getLogger().log(Level.SEVERE, ex.getMessage(), ex);

            this.getServer().getPluginManager().disablePlugin(this);
            return;
        }

        pluginEnabled = true;
        Bukkit.getConsoleSender().sendMessage("");
        Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_GREEN + "Enabled Plot-System plugin.");
        Bukkit.getConsoleSender().sendMessage(ChatColor.GOLD + "------------------------------------------------------");
        Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_GRAY + "> " + ChatColor.GRAY + "Made by " + ChatColor.RED + "Alps BTE (AT/CH/LI)");
        Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_GRAY + "> " + ChatColor.GRAY + "GitHub: " + ChatColor.WHITE + "https://github.com/AlpsBTE/Plot-System");
        Bukkit.getConsoleSender().sendMessage(ChatColor.GOLD + "------------------------------------------------------");
    }

    public boolean checkServer(String IP, int port) {
        Socket s = new Socket();
        try {
            s.connect(new InetSocketAddress(IP, port), 20);
            return true;
        } catch (IOException ignore) {
        }
        return false;
    }

    public void connectPlayer(Player player, String server) {
        try {
            ByteArrayDataOutput out = ByteStreams.newDataOutput();
            out.writeUTF("ConnectOther");
            out.writeUTF(player.getName());
            out.writeUTF(server);
            player.sendPluginMessage(this, "BungeeCord", out.toByteArray());
        } catch (Exception ex) {
            getLogger().log(Level.WARNING, "Could not connect player [" + player + "] to " + server, ex);
        }
    }

    @Override
    public void onDisable() {
        if (!pluginEnabled) {
            Bukkit.getConsoleSender().sendMessage("");
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Disabling plugin...");
            Bukkit.getConsoleSender().sendMessage(ChatColor.GOLD + "------------------------------------------------------");
            Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_GRAY + "> " + ChatColor.GRAY + "Made by " + ChatColor.RED + "Alps BTE (AT/CH/LI)");
            Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_GRAY + "> " + ChatColor.GRAY + "GitHub: " + ChatColor.WHITE + "https://github.com/AlpsBTE/Companion");
            Bukkit.getConsoleSender().sendMessage(ChatColor.GOLD + "------------------------------------------------------");
        }
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    @Override @Deprecated
    public FileConfiguration getConfig() {
        return this.configManager.getConfig();
    }

    @Override
    public void reloadConfig() {
        this.configManager.reloadConfigs();
    }

    @Override
    public void saveConfig() {
        this.configManager.saveConfigs();
    }


    public static Companion getPlugin() {
        return plugin;
    }


    public static class DependencyManager {

        // List with all missing dependencies
        private final static List<String> missingDependencies = new ArrayList<>();

        /**
         * Check for all required dependencies and inform in console about missing dependencies
         * @return True if all dependencies are present
         */
        private static boolean checkForRequiredDependencies() {
            PluginManager pluginManager = plugin.getServer().getPluginManager();

            if (!pluginManager.isPluginEnabled("HeadDatabase")) {
                missingDependencies.add("HeadDatabase");
            }

            return missingDependencies.isEmpty();
        }
    }
}
