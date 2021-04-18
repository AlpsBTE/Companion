package github.BTECompanion.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@Deprecated
public class CMD_Tpll implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if(sender instanceof Player) {
            if(sender.hasPermission("alpsbte.tpll")) {
                if(args.length >= 1) {
                    Player player = (Player)sender;
                    player.performCommand("cs tpll " + String.join(" ", args));
                } else {
                    sender.sendMessage("§8§l>> §cUsage: /tpll <lon> <lat>");
                }
            }
        }
        return true;
    }
}
