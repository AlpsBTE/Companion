package com.alpsbte.companion.commands;

import com.alpsbte.companion.Companion;
import com.alpsbte.companion.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CMD_ReloadConfig implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if(sender instanceof Player) {
            if(sender.hasPermission("alpsbte.admin")) {
                Companion.getPlugin().reloadConfig();
                Companion.getPlugin().saveConfig();

                Player player = (Player)sender;
                sender.sendMessage(Utils.getInfoMessageFormat("Successfully reloaded config."));
                player.playSound(player.getLocation(), Utils.Done, 1.0f, 1.0f);
            } else {
                sender.sendMessage(Utils.getErrorMessageFormat("You don't have permission to execute this command!"));
            }
        }
        return true;
    }
}
