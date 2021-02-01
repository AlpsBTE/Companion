package github.BTECompanion.core.plotsystem;

import github.BTECompanion.BTECompanion;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;

import java.sql.*;
import java.util.logging.Level;

public class DatabaseConnection {

    private static Connection connection;

    public static void ConnectToDatabase() {
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            System.out.println("MariaDB JDBC Driver Registered!");

            try {
                FileConfiguration config = BTECompanion.getPlugin().getConfig();

                connection = DriverManager.getConnection(
                        "jdbc:mariadb://172.18.0.1:3306/alpsbte",
                        config.getString("PlotSystem.database.username"),
                        config.getString("PlotSystem.database.password"));

                Bukkit.getLogger().log(Level.INFO, "SQL Connection to database established!");
            } catch (SQLException ex) {
                Bukkit.getLogger().log(Level.SEVERE, "Connection Failed!", ex);
            }
        } catch (Exception ex) {
            Bukkit.getLogger().log(Level.SEVERE, "MySQL JDBC Driver not found!", ex);
        }
    }

    public static Connection getConnection() {
        return connection;
    }

    public static Statement createStatement() throws SQLException {
        if(getConnection().isClosed()) {
            ConnectToDatabase();
        }
        return getConnection().createStatement();
    }

    public static PreparedStatement createPreparedStatement(String sql) throws SQLException {
        if(getConnection().isClosed()) {
            ConnectToDatabase();
        }
        return getConnection().prepareStatement(sql);
    }

}
