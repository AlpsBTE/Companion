package com.alpsbte.companion.core;

import com.alpsbte.companion.Companion;
import com.alpsbte.companion.core.config.ConfigPaths;
import com.alpsbte.companion.core.menus.CompanionMenu;
import com.alpsbte.companion.utils.Utils;
import com.sk89q.worldguard.bukkit.RegionContainer;
import com.sk89q.worldguard.bukkit.RegionQuery;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.flags.DefaultFlag;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.BlockState;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.TrapDoor;

public class EventListener extends SpecialBlocks implements Listener {

    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent event) {
        event.setJoinMessage(null);
        FileConfiguration config = Companion.getPlugin().getConfigManager().getConfig();

        event.getPlayer().getInventory().setItem(8, CompanionMenu.getItem());

        if(!event.getPlayer().hasPlayedBefore()) {
            event.getPlayer().teleport(new Location(event.getPlayer().getWorld(),
                    config.getDouble(ConfigPaths.SPAWN_POINTS_MAP_X),
                    config.getDouble(ConfigPaths.SPAWN_POINTS_MAP_Y),
                    config.getDouble(ConfigPaths.SPAWN_POINTS_MAP_Z),
                    (float) config.getDouble(ConfigPaths.SPAWN_POINTS_MAP_YAW),
                    (float) config.getDouble(ConfigPaths.SPAWN_POINTS_MAP_PITCH)));

            event.getPlayer().sendMessage(Utils.getInfoMessageFormat("Use the §6pressure plates §ato teleport to the specific location."));
            event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.ENTITY_FIREWORK_LAUNCH, 5.0f, 1.0f);
        }
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

        // Open/Close iron trap door when right-clicking
        if (event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            if (event.getHand() != EquipmentSlot.OFF_HAND) {
                if (!event.getPlayer().isSneaking()){
                    if (event.getClickedBlock() != null && event.getItem() == null && event.getClickedBlock().getType() == Material.IRON_TRAPDOOR) {
                        WorldGuardPlugin worldGuard = WorldGuardPlugin.inst();
                        RegionContainer regionContainer = worldGuard.getRegionContainer();
                        RegionQuery query = regionContainer.createQuery();

                        if (query.testBuild(event.getPlayer().getLocation(), worldGuard.wrapPlayer(event.getPlayer()), DefaultFlag.INTERACT)) {
                            BlockState state = event.getClickedBlock().getState();
                            TrapDoor tp = (TrapDoor) state.getData();

                            if (!tp.isOpen()) {
                                tp.setOpen(true);
                                event.getPlayer().playSound(event.getClickedBlock().getLocation(), "block.iron_trapdoor.open", 1f, 1f);
                            } else {
                                tp.setOpen(false);
                                event.getPlayer().playSound(event.getClickedBlock().getLocation(), "block.iron_trapdoor.close", 1f, 1f);
                            }
                            state.update();
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void onPlayerBlockPlaceEvent(BlockPlaceEvent event) {
        if(event.canBuild()) {
            ItemStack item = event.getItemInHand();

            if(item.isSimilar(SeamlessSandstone)) {
                event.getBlockPlaced().setTypeIdAndData(43, (byte) 9, true);
            } else if(item.isSimilar(SeamlessRedSandstone)) {
                event.getBlockPlaced().setTypeIdAndData(181, (byte) 12, true);
            } else if(item.isSimilar(SeamlessStone)) {
                event.getBlockPlaced().setTypeIdAndData(43, (byte) 8, true);
            } else if(item.isSimilar(SeamlessMushroomStem)) {
                event.getBlockPlaced().setTypeIdAndData(99, (byte) 15, true);
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
