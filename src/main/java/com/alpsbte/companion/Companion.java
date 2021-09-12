package com.alpsbte.companion;

import com.alpsbte.companion.commands.*;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import com.alpsbte.companion.core.EventListener;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.ipvp.canvas.MenuFunctionListener;
import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;

public class Companion extends JavaPlugin {

    private static Companion plugin;
    private FileConfiguration config;
    private File configFile;

    @Override
    public void onEnable() {
        plugin = this;

        reloadConfig();

        // Add listeners
        this.getServer().getPluginManager().registerEvents(new EventListener(), plugin);
        this.getServer().getPluginManager().registerEvents(new MenuFunctionListener(), plugin);

        // Add commands
        this.getCommand("setspawn").setExecutor(new CMD_SetSpawn());
        this.getCommand("creload").setExecutor(new CMD_ReloadConfig());
        this.getCommand("map").setExecutor(new CMD_Map());
        this.getCommand("speed").setExecutor(new CMD_Speed());
        this.getCommand("hub").setExecutor(new CMD_Hub());
        this.getCommand("companion").setExecutor(new CMD_Companion());

        if(getConfig().getBoolean("Companion.enable-custom-tp")) {
            this.getCommand("tpp").setExecutor(new CMD_Tpp());
        }

        this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");

        getLogger().log(Level.INFO, "Successfully enabled AlpsBTE-Companion plugin.");
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

    public static Companion getPlugin() {
        return plugin;
    }

    @Override
    public void reloadConfig() {
        configFile = new File(getDataFolder(), "config.yml");
        if (configFile.exists()) {
            config = YamlConfiguration.loadConfiguration(configFile);
        } else {
            // Look for default configuration file
            Reader defConfigStream = new InputStreamReader(this.getResource("defaultConfig.yml"), StandardCharsets.UTF_8);

            config = YamlConfiguration.loadConfiguration(defConfigStream);
        }
        saveConfig();
    }

    @Override
    public FileConfiguration getConfig() {
        if (config == null) {
            reloadConfig();
        }
        return config;
    }

    @Override
    public void saveConfig() {
        if (config == null || configFile == null) {
            return;
        }

        try {
            getConfig().save(configFile);
        } catch (IOException ex) {
            getLogger().log(Level.SEVERE, "Could not save config to " + configFile, ex);
        }
    }
}
