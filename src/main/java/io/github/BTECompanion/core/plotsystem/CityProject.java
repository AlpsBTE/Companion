package github.BTECompanion.core.plotsystem;

import github.BTECompanion.utils.ItemBuilder;
import github.BTECompanion.utils.LoreBuilder;
import me.arcaniax.hdb.api.HeadDatabaseAPI;
import org.bukkit.inventory.ItemStack;

public class CityProject {

    private final static HeadDatabaseAPI hdbAPI = new HeadDatabaseAPI();

    private final int ID;
    private final String name;
    private final String headID;

    public CityProject(int ID, String name, String headID) {
        this.ID = ID;
        this.name = name;
        this.headID = headID;
    }

    public int getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public ItemStack getItem() {
        return new ItemBuilder(hdbAPI.getItemHead(headID))
                .setName("§b§l" + name)
                .setLore(new LoreBuilder()
                    .description("§bID: §7" + getID())
                    .build())
                .build();
    }
}
