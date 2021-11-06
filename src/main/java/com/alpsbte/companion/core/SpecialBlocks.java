package com.alpsbte.companion.core;

import com.alpsbte.companion.utils.ItemBuilder;
import com.alpsbte.companion.utils.LoreBuilder;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

public class SpecialBlocks {
    public static ItemStack SeamlessSandstone = new ItemBuilder(Material.SANDSTONE, 1, (byte) 2)
            .setName("§6§lSeamless Sandstone").setLore(new LoreBuilder()
                    .emptyLine()
                    .description("Use this tool to place a §fSeamless Sandstone§7 block.",
                            "",
                            "§7ID: §b43:9")
                    .emptyLine()
                    .build())
            .setEnchantment(true)
            .build();

    public static ItemStack SeamlessRedSandstone = new ItemBuilder(Material.RED_SANDSTONE, 1, (byte) 2)
            .setName("§6§lSeamless Red Sandstone").setLore(new LoreBuilder()
                    .emptyLine()
                    .description("Use this tool to place a §fSeamless Red Sandstone§7 block.",
                            "",
                            "§7ID: §b181:12")
                    .emptyLine()
                    .build())
            .setEnchantment(true)
            .build();

    public static ItemStack SeamlessStone = new ItemBuilder(Material.STONE, 1)
            .setName("§6§lSeamless Stone").setLore(new LoreBuilder()
                    .emptyLine()
                    .description("Use this tool to place a §fSeamless Stone§7 block.",
                            "",
                            "§7ID: §b43:8")
                    .emptyLine()
                    .build())
            .setEnchantment(true)
            .build();

    public static ItemStack RedMushroom = new ItemBuilder(Material.HUGE_MUSHROOM_2, 1)
            .setName("§6§lRed Mushroom").setLore(new LoreBuilder()
                    .emptyLine()
                    .description("Use this tool to place a §fRed Mushroom§7 block.",
                            "",
                            "§7ID: §b100")
                    .emptyLine()
                    .build())
            .setEnchantment(true)
            .build();

    public static ItemStack BrownMushroom = new ItemBuilder(Material.HUGE_MUSHROOM_1, 1)
            .setName("§6§lBrown Mushroom Block").setLore(new LoreBuilder()
                    .emptyLine()
                    .description("Use this tool to place a §fBrown Mushroom§7 block.",
                            "",
                            "§7ID: §b99")
                    .emptyLine()
                    .build())
            .setEnchantment(true)
            .build();

    public static ItemStack SeamlessMushroomStem = new ItemBuilder(Material.HUGE_MUSHROOM_2, 1)
            .setName("§6§lSeamless Mushroom Stem Block").setLore(new LoreBuilder()
                    .emptyLine()
                    .description("Use this tool to place a §fSeamless Mushroom Stem§7 block.",
                            "",
                            "§7ID: §b99:15")
                    .emptyLine()
                    .build())
            .setEnchantment(true)
            .build();

    public static ItemStack LightBrownMushroom = new ItemBuilder(Material.HUGE_MUSHROOM_1, 1)
            .setName("§6§lLight Brown Mushroom Block").setLore(new LoreBuilder()
                    .emptyLine()
                    .description("Use this tool to place a §fLight Brown Mushroom§7 block.",
                            "",
                            "§7ID: §b99:0")
                    .emptyLine()
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

    public static ItemStack BarkOakLog = new ItemBuilder(Material.LOG, 1, (byte) 0)
            .setName("§6§lBark Oak Log").setLore(new LoreBuilder()
                    .emptyLine()
                    .description("Use this tool to place a §f6-Sided Bark Oak Log§7 block.",
                            "",
                            "§7ID: §b17:12")
                    .emptyLine()
                    .build())
            .setEnchantment(true)
            .build();

    public static ItemStack BarkBirchLog = new ItemBuilder(Material.LOG, 1, (byte) 2)
            .setName("§6§lBark Birch Log").setLore(new LoreBuilder()
                    .emptyLine()
                    .description("Use this tool to place a §f6-Sided Bark Birch Log§7 block.",
                            "",
                            "§7ID: §b17:14")
                    .emptyLine()
                    .build())
            .setEnchantment(true)
            .build();

    public static ItemStack BarkSpruceLog = new ItemBuilder(Material.LOG, 1, (byte) 1)
            .setName("§6§lBark Spruce Log").setLore(new LoreBuilder()
                    .emptyLine()
                    .description("Use this tool to place a §f6-Sided Bark Spruce Log§7 block.",
                            "",
                            "§7ID: §b17:13")
                    .emptyLine()
                    .build())
            .setEnchantment(true)
            .build();

    public static ItemStack BarkJungleLog = new ItemBuilder(Material.LOG, 1, (byte) 3)
            .setName("§6§lBark Jungle Log").setLore(new LoreBuilder()
                    .emptyLine()
                    .description("Use this tool to place a §f6-Sided Bark Jungle Log§7 block.",
                            "",
                            "§7ID: §b17:15")
                    .emptyLine()
                    .build())
            .setEnchantment(true)
            .build();

    public static ItemStack BarkAcaciaLog = new ItemBuilder(Material.LOG_2, 1, (byte) 0)
            .setName("§6§lBark Acacia Log").setLore(new LoreBuilder()
                    .emptyLine()
                    .description("Use this tool to place a §f6-Sided Bark Acacia Log§7 block.",
                            "",
                            "§7ID: §b162:12")
                    .emptyLine()
                    .build())
            .setEnchantment(true)
            .build();

    public static ItemStack BarkDarkOakLog = new ItemBuilder(Material.LOG_2, 1, (byte) 1)
            .setName("§6§lBark Dark Oak Log").setLore(new LoreBuilder()
                    .emptyLine()
                    .description("Use this tool to place a §f6-Sided Bark Dark Oak Log§7 block.",
                            "",
                            "§7ID: §b162:13")
                    .emptyLine()
                    .build())
            .setEnchantment(true)
            .build();
}
