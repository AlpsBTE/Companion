package com.alpsbte.companion.commands;

import com.alpsbte.companion.Companion;
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

                sender.sendMessage("§8§l>> §aSuccessfully reloaded config.");
            }
        }
        return true;
    }
}
