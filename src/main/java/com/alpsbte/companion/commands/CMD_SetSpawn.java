package com.alpsbte.companion.commands;

import com.alpsbte.companion.Companion;
import com.alpsbte.companion.core.config.ConfigPaths;
import com.alpsbte.companion.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class CMD_SetSpawn implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if(sender instanceof Player) {
            if(sender.hasPermission("alpsbte.admin")) {
                if(args.length == 1) {
                    FileConfiguration config = Companion.getPlugin().getConfigManager().getConfig();
                    Player player = (Player)sender;

                    if(args[0].equalsIgnoreCase("map")) {
                        config.set(ConfigPaths.SPAWN_POINTS_MAP_X, player.getLocation().getX());
                        config.set(ConfigPaths.SPAWN_POINTS_MAP_Y, player.getLocation().getY());
                        config.set(ConfigPaths.SPAWN_POINTS_MAP_Z, player.getLocation().getZ());
                        config.set(ConfigPaths.SPAWN_POINTS_MAP_YAW, player.getLocation().getYaw());
                        config.set(ConfigPaths.SPAWN_POINTS_MAP_PITCH, player.getLocation().getPitch());
                    } else if(args[0].equalsIgnoreCase("trees")) {
                        config.set(ConfigPaths.SPAWN_POINTS_TREES_X, player.getLocation().getX());
                        config.set(ConfigPaths.SPAWN_POINTS_TREES_Y, player.getLocation().getY());
                        config.set(ConfigPaths.SPAWN_POINTS_TREES_Z, player.getLocation().getZ());
                        config.set(ConfigPaths.SPAWN_POINTS_TREES_YAW, player.getLocation().getYaw());
                        config.set(ConfigPaths.SPAWN_POINTS_TREES_PITCH, player.getLocation().getPitch());
                    } else {
                        sender.sendMessage(Utils.getErrorMessageFormat("Usage: /setspawn <map/trees>"));
                        return true;
                    }

                    Companion.getPlugin().saveConfig();
                    sender.sendMessage(Utils.getInfoMessageFormat("Successfully updated spawn point."));
                    player.playSound(player.getLocation(), Utils.Done, 1.0f, 1.0f);
                } else {
                    sender.sendMessage(Utils.getErrorMessageFormat("Usage: /setspawn <map/trees>"));
                }
            } else {
                sender.sendMessage(Utils.getErrorMessageFormat("You don't have permission to execute this command!"));
            }
        }
        return true;
    }
}
