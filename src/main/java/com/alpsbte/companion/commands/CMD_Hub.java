package com.alpsbte.companion.commands;

import com.alpsbte.companion.Companion;
import com.alpsbte.companion.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CMD_Hub implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if(sender instanceof Player) {
            if(sender.hasPermission("alpsbte.hub")) {
                sender.sendMessage(Utils.getInfoMessageFormat("Connecting to server..."));
                Companion.getPlugin().connectPlayer((Player)sender, "ALPS-1");
            } else {
                sender.sendMessage(Utils.getErrorMessageFormat("You don't have permission to execute this command!"));
            }
        }
        return true;
    }
}
