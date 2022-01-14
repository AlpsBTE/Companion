package com.alpsbte.companion.commands;

import com.alpsbte.companion.utils.Utils;
import org.bukkit.WeatherType;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CMD_SetWeather implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if (sender instanceof Player) {
            if (sender.hasPermission("alpsbte.pweather")) {
                Player player = (Player) sender;
                if (args.length == 1) {
                    switch (args[0].toLowerCase()) {
                        case "rain":
                            player.setPlayerWeather(WeatherType.DOWNFALL);
                            player.sendMessage(Utils.getInfoMessageFormat("Weather was set to §6rain§a for §6" + player.getName() + "§a."));
                            player.playSound(player.getLocation(), Utils.Done, 1f, 1f);
                            return true;
                        case "clear":
                            player.setPlayerWeather(WeatherType.CLEAR);
                            player.sendMessage(Utils.getInfoMessageFormat("Weather was set to §6clear§a for §6" + player.getName() + "§a."));
                            player.playSound(player.getLocation(), Utils.Done, 1f, 1f);
                            return true;
                        case "reset":
                            player.resetPlayerWeather();
                            player.sendMessage(Utils.getInfoMessageFormat("Weather was §6synchronized with the server§a for §6" + player.getName() + "§a."));
                            player.playSound(player.getLocation(), Utils.Done, 1f, 1f);
                            return true;
                    }
                }
                player.sendMessage(Utils.getErrorMessageFormat("Usage: /pweather <rain/clear/reset>"));
            } else {
                sender.sendMessage(Utils.getErrorMessageFormat("You don't have permission to execute this command!"));
            }
        }
        return true;
    }
}
