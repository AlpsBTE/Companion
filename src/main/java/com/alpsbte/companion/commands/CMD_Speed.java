package com.alpsbte.companion.commands;

import com.alpsbte.companion.utils.Utils;
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
                            player.sendMessage(Utils.getInfoMessageFormat("Updated speed to §6" + args[0] + "§a."));
                        } else {
                            player.sendMessage(Utils.getErrorMessageFormat("Usage: /speed <1/2/3>"));
                        }
                    } catch (Exception ignore) {
                        player.sendMessage(Utils.getErrorMessageFormat("Usage: /speed <1/2/3>"));
                    }
                } else {
                    player.sendMessage(Utils.getErrorMessageFormat("Usage: /speed <1/2/3>"));
                }
            } else {
                sender.sendMessage(Utils.getErrorMessageFormat("You don't have permission to execute this command!"));
            }
        }
        return true;
    }
}
