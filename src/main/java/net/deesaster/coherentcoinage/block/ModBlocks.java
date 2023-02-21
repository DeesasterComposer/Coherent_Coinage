package net.deesaster.coherentcoinage.block;

import net.deesaster.coherentcoinage.CoherentCoinage;
import net.deesaster.coherentcoinage.item.ModItemGroup;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModBlocks {
    public static final Block PIGGY_BANK = registerBlock("piggy_bank",
            new PiggyBankBlock(FabricBlockSettings.of(Material.STONE).hardness(0.5f).resistance(0.5f).dynamicBounds()),
            ModItemGroup.COHERENT_COINAGE);
    public static final Block SAFE = registerBlock("safe",
            new SafeBlock(FabricBlockSettings.of(Material.METAL).hardness(10.0f).resistance(400.0f).dynamicBounds()),
            ModItemGroup.COHERENT_COINAGE);

    private static Block registerBlock(String name, Block block, ItemGroup group) {
        registerBlockItem(name, block, group);
        return Registry.register(Registry.BLOCK, new Identifier(CoherentCoinage.MOD_ID, name), block);
    }

    private static Item registerBlockItem(String name, Block block, ItemGroup group) {
        return Registry.register(Registry.ITEM, new Identifier(CoherentCoinage.MOD_ID, name),
            new BlockItem(block, new FabricItemSettings().group(group)));
    }

    public static void registerModBlocks() {
        CoherentCoinage.LOGGER.info("Registering ModBlocks for " + CoherentCoinage.MOD_ID);
    }
}
