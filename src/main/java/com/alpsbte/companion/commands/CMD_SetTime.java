package com.alpsbte.companion.commands;

import com.alpsbte.companion.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CMD_SetTime implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if (sender instanceof Player) {
            if (sender.hasPermission("alpsbte.ptime")) {
                Player player = (Player) sender;
                if (args.length == 1) {
                    switch (args[0].toLowerCase()) {
                        case "day":
                            player.setPlayerTime(6000, false);
                            player.sendMessage(Utils.getInfoMessageFormat("Time was set to §6day§a for §6" + player.getName() + "§a."));
                            player.playSound(player.getLocation(), Utils.Done, 1f, 1f);
                            return true;
                        case "night":
                            player.setPlayerTime(18000, false);
                            player.sendMessage(Utils.getInfoMessageFormat("Time was set to §6night§a for §6" + player.getName() + "§a."));
                            player.playSound(player.getLocation(), Utils.Done, 1f, 1f);
                            return true;
                        case "reset":
                            player.resetPlayerTime();
                            player.sendMessage(Utils.getInfoMessageFormat("Time was §6synchronized with the server§a for §6" + player.getName() + "§a."));
                            player.playSound(player.getLocation(), Utils.Done, 1f, 1f);
                            return true;
                        default:
                            if (Utils.tryParseInt(args[0]) != null) {
                                int ticks = Integer.parseInt(args[0]);
                                if (ticks >= 0 && ticks <= 24000) {
                                    player.setPlayerTime(ticks, false);
                                    player.sendMessage(Utils.getInfoMessageFormat("Time was set to §6" + ticks + " ticks§a for §6" + player.getName() + "§a."));
                                    player.playSound(player.getLocation(), Utils.Done, 1f, 1f);
                                    return true;
                                }
                            }
                        break;
                    }
                }
                player.sendMessage(Utils.getErrorMessageFormat("Usage: /ptime <day/night/reset/ticks>"));
            } else {
                sender.sendMessage(Utils.getErrorMessageFormat("You don't have permission to execute this command!"));
            }
        }
        return true;
    }
}
