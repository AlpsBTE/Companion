package com.alpsbte.companion.commands;

import com.alpsbte.companion.core.menus.CompanionMenu;
import com.alpsbte.companion.utils.Utils;
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
            } else {
                sender.sendMessage(Utils.getErrorMessageFormat("You don't have permission to execute this command!"));
            }
        }
        return true;
    }
}
