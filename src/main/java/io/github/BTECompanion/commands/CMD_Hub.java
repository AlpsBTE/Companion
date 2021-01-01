package github.BTECompanion.commands;

import github.BTECompanion.BTECompanion;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CMD_Hub implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if(sender instanceof Player) {
            if(sender.hasPermission("companion.hub")) {
                sender.sendMessage("§8§l>> §aConnecting to server");
                BTECompanion.getPlugin().connectPlayer((Player)sender, "HUB");
            }
        }
        return true;
    }
}
