package github.BTECompanion.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CMD_Speed implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if(sender instanceof Player) {
            if(sender.hasPermission("alpsbte.speed")) {
                Player player = (Player)sender;
                if(args.length == 1) {
                    try {
                        float speed = Float.parseFloat(args[0]) / 10;

                        if(speed >= 0.1 && speed <= 0.4) {
                            player.setFlySpeed(speed);
                            player.sendMessage("§8>> §aSuccessfully set speed to §6" + args[0] + "§a.");
                        } else {
                            player.sendMessage("§8§l>> §cUsage: /speed <1/2/3>");;
                        }
                    } catch (Exception e) {
                        player.sendMessage("§8§l>> §cUsage: /speed <1/2/3>");
                    }
                } else {
                    player.sendMessage("§8§l>> §cUsage: /speed <1/2/3>");
                }
            }
        }
        return true;
    }
}
