package com.alpsbte.companion.commands;

import com.alpsbte.companion.Companion;
import com.alpsbte.companion.core.config.ConfigPaths;
import com.alpsbte.companion.utils.Utils;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class CMD_Map implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if(sender instanceof Player) {
            if(sender.hasPermission("alpsbte.map")) {
                FileConfiguration config = Companion.getPlugin().getConfigManager().getConfig();
                Player player = (Player)sender;

                player.teleport(new Location(player.getWorld(),
                        config.getDouble(ConfigPaths.SPAWN_POINTS_MAP_X),
                        config.getDouble(ConfigPaths.SPAWN_POINTS_MAP_Y),
                        config.getDouble(ConfigPaths.SPAWN_POINTS_MAP_Z),
                        (float) config.getDouble(ConfigPaths.SPAWN_POINTS_MAP_YAW),
                        (float) config.getDouble(ConfigPaths.SPAWN_POINTS_MAP_PITCH)));

                player.sendMessage(Utils.getInfoMessageFormat("Use the §6pressure plates §ato teleport to the specific location."));
                player.playSound(player.getLocation(), Sound.ENTITY_FIREWORK_LAUNCH, 5.0f, 1.0f);
            } else {
                sender.sendMessage(Utils.getErrorMessageFormat("You don't have permission to execute this command!"));
            }
        }
        return true;
    }
}
