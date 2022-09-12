package com.alpsbte.companion.core;

import com.alpsbte.companion.Companion;
import com.alpsbte.companion.core.config.ConfigPaths;
import com.alpsbte.companion.core.menus.CompanionMenu;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.EditSessionBuilder;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.world.block.BaseBlock;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.flags.Flags;
import com.sk89q.worldguard.protection.regions.RegionQuery;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Openable;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.TrapDoor;

import java.util.logging.Level;

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

            event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.ENTITY_FIREWORK_ROCKET_LAUNCH, 5.0f, 1.0f);
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

        if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            if (event.getItem() != null && event.getItem().isSimilar(SpecialBlocks.BlockUpdateTool) && event.getClickedBlock() != null) {
                Material clickedBlockMaterial = event.getClickedBlock().getType();
                event.getClickedBlock().setType(Material.GRAY_STAINED_GLASS_PANE);
                event.getClickedBlock().setType(clickedBlockMaterial);
                event.getClickedBlock().getState().update(true);
            }
        }

        // Open/Close iron trap door when right-clicking
        if (event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            if (event.getHand() != EquipmentSlot.OFF_HAND) {
                if (!event.getPlayer().isSneaking()){
                    if (event.getClickedBlock() != null && event.getItem() == null && event.getClickedBlock().getType() == Material.IRON_TRAPDOOR) {
                        RegionQuery query = WorldGuard.getInstance().getPlatform().getRegionContainer().createQuery();
                        com.sk89q.worldedit.util.Location loc = BukkitAdapter.adapt(event.getPlayer().getLocation());
                        com.sk89q.worldedit.world.World world = BukkitAdapter.adapt(event.getPlayer().getWorld());
                        if (!WorldGuard.getInstance().getPlatform().getSessionManager().hasBypass(WorldGuardPlugin.inst().wrapPlayer(event.getPlayer()), world)) {
                            if (query.testState(loc, WorldGuardPlugin.inst().wrapPlayer(event.getPlayer()), Flags.INTERACT)) {
                                BlockData data = event.getClickedBlock().getBlockData();

                                if (data instanceof Openable) {
                                    Openable door = (Openable) data;

                                    if (!door.isOpen()) {
                                        door.setOpen(true);
                                        event.getPlayer().playSound(event.getClickedBlock().getLocation(), "block.iron_trapdoor.open", 1f, 1f);
                                    } else {
                                        door.setOpen(false);
                                        event.getPlayer().playSound(event.getClickedBlock().getLocation(), "block.iron_trapdoor.close", 1f, 1f);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void onPlayerMoveEvent(PlayerMoveEvent event) {
        Bukkit.getScheduler().runTaskAsynchronously(Companion.getPlugin(), (() -> event.getPlayer().spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("§c§lHeight: §f§l" + (event.getPlayer().getLocation().getBlockY() + 152) + "m"))));
    }

    @EventHandler
    public void onPlayerBlockPlaceEvent(BlockPlaceEvent event) {
        if(event.canBuild()) {
            ItemStack item = event.getItemInHand();

            if(item.isSimilar(Barrier)) {
                event.getBlockPlaced().setType(Material.BARRIER, true);
            } else if(item.isSimilar(StructureVoid)) {
                event.getBlockPlaced().setType(Material.STRUCTURE_VOID, true);
            }
        }
    }
}
