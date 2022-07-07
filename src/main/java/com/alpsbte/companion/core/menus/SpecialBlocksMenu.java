package com.alpsbte.companion.core.menus;

import com.alpsbte.companion.core.SpecialBlocks;
import com.alpsbte.companion.utils.ItemBuilder;
import com.alpsbte.companion.utils.LoreBuilder;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.ipvp.canvas.Menu;
import org.ipvp.canvas.mask.BinaryMask;
import org.ipvp.canvas.mask.Mask;
import org.ipvp.canvas.slot.Slot;
import org.ipvp.canvas.type.ChestMenu;

public class SpecialBlocksMenu extends SpecialBlocks {

    public Menu getUI() {
        Menu specialBlocksMenu = ChestMenu.builder(3).title("Special Blocks").redraw(true).build();

        // Set bottom glass border
        Mask mask = BinaryMask.builder(specialBlocksMenu)
                .item(new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE, 1).setName(" ").build())
                .pattern("000000000") // First row
                .pattern("000000000") // Second row
                .pattern("111101111").build(); // Third row
        mask.apply(specialBlocksMenu);

        // Set close button item
        specialBlocksMenu.getSlot(22).setItem(new ItemBuilder(Material.BARRIER, 1)
                .setName("§6§lCLOSE")
                .build());

        specialBlocksMenu.getSlot(22).setClickHandler((player, clickInformation) -> {
            player.closeInventory();

            new CompanionMenu().getUI().open(player);
        });

        // Set special blocks items
        for(int i = 0; i <= 14; i++) {
            Slot slot = specialBlocksMenu.getSlot(i);

            switch (i) {
                // First Row
                case 0:
                    slot.setItem(DebugStick);

                    slot.setClickHandler((player, clickInformation) -> {
                        player.performCommand("dbs");
                    });
                    break;
                // Barrier
                case 1:
                    slot.setItem(Barrier);

                    slot.setClickHandler((player, clickInformation) -> {
                        giveItemToPlayer(player, Barrier);
                    });
                    break;
                // Structure Void
                case 2:
                    slot.setItem(StructureVoid);

                    slot.setClickHandler((player, clickInformation) -> {
                        giveItemToPlayer(player, StructureVoid);
                    });
                    break;
            }
        }

        return specialBlocksMenu;
    }

    private void giveItemToPlayer(Player player, ItemStack item) {
        if(!player.getInventory().contains(item)) {
            player.getInventory().addItem(item);
            player.playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP, 5.0f, 1.0f);
        }
    }

    public static ItemStack getItem() {
        return new ItemBuilder(Material.GOLD_BLOCK ,1)
                .setName("§b§lSPECIAL BLOCKS AND ITEMS")
                .setLore(new LoreBuilder().description("Open the special blocks menu to get a variety of inaccessible blocks and items.").build())
                .build();
    }
}
