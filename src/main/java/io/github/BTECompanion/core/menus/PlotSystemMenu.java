package github.BTECompanion.core.menus;

import github.BTECompanion.core.plotsystem.CityProject;
import github.BTECompanion.core.plotsystem.PlotSystem;
import github.BTECompanion.utils.ItemBuilder;
import github.BTECompanion.utils.LoreBuilder;
import github.BTECompanion.utils.Utils;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.ipvp.canvas.Menu;
import org.ipvp.canvas.mask.BinaryMask;
import org.ipvp.canvas.mask.Mask;
import org.ipvp.canvas.type.ChestMenu;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PlotSystemMenu extends PlotSystem {

    private final Menu createPlotMenu = ChestMenu.builder(6).title("Create Plot").redraw(true).build();
    private final Menu difficultyMenu = ChestMenu.builder(3).title("Select Plot Difficulty").redraw(true).build();

    private final List<CityProject> cityProjects = getCityProjects();

    public PlotSystemMenu(Player player) throws SQLException {
        super(player.getLocation());
    }

    public Menu getCityProjectUI() throws SQLException {
        // Set glass border
        Mask mask = BinaryMask.builder(createPlotMenu)
                .item(new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (byte)7).setName(" ").build())
                .pattern("111101111") // First row
                .pattern("000000000") // Second row
                .pattern("000000000") // Third row
                .pattern("000000000") // Fourth row
                .pattern("000000000") // Fifth row
                .pattern("111010111")
                .build(); // Sixth row
        mask.apply(createPlotMenu);

        createPlotMenu.getSlot(4).setItem(getStats());

        for(int i = 0; i < cityProjects.size(); i++) {
            int cityID = i;
            createPlotMenu.getSlot(9 + i).setClickHandler((clickPlayer, clickInformation) -> {
                if(selectedCityID != cityID) {

                    selectedCityID = cityID;
                    createPlotMenu.getSlot(4).setItem(getStats());

                    clickPlayer.playSound(clickPlayer.getLocation(), Sound.ENTITY_ITEM_PICKUP, 5.0f, 1.0f);
                }
            });
        }

        createPlotMenu.getSlot(48).setItem(
                new ItemBuilder(Material.WOOL, 1, (byte) 13)
                    .setName("§a§lContinue")
                    .build());
        createPlotMenu.getSlot(48).setClickHandler((clickPlayer, clickInformation) -> {
            clickPlayer.closeInventory();
            getDifficultyMenu().open(clickPlayer);
        });

        createPlotMenu.getSlot(50).setItem(
                new ItemBuilder(Material.WOOL, 1, (byte) 14)
                        .setName("§c§lCancel")
                        .build());
        createPlotMenu.getSlot(50).setClickHandler((clickPlayer, clickInformation) -> {
            clickPlayer.closeInventory();
        });

        return createPlotMenu;
    }

    public Menu getDifficultyMenu() {
        int cityID = cityProjects.get(selectedCityID).getID();

        // Set glass border
        Mask mask = BinaryMask.builder(createPlotMenu)
                .item(new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (byte)7).setName(" ").build())
                .pattern("111111111") // First row
                .pattern("000000000") // Second row
                .pattern("111111111") // Third row
                .build();
        mask.apply(difficultyMenu);

        difficultyMenu.getSlot(10).setItem(
                new ItemBuilder(Material.WOOL, 1, (byte) 5)
                    .setName("§a§lEasy")
                    .build()
        );
        difficultyMenu.getSlot(10).setClickHandler((clickPlayer, clickInformation) -> {
            clickPlayer.closeInventory();
            CreatePlot(clickPlayer, cityID, 1);
        });

        difficultyMenu.getSlot(13).setItem(
                new ItemBuilder(Material.WOOL, 1, (byte) 1)
                        .setName("§6§lMedium")
                        .build()
        );
        difficultyMenu.getSlot(13).setClickHandler((clickPlayer, clickInformation) -> {
            clickPlayer.closeInventory();
            CreatePlot(clickPlayer, cityID, 2);
        });

        difficultyMenu.getSlot(16).setItem(
                new ItemBuilder(Material.WOOL, 1, (byte) 14)
                        .setName("§c§lHard")
                        .build()
        );
        difficultyMenu.getSlot(16).setClickHandler((clickPlayer, clickInformation) -> {
            clickPlayer.closeInventory();
            CreatePlot(clickPlayer, cityID, 3);
        });

        return difficultyMenu;
    }

    private List<CityProject> getCityProjects() throws SQLException {
        List<CityProject> listProjects = new ArrayList<>();

        try (Connection connection = Utils.getConnection()) {
            ResultSet rs = connection.createStatement().executeQuery("SELECT idcityProject, name, country FROM cityProjects WHERE visible = '1'");

            int counter = 0;

            while (rs.next()) {
                int cityID = rs.getInt("idcityProject");
                String name = rs.getString("name");

                CityProject city = null;

                switch (rs.getString("country")){
                    case "AT":
                        city = new CityProject(cityID, name, "4397");
                        break;
                    case "CH":
                        city = new CityProject(cityID, name, "32348");
                        break;
                    case "LI":
                        city = new CityProject(cityID, name, "26174");
                        break;
                    case "IT":
                        city = new CityProject(cityID, name, "21903");
                }

                createPlotMenu.getSlot(9 + counter).setItem(city.getItem());
                listProjects.add(city);
                counter++;
            }
            rs.close();
        }

        return listProjects;
    }

    private ItemStack getStats() {
        return new ItemBuilder((selectedCityID == -1) ? new ItemStack(Material.NAME_TAG) : cityProjects.get(selectedCityID).getItem())
                .setName("§6§lSTATS")
                .setLore(new LoreBuilder()
                        .description("§bX: §7" + (int) getMcCoordinates().getX(),
                                     "§bY: §7" + (int) getMcCoordinates().getY(),
                                     "§bZ: §7" + (int) getMcCoordinates().getZ(),
                                     "§bCity: §7" + ((selectedCityID != -1) ? cityProjects.get(selectedCityID).getName() : "none"))
                        .build())
                .build();
    }
}
