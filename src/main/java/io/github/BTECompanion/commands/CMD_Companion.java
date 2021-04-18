package github.BTECompanion.commands;

import github.BTECompanion.core.menus.CompanionMenu;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CMD_Companion implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if(sender instanceof Player) {
            if(sender.hasPermission("alpsbte.companion")) {
                new CompanionMenu().getUI().open((Player)sender);
            }
        }
        return true;
    }
}
