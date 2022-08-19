package com.alpsbte.companion.core;

import com.alpsbte.companion.utils.ItemBuilder;
import com.alpsbte.companion.utils.LoreBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class SpecialBlocks {
    public static ItemStack DebugStick = new ItemBuilder(Material.DEBUG_STICK, 1)
            .setName("§6§lDebug Stick").setLore(new LoreBuilder()
                    .emptyLine()
                    .description("Use this tool to modify block states.")
                    .build())
            .setEnchantment(true)
            .build();

    public static ItemStack Barrier = new ItemBuilder(Material.BARRIER, 1)
            .setName("§6§lBarrier").setLore(new LoreBuilder()
                    .emptyLine()
                    .description("Use this tool to place an §fInvisible§7 block.",
                            "",
                            "§7ID: §b166")
                    .emptyLine()
                    .build())
            .setEnchantment(true)
            .build();

    public static ItemStack StructureVoid = new ItemBuilder(Material.STRUCTURE_VOID, 1)
            .setName("§6§lStructure Void").setLore(new LoreBuilder()
                    .emptyLine()
                    .description("Use this tool to place an §fInvisible§7 block.",
                            "",
                            "§7ID: §b217")
                    .emptyLine()
                    .build())
            .setEnchantment(true)
            .build();

    public static ItemStack BlockUpdateTool = new ItemBuilder(Material.STICK, 1)
            .setName("§c§lBlock Update Tool").setLore(new LoreBuilder()
                    .emptyLine()
                    .description("Use this tool to update the state of a existing block.")
                    .emptyLine()
                    .build())
            .setEnchantment(true)
            .build();
}
