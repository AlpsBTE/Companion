package github.BTECompanion.core.menus;

import github.BTECompanion.BTECompanion;
import github.BTECompanion.core.menus.SpecialBlocksMenu;
import github.BTECompanion.utils.ItemBuilder;
import github.BTECompanion.utils.LoreBuilder;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.ipvp.canvas.Menu;
import org.ipvp.canvas.mask.BinaryMask;
import org.ipvp.canvas.mask.Mask;
import org.ipvp.canvas.type.ChestMenu;

public class CompanionMenu {

    public Menu getUI() {
        Menu companionMenu = ChestMenu.builder(3).title("Companion Menu").redraw(true).build();
        FileConfiguration config = BTECompanion.getPlugin().getConfig();

        // Set glass border
        Mask mask = BinaryMask.builder(companionMenu)
                .item(new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (byte)7).setName(" ").build())
                .pattern("111101111") // First row
                .pattern("000000000") // Second row
                .pattern("111101111").build(); // Third row
        mask.apply(companionMenu);



        // Set HUB item
        companionMenu.getSlot(4).setItem(new ItemBuilder(Material.NETHER_STAR, 1)
                .setName("§6§lHUB")
                .setEnchantment(Enchantment.ARROW_DAMAGE)
                .setItemFlag(ItemFlag.HIDE_ENCHANTS)
                .setLore(new LoreBuilder()
                        .server(BTECompanion.getPlugin().checkServer(config.getString("Companion.IP"), config.getInt("Companion.port")))
                        .build())
                .build());

        companionMenu.getSlot(4).setClickHandler((player, clickInformation) -> {
            player.closeInventory();

            player.sendMessage("§8§l>> §aConnecting to server");
            BTECompanion.getPlugin().connectPlayer(player, "HUB");
        });



        // [1] Set HeadDB item
        companionMenu.getSlot(9).setItem(new ItemBuilder(Material.SKULL_ITEM, 1, (byte) 3)
                .setName("§b§lCUSTOM HEADS")
                .setLore(new LoreBuilder().description("Open the head menu to get a variety of custom heads.").build())
                .build());

        companionMenu.getSlot(9).setClickHandler((player, clickInformation) -> {
            player.closeInventory();
            player.performCommand("headdb");
        });



        // [2] Set teleporting item to the trees platform
        companionMenu.getSlot(11).setItem(new ItemBuilder(Material.LEAVES, 1)
                .setName("§b§lCUSTOM TREES")
                .setLore(new LoreBuilder().description("Teleport to the trees platform to get a variety of custom trees for building.").build())
                .build());

        companionMenu.getSlot(11).setClickHandler((player, clickInformation) -> {
            player.closeInventory();

            player.teleport(new Location(player.getWorld(),
                    config.getDouble("Companion.trees-spawn-point.x"),
                    config.getDouble("Companion.trees-spawn-point.y"),
                    config.getDouble("Companion.trees-spawn-point.z"),
                    (float) config.getDouble("Companion.trees-spawn-point.yaw"),
                    (float) config.getDouble("Companion.trees-spawn-point.pitch")));

            player.sendMessage("§8§l>> §aWelcome to the custom trees platform! Select a tree and §6//copy & //paste §ait to use it.");
            player.playSound(player.getLocation(), Sound.ENTITY_FIREWORK_LAUNCH, 5.0f, 1.0f);
        });



        // [3] Set player effects item
        companionMenu.getSlot(13).setItem(new ItemBuilder(Material.SPECTRAL_ARROW, 1)
                .setName("§b§lPLAYER EFFECTS")
                .setLore(new LoreBuilder().description("Open the player effects menu to adjust your speed and night vision.").build())
                .build());

        companionMenu.getSlot(13).setClickHandler((player, clickInformation) -> {
            player.closeInventory();

            player.sendMessage("§8§l>> §6Coming Soon!");
        });



        // [4] Set banner maker menu item
        companionMenu.getSlot(15).setItem(new ItemBuilder(Material.BANNER, 1, (byte) 14)
                .setName("§b§lBANNER MAKER")
                .setLore(new LoreBuilder().description("Open the banner maker menu to create your own custom banners.").build())
                .build());

        companionMenu.getSlot(15).setClickHandler((player, clickInformation) -> {
            player.closeInventory();

            player.performCommand("bm");
        });



        // [5] Set special blocks item
        companionMenu.getSlot(17).setItem(SpecialBlocksMenu.getItem());

        companionMenu.getSlot(17).setClickHandler((player, clickInformation) -> {
            player.closeInventory();

            new SpecialBlocksMenu().getUI().open(player);
        });



        // Set map item
        companionMenu.getSlot(22).setItem(new ItemBuilder(Material.MAP, 1)
                .setName("§b§lMAP")
                .setLore(new LoreBuilder()
                        .description("Teleport to the countries map to visit already built areas.")
                        .build())
                .build());

        companionMenu.getSlot(22).setClickHandler((player, clickInformation) -> {
            player.closeInventory();

            player.teleport(new Location(player.getWorld(),
                    config.getDouble("Companion.map-spawn-point.x"),
                    config.getDouble("Companion.map-spawn-point.y"),
                    config.getDouble("Companion.map-spawn-point.z"),
                    (float) config.getDouble("Companion.map-spawn-point.yaw"),
                    (float) config.getDouble("Companion.map-spawn-point.pitch")));

            player.sendMessage("§8§l>> §aWelcome to the countries map! Use the §6pressure plates §ato teleport to the specific location.");
            player.playSound(player.getLocation(), Sound.ENTITY_FIREWORK_LAUNCH, 5.0f, 1.0f);
        });



        return companionMenu;
    }

    public static ItemStack getItem() {
        return new ItemBuilder(Material.NETHER_STAR, 1)
                .setName("§b§lCompanion §7(Right Click)")
                .setEnchantment(Enchantment.ARROW_DAMAGE)
                .setItemFlag(ItemFlag.HIDE_ENCHANTS)
                .build();
    }
}
