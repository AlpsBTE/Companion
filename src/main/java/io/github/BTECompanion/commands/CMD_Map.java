package github.BTECompanion.commands;

import github.BTECompanion.BTECompanion;
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
            if(sender.hasPermission("companion.map")) {
                FileConfiguration config = BTECompanion.getPlugin().getConfig();
                Player player = (Player)sender;

                player.teleport(new Location(player.getWorld(),
                        config.getDouble("Companion.map-spawn-point.x"),
                        config.getDouble("Companion.map-spawn-point.y"),
                        config.getDouble("Companion.map-spawn-point.z"),
                        (float) config.getDouble("Companion.map-spawn-point.yaw"),
                        (float) config.getDouble("Companion.map-spawn-point.pitch")));

                player.sendMessage("§8§l>> §aWelcome to the countries map! Use the §6pressure plates §ato teleport to the specific location.");
                player.playSound(player.getLocation(), Sound.ENTITY_FIREWORK_LAUNCH, 5.0f, 1.0f);
            }
        }
        return true;
    }
}
