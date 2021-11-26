package com.alpsbte.companion.core.menus;

import com.alpsbte.companion.utils.ItemBuilder;
import com.alpsbte.companion.utils.LoreBuilder;
import com.alpsbte.companion.utils.Utils;
import org.bukkit.Material;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.ipvp.canvas.Menu;
import org.ipvp.canvas.mask.BinaryMask;
import org.ipvp.canvas.mask.Mask;
import org.ipvp.canvas.slot.Slot;
import org.ipvp.canvas.type.ChestMenu;

public class EffectsMenu {

    public Menu getUI() {
        Menu playerEffectsMenu = ChestMenu.builder(3).title("Player Effects").redraw(true).build();

        // Set bottom glass border
        Mask mask = BinaryMask.builder(playerEffectsMenu)
                .item(new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (byte)7).setName(" ").build())
                .pattern("111111111") // First row
                .pattern("000000000") // Second row
                .pattern("111101111").build(); // Third row
        mask.apply(playerEffectsMenu);

        // Set close button item
        playerEffectsMenu.getSlot(22).setItem(new ItemBuilder(Material.BARRIER, 1)
                .setName("§6§lCLOSE")
                .build());

        playerEffectsMenu.getSlot(22).setClickHandler((p, clickInformation) -> {
            p.closeInventory();

            new CompanionMenu().getUI().open(p);
        });

        for (int i = 9; i < 17; i++) {
            Slot slot = playerEffectsMenu.getSlot(i);

            switch (i) {
                // Speed 1
                case 10:
                    slot.setItem(new ItemBuilder(Material.FEATHER,1)
                            .setName("§6§lSpeed 1")
                            .setLore(new LoreBuilder().description("Set your flying speed to 1").build())
                            .build());

                    slot.setClickHandler((player, clickInformation) -> {
                        player.performCommand("speed 1");
                    });
                    break;
                // Speed 2
                case 11:
                    slot.setItem(new ItemBuilder(Material.FEATHER,2)
                            .setName("§6§lSpeed 2")
                            .setLore(new LoreBuilder().description("Set your flying speed to 2").build())
                            .build());

                    slot.setClickHandler((player, clickInformation) -> {
                        player.performCommand("speed 2");
                    });
                    break;
                // Speed 3
                case 12:
                    slot.setItem(new ItemBuilder(Material.FEATHER,3)
                            .setName("§6§lSpeed 3")
                            .setLore(new LoreBuilder().description("Set your flying speed to 3").build())
                            .build());

                    slot.setClickHandler((player, clickInformation) -> {
                        player.performCommand("speed 3");
                    });
                    break;
                // Apply Night Vision
                case 15:
                    slot.setItem(new ItemBuilder(Material.EYE_OF_ENDER,1)
                            .setName("§6§lApply Night Vision")
                            .setLore(new LoreBuilder().description("Add a permanent Night Vision effect").build())
                            .build());

                    slot.setClickHandler((player, clickInformation) -> {
                        player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, Integer.MAX_VALUE,-1),false);
                        player.sendMessage(Utils.getInfoMessageFormat("Successfully added Night Vision effect"));
                    });
                    break;
                // Remove Night Vision
                case 16:
                    slot.setItem(new ItemBuilder(Material.ENDER_PEARL,1)
                            .setName("§6§lRemove Night Vision")
                            .setLore(new LoreBuilder().description("Remove any remaining Night Vision effect").build())
                            .build());

                    slot.setClickHandler((player, clickInformation) -> {
                        player.removePotionEffect(PotionEffectType.NIGHT_VISION);
                        player.sendMessage(Utils.getInfoMessageFormat("Successfully removed Night Vision effect"));
                    });
                    break;
            }
        }



        return playerEffectsMenu;
    }
}
