package github.BTECompanion.commands;

import com.sk89q.worldedit.Vector;
import github.BTECompanion.core.plotsystem.PlotSystem;
import github.BTECompanion.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.sql.Connection;
import java.sql.ResultSet;

public class CMD_PastePlot implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {

            if(sender.hasPermission("alpsbte.createplot")) {
                if(args.length == 1) {
                    try {
                        int ID = Integer.parseInt(args[0]);

                        try (Connection connection = Utils.getConnection()) {
                            ResultSet rs = connection.createStatement().executeQuery("SELECT idplot, idcity, mcCoordinates FROM plots WHERE status = 'complete' AND isPasted = '0'");

                            while (rs.next()) {
                                int plotID = rs.getInt(1);
                                if(ID == plotID) {
                                    String[] splitCoordinates = rs.getString(3).split(",");

                                    if(splitCoordinates.length == 3) {
                                        Vector mcCoordinates = Vector.toBlockPoint(
                                                Float.parseFloat(splitCoordinates[0]),
                                                Float.parseFloat(splitCoordinates[1]),
                                                Float.parseFloat(splitCoordinates[2])
                                        );

                                        PlotSystem.pastePlotSchematic(plotID, rs.getInt(2), mcCoordinates);
                                        sender.hasPermission("§7§l>> §aSuccessfully pasted plot with the ID §6#" + plotID + "§a!");
                                    } else {
                                        sender.sendMessage("§7§l>> §cThis plot doesn't have a Y coordinate!");
                                    }
                                }
                            }
                        }
                    } catch (Exception ex) {
                        sender.sendMessage("§7§l>> §cUsage: /pasteplot <ID>");
                    }
                } else {
                    sender.sendMessage("§7§l>> §cUsage: /pasteplot <ID>");
                }
            }
        return true;
    }
}
