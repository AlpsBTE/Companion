package github.BTECompanion.core.plotsystem;

import com.sk89q.worldedit.Vector;
import github.BTECompanion.BTECompanion;
import github.BTECompanion.utils.Utils;
import org.bukkit.Bukkit;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;

public class PlotPlacement extends Thread {

    @Override
    public void run() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(BTECompanion.getPlugin(), () -> {
            try (Connection connection = Utils.getConnection()) {
                ResultSet rs = connection.createStatement().executeQuery("SELECT idplot, idcity, mcCoordinates FROM plots WHERE status = 'complete' AND isPasted = '0' LIMIT 20");
                int pastedPlots = 0;

                if(!rs.wasNull()) {
                    Bukkit.broadcastMessage("§7§l>> §aStarting to paste plots...");
                }

                while (rs.next()) {
                    int plotID = rs.getInt(1);
                    int cityID = rs.getInt(2);

                    String[] splitCoordinates = rs.getString(3).split(",");

                    if(splitCoordinates.length == 3) {
                        Vector mcCoordinates = Vector.toBlockPoint(
                                Float.parseFloat(splitCoordinates[0]),
                                Float.parseFloat(splitCoordinates[1]),
                                Float.parseFloat(splitCoordinates[2])
                        );

                        PlotSystem.pastePlotSchematic(plotID, cityID, mcCoordinates);
                        pastedPlots++;
                    } else {
                        Bukkit.getLogger().log(Level.SEVERE, "Unable to paste plot #" + plotID + " because it has invalid coordinates!");
                    }
                }

                if(pastedPlots != 0) {
                    Bukkit.broadcastMessage("§7§l>> §aPasted §6" + pastedPlots + " §aplots!");
                }
            } catch (SQLException ex) {
                Bukkit.getLogger().log(Level.SEVERE, "A SQL error occurred!", ex);
            } catch (Exception ex) {
                Bukkit.getLogger().log(Level.SEVERE, "A unknown error occurred!", ex);
            }
        }, 0L, 20 * 60 * 10); // Check every 10 min.
    }
}
