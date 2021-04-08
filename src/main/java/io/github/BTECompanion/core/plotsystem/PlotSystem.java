package github.BTECompanion.core.plotsystem;

import com.sk89q.worldedit.*;
import com.sk89q.worldedit.bukkit.BukkitUtil;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.data.DataException;
import com.sk89q.worldedit.extent.clipboard.BlockArrayClipboard;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardWriter;
import com.sk89q.worldedit.function.operation.ForwardExtentCopy;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.regions.Polygonal2DRegion;
import com.sk89q.worldedit.regions.Region;
import com.sk89q.worldedit.schematic.SchematicFormat;
import com.sk89q.worldedit.session.ClipboardHolder;
import github.BTECompanion.BTECompanion;
import github.BTECompanion.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;

public class PlotSystem {

    private static final FileConfiguration config = BTECompanion.getPlugin().getConfig();
    private static String path = config.getString("PlotSystem.path");

    private final Location mcCoordinates;
    protected int selectedCityID = -1;

    private int plotID = 1;

    public PlotSystem(Location playerLocation) {
        this.mcCoordinates = playerLocation;
    }

    protected void CreatePlot(Player player, int cityID, int difficultyID) {
        Region plotRegion;

        // Get WorldEdit selection of player
        try {
            plotRegion = WorldEdit.getInstance().getSessionManager().findByName(player.getDisplayName()).getSelection(
                         WorldEdit.getInstance().getSessionManager().findByName(player.getDisplayName()).getSelectionWorld());
        } catch (NullPointerException | IncompleteRegionException ex) {
            player.sendMessage("§7§l>> §cPlease select a WorldEdit selection!");
            return;
        }

        // Conversion
        Polygonal2DRegion polyRegion;
        try {
            // Check if WorldEdit selection is polygonal
            if(plotRegion instanceof Polygonal2DRegion) {
                // Cast WorldEdit region to polygonal region
                polyRegion = (Polygonal2DRegion)plotRegion;

                if(polyRegion.getLength() > 100 || polyRegion.getWidth() > 100 || polyRegion.getHeight() > 30) {
                     player.sendMessage("§7§l>> §cPlease adjust your selection size!");
                     return;
                }

                // Set minimum selection height under player location
                polyRegion.setMinimumY((int) player.getLocation().getY() - 5);

                if(polyRegion.getMaximumY() <= player.getLocation().getY() + 1) {
                    polyRegion.setMaximumY((int) player.getLocation().getY() + 1);
                }
            } else {
                 player.sendMessage("§7§l>> §cPlease use poly selection to create a new plot!");
                 return;
            }
        } catch (Exception ex) {
             Bukkit.getLogger().log(Level.SEVERE, "An error occurred while creating a new plot!", ex);
             player.sendMessage("§7§l>> §cAn error occurred while creating plot!");
             return;
        }

        // Saving schematic
        try (Connection connection = Utils.getConnection()) {
            ResultSet rs = connection.createStatement().executeQuery("SELECT (t1.idplot + 1) as firstID FROM plots t1 " +
                    "WHERE NOT EXISTS (SELECT t2.idplot FROM plots t2 WHERE t2.idplot = t1.idplot + 1)");
            if(rs.next()) {
                plotID = rs.getInt(1);
            }

            String filePath = Paths.get(path, String.valueOf(cityID), plotID + ".schematic").toString();
            File schematic = new File(filePath);

            boolean createdDirectory = schematic.getParentFile().mkdirs();
            Bukkit.getLogger().log(Level.INFO, "Created new Directory (" + schematic.getParentFile().getName() + "): " + createdDirectory);

            boolean createdFile = schematic.createNewFile();
            Bukkit.getLogger().log(Level.INFO, "Created new File (" + schematic.getName() + "): " + createdFile);

            WorldEditPlugin worldEdit = (WorldEditPlugin) Bukkit.getServer().getPluginManager().getPlugin("WorldEdit");

            Clipboard cb = new BlockArrayClipboard(polyRegion);
            cb.setOrigin(cb.getRegion().getCenter());
            LocalSession playerSession = WorldEdit.getInstance().getSessionManager().findByName(player.getDisplayName());
            ForwardExtentCopy copy = new ForwardExtentCopy(playerSession.createEditSession(worldEdit.wrapPlayer(player)), polyRegion, cb, polyRegion.getMinimumPoint());
            Operations.completeLegacy(copy);

            try(ClipboardWriter writer = ClipboardFormat.SCHEMATIC.getWriter(new FileOutputStream(schematic, false))) {
                writer.write(cb, polyRegion.getWorld().getWorldData());
            }

            rs.close();
        } catch (Exception ex) {
            Bukkit.getLogger().log(Level.SEVERE, "An error occurred while saving new plot to a schematic!", ex);
            player.sendMessage("§7§l>> §cAn error occurred while creating plot!");
            return;
        }

        // Save to database
        try (Connection connection = Utils.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO plots (idplot, idcity, mcCoordinates, iddifficulty) VALUES (?, ?, ?, ?)");

            statement.setInt(1, plotID);
            statement.setInt(2, cityID);
            statement.setString(3, mcCoordinates.getX() + "," + mcCoordinates.getY() + "," + mcCoordinates.getZ());
            statement.setInt(4, difficultyID);

            statement.execute();

            player.sendMessage("§7§l>> §aSuccessfully created new plot!§7 (City: " + cityID + " | ID: " + plotID + ")");
            player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);

            statement.close();
        } catch (Exception ex) {
            Bukkit.getLogger().log(Level.SEVERE, "An error occurred while saving new plot to database!", ex);
            player.sendMessage("§7§l>> §cAn error occurred while creating plot!");

            try {
                Files.deleteIfExists(Paths.get(path));
            } catch (IOException e) {
                Bukkit.getLogger().log(Level.SEVERE, "An error occurred while deleting schematic!", ex);
            }
        }
    }

    public Location getMcCoordinates() { return mcCoordinates; }

    public static void pastePlotSchematic(int plotID, int cityID, Vector mcCoordinates) throws IOException, DataException, MaxChangedBlocksException {
        File file = Paths.get(path, "finishedPlots", String.valueOf(cityID), plotID + ".schematic").toFile();

        EditSession editSession = new EditSession(new BukkitWorld(Bukkit.getWorld("Terra")), -1);
        editSession.enableQueue();

        SchematicFormat schematicFormat = SchematicFormat.getFormat(file);
        CuboidClipboard clipboard = schematicFormat.load(file);

        clipboard.paste(editSession, mcCoordinates, true);
        editSession.flushQueue();

        try (Connection connection = Utils.getConnection()){
            PreparedStatement ps = connection.prepareStatement("UPDATE plots SET isPasted = '1' WHERE idplot = '" + plotID + "'");
            ps.executeUpdate();
        } catch (SQLException ex) {
            Bukkit.getLogger().log(Level.SEVERE, "A SQL error occurred!", ex);
        }
    }
}
