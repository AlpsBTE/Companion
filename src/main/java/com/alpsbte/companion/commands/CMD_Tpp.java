package com.alpsbte.companion.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CMD_Tpp implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if(sender instanceof Player) {
            if(sender.hasPermission("alpsbte.tpp")) {
                Player player = (Player)sender;
                try {
                    Player targetPlayer = player.getServer().getPlayer(args[0]);
                    player.teleport(targetPlayer);
                    player.sendMessage("§8§l>> §aTeleporting to player");
                } catch (Exception ignore) {
                    player.sendMessage("§8§l>> §cUsage: /tpp <Player>");
                }
            }
        }
        return true;
    }
}
