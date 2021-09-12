package com.alpsbte.companion.commands;

import com.alpsbte.companion.utils.Utils;
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
                    player.playSound(player.getLocation(), Utils.TeleportSound, 1f, 1f);
                    player.sendMessage(Utils.getInfoMessageFormat("Teleporting to player..."));
                } catch (Exception ignore) {
                    player.sendMessage(Utils.getErrorMessageFormat("Usage: /tpp <Player>"));
                }
            } else {
                sender.sendMessage(Utils.getErrorMessageFormat("You don't have permission to execute this command!"));
            }
        }
        return true;
    }
}
