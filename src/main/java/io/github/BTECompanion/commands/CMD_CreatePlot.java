package github.BTECompanion.commands;

import github.BTECompanion.core.menus.PlotSystemMenu;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.SQLException;

public class CMD_CreatePlot implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {

       if(sender instanceof Player) {
           try {
               Player player = (Player)sender;
               new PlotSystemMenu(player).getUI().open(player);
           } catch (SQLException ex) {
               ex.printStackTrace();
           }
       }
        return true;
    }
}
