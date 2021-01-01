package github.BTECompanion.core;

import github.BTECompanion.BTECompanion;
import github.BTECompanion.core.menus.CompanionMenu;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

public class EventListener extends SpecialBlocks implements Listener {

    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent event) {
        Bukkit.broadcastMessage("§7[§6+§7] > " + event.getPlayer().getName());

        FileConfiguration config = BTECompanion.getPlugin().getConfig();

        if(!event.getPlayer().getInventory().contains(CompanionMenu.getItem()) && !config.getBoolean("Companion.command-only")) {
            event.getPlayer().getInventory().setItem(8, CompanionMenu.getItem());
        }

        if(!event.getPlayer().hasPlayedBefore()) {
            event.getPlayer().teleport(new Location(event.getPlayer().getWorld(),
                    config.getDouble("Companion.map-spawn-point.x"),
                    config.getDouble("Companion.map-spawn-point.y"),
                    config.getDouble("Companion.map-spawn-point.z"),
                    (float) config.getDouble("Companion.map-spawn-point.yaw"),
                    (float) config.getDouble("Companion.map-spawn-point.pitch")));

            event.getPlayer().sendMessage("§8§l>> §aWelcome to the countries map! Use the §6pressure plates §ato teleport to the specific location.");
            event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.ENTITY_FIREWORK_LAUNCH, 5.0f, 1.0f);
        }

        event.setJoinMessage(null);
    }

    @EventHandler
    public void onPlayerLeaveEvent(PlayerQuitEvent event) {
        Bukkit.broadcastMessage("§7[§c-§7] > " + event.getPlayer().getName());

        event.setQuitMessage(null);
    }

    @EventHandler
    public void onPlayerInteractEvent(PlayerInteractEvent event) {
        try {
            if(event.getAction().equals(Action.RIGHT_CLICK_BLOCK) || event.getAction().equals(Action.RIGHT_CLICK_AIR)) {
                if(event.getItem() != null && event.getItem().equals(CompanionMenu.getItem())) {
                    new CompanionMenu().getUI().open(event.getPlayer());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @EventHandler
    public void onPlayerBlockPlaceEvent(BlockPlaceEvent event) {
        if(event.canBuild()) {
            ItemStack item = event.getItemInHand();

            if(item.isSimilar(SeamlessSandstone)) {
                event.getBlockPlaced().setTypeIdAndData(43, (byte) 9, true);
            } else if(item.isSimilar(SeamlessStone)) {
                event.getBlockPlaced().setTypeIdAndData(43, (byte) 8, true);
            } else if(item.isSimilar(MushroomStem)) {
                event.getBlockPlaced().setTypeIdAndData(99, (byte) 10, true);
            } else if(item.isSimilar(LightBrownMushroom)) {
                event.getBlockPlaced().setTypeIdAndData(99, (byte) 0, true);
            } else if(item.isSimilar(BarkOakLog)) {
                event.getBlockPlaced().setTypeIdAndData(17, (byte) 12, true);
            } else if(item.isSimilar(BarkSpruceLog)) {
                event.getBlockPlaced().setTypeIdAndData(17, (byte) 13, true);
            } else if(item.isSimilar(BarkBirchLog)) {
                event.getBlockPlaced().setTypeIdAndData(17, (byte) 14, true);
            } else if(item.isSimilar(BarkJungleLog)) {
                event.getBlockPlaced().setTypeIdAndData(17, (byte) 15, true);
            } else if(item.isSimilar(BarkAcaciaLog)) {
                event.getBlockPlaced().setTypeIdAndData(162, (byte) 12, true);
            } else if(item.isSimilar(BarkDarkOakLog)) {
                event.getBlockPlaced().setTypeIdAndData(162, (byte) 13, true);
            }
        }
    }
}
