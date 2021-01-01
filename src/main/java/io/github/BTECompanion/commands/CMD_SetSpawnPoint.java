package github.BTECompanion.commands;

import github.BTECompanion.BTECompanion;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class CMD_SetSpawnPoint implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if(sender instanceof Player) {
            if(sender.hasPermission("companion.setspawnpoint")) {
                if(args.length == 1) {
                    FileConfiguration config = BTECompanion.getPlugin().getConfig();
                    Player player = (Player)sender;

                    if(args[0].equalsIgnoreCase("map")) {
                        config.set("Companion.map-spawn-point.x", player.getLocation().getX());
                        config.set("Companion.map-spawn-point.y", player.getLocation().getY());
                        config.set("Companion.map-spawn-point.z", player.getLocation().getZ());
                        config.set("Companion.map-spawn-point.yaw", player.getLocation().getYaw());
                        config.set("Companion.map-spawn-point.pitch", player.getLocation().getPitch());
                    } else if(args[0].equalsIgnoreCase("trees")) {
                        config.set("Companion.trees-spawn-point.x", player.getLocation().getX());
                        config.set("Companion.trees-spawn-point.y", player.getLocation().getY());
                        config.set("Companion.trees-spawn-point.z", player.getLocation().getZ());
                        config.set("Companion.trees-spawn-point.yaw", player.getLocation().getYaw());
                        config.set("Companion.trees-spawn-point.pitch", player.getLocation().getPitch());
                    } else {
                        sender.sendMessage("§8§l>> §cUsage: /setspawnpoint <map/trees>");
                        return true;
                    }

                    BTECompanion.getPlugin().saveConfig();
                    sender.sendMessage("§8§l>> §aSuccessfully set new spawn point.");
                    player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 5.0f, 1.0f);
                } else {
                    sender.sendMessage("§8§l>> §cUsage: /setspawnpoint <map/trees>");
                }
            }
        }
        return true;
    }
}
