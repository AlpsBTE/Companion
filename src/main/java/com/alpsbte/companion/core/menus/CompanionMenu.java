package com.alpsbte.companion.core.menus;

import com.alpsbte.companion.Companion;
import com.alpsbte.companion.core.config.ConfigPaths;
import com.alpsbte.companion.utils.ItemBuilder;
import com.alpsbte.companion.utils.LoreBuilder;
import com.alpsbte.companion.utils.Utils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.ipvp.canvas.Menu;
import org.ipvp.canvas.mask.BinaryMask;
import org.ipvp.canvas.mask.Mask;
import org.ipvp.canvas.type.ChestMenu;

public class CompanionMenu {

    public Menu getUI() {
        Menu companionMenu = ChestMenu.builder(3).title("Companion Menu").redraw(true).build();
        FileConfiguration config = Companion.getPlugin().getConfigManager().getConfig();

        // Set glass border
        Mask mask = BinaryMask.builder(companionMenu)
                .item(new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE, 1).setName(" ").build())
                .pattern("111101111") // First row
                .pattern("000000000") // Second row
                .pattern("111101111").build(); // Third row
        mask.apply(companionMenu);



        // Set HUB item
        companionMenu.getSlot(4).setItem(new ItemBuilder(Material.NETHER_STAR, 1)
                .setName("§6§lHUB")
                .setEnchantment(true)
                .setItemFlag(ItemFlag.HIDE_ENCHANTS)
                .setLore(new LoreBuilder()
                        .server(Companion.getPlugin().checkServer(config.getString(ConfigPaths.HUB_IP), config.getInt(ConfigPaths.HUB_PORT)))
                        .build())
                .build());

        companionMenu.getSlot(4).setClickHandler((player, clickInformation) -> {
            player.closeInventory();

            player.sendMessage(Utils.getInfoMessageFormat("Connecting to server..."));
            Companion.getPlugin().connectPlayer(player, "ALPS-1");
        });



        // [1] Set HeadDB item
        companionMenu.getSlot(10).setItem(new ItemBuilder(Material.PLAYER_HEAD, 1, (byte) 3)
                .setName("§b§lCUSTOM HEADS")
                .setLore(new LoreBuilder().description("Open the head menu to get a variety of custom heads for building.").build())
                .build());

        companionMenu.getSlot(10).setClickHandler((player, clickInformation) -> {
            player.closeInventory();
            player.performCommand("headdb");
        });



        // [2] Set teleporting item to the trees platform
/*        companionMenu.getSlot(11).setItem(new ItemBuilder(Material.OAK_LEAVES, 1)
                .setName("§b§lCUSTOM TREES")
                .setLore(new LoreBuilder().description("Teleport to the trees platform to get a variety of custom trees and bushes for building.").build())
                .build());

        companionMenu.getSlot(11).setClickHandler((player, clickInformation) -> {
            player.closeInventory();

            player.teleport(new Location(player.getWorld(),
                    config.getDouble(ConfigPaths.SPAWN_POINTS_TREES_X),
                    config.getDouble(ConfigPaths.SPAWN_POINTS_TREES_Y),
                    config.getDouble(ConfigPaths.SPAWN_POINTS_TREES_Z),
                    (float) config.getDouble(ConfigPaths.SPAWN_POINTS_TREES_YAW),
                    (float) config.getDouble(ConfigPaths.SPAWN_POINTS_TREES_PITCH)));

            player.sendMessage(Utils.getInfoMessageFormat("Select a tree or bush and copy it using §6//copy§a."));
            player.playSound(player.getLocation(), Sound.ENTITY_FIREWORK_LAUNCH, 5.0f, 1.0f);
        });*/



        // [3] Set player effects item
        companionMenu.getSlot(12).setItem(new ItemBuilder(Material.SPECTRAL_ARROW, 1)
                .setName("§b§lPLAYER EFFECTS")
                .setLore(new LoreBuilder().description("Open the player effects menu to adjust your speed and night vision.").build())
                .build());

        companionMenu.getSlot(12).setClickHandler((player, clickInformation) -> {
            player.closeInventory();
            new EffectsMenu().getUI().open(player);
        });



        // [4] Set banner maker menu item
        companionMenu.getSlot(14).setItem(new ItemBuilder(Material.ORANGE_BANNER, 1)
                .setName("§b§lBANNER MAKER")
                .setLore(new LoreBuilder().description("Open the banner maker menu to create and manage your own custom banners.").build())
                .build());

        companionMenu.getSlot(14).setClickHandler((player, clickInformation) -> {
            player.closeInventory();
            player.performCommand("bm");
        });



        // [5] Set special blocks item
        companionMenu.getSlot(16).setItem(SpecialBlocksMenu.getItem());

        companionMenu.getSlot(16).setClickHandler((player, clickInformation) -> {
            player.closeInventory();
            new SpecialBlocksMenu().getUI().open(player);
        });



        // Set map item
        companionMenu.getSlot(22).setItem(new ItemBuilder(Material.MAP, 1)
                .setName("§b§lMAP")
                .setLore(new LoreBuilder()
                        .description("Teleport to the spawn.")
                        .build())
                .build());

        companionMenu.getSlot(22).setClickHandler((player, clickInformation) -> {
            player.closeInventory();

            player.teleport(new Location(player.getWorld(),
                    config.getDouble(ConfigPaths.SPAWN_POINTS_MAP_X),
                    config.getDouble(ConfigPaths.SPAWN_POINTS_MAP_Y),
                    config.getDouble(ConfigPaths.SPAWN_POINTS_MAP_Z),
                    (float) config.getDouble(ConfigPaths.SPAWN_POINTS_MAP_YAW),
                    (float) config.getDouble(ConfigPaths.SPAWN_POINTS_MAP_PITCH)));

            player.sendMessage(Utils.getInfoMessageFormat("Use the §6pressure plates §ato teleport to the specific location."));
            player.playSound(player.getLocation(), Sound.ENTITY_FIREWORK_ROCKET_LAUNCH, 5.0f, 1.0f);
        });



        return companionMenu;
    }

    public static ItemStack getItem() {
        return new ItemBuilder(Material.NETHER_STAR, 1)
                .setName("§b§lCompanion §7(Right Click)")
                .setEnchantment(true)
                .setItemFlag(ItemFlag.HIDE_ENCHANTS)
                .build();
    }
}
