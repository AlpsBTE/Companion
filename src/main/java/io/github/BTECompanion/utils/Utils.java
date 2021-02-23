package github.BTECompanion.utils;

import github.BTECompanion.BTECompanion;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;

public class Utils {

    private static boolean driverIsRegistered;

    static {
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            Bukkit.getLogger().log(Level.INFO, "MariaDB JDBC Driver Registered!");
            driverIsRegistered = true;
        } catch (ClassNotFoundException ex) {
            Bukkit.getLogger().log(Level.SEVERE, "Could not register MariaDB JDBC Driver!", ex);
        }
    }

    public static Connection getConnection() {
        try {
            if(driverIsRegistered) {
                FileConfiguration config = BTECompanion.getPlugin().getConfig();

                return DriverManager.getConnection(
                        "jdbc:mariadb://172.18.0.1:3306/alpsbte",
                        config.getString("PlotSystem.database.username"),
                        config.getString("PlotSystem.database.password"));
            }
        } catch (SQLException ex) {
            Bukkit.getLogger().log(Level.SEVERE, "Connection Failed!", ex);
        }
        return null;
    }
}
