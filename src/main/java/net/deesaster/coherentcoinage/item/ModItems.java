package net.deesaster.coherentcoinage.item;

import net.deesaster.coherentcoinage.CoherentCoinage;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModItems {

    public static final Item COPPER_COIN = registerItem("copper_coin",
            new CoinItem(new FabricItemSettings().maxCount(64).group(ModItemGroup.COHERENT_COINAGE)));
    public static final Item IRON_COIN = registerItem("iron_coin",
            new CoinItem(new FabricItemSettings().maxCount(64).group(ModItemGroup.COHERENT_COINAGE)));
    public static final Item GOLDEN_COIN = registerItem("golden_coin",
            new CoinItem(new FabricItemSettings().maxCount(64).group(ModItemGroup.COHERENT_COINAGE)));
    public static final Item PLATINUM_COIN = registerItem("platinum_coin",
            new CoinItem(new FabricItemSettings().maxCount(64).group(ModItemGroup.COHERENT_COINAGE)));

    public static final Item COIN_PURSE = registerItem("coin_purse",
            new CoinPurseItem(new FabricItemSettings().maxCount(1).group(ModItemGroup.COHERENT_COINAGE), 1.0F));
    public static final Item NETHERITE_COIN_PURSE = registerItem("netherite_coin_purse",
            new CoinPurseItem(new FabricItemSettings().maxCount(1).fireproof().group(ModItemGroup.COHERENT_COINAGE), 4.0F));

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registry.ITEM, new Identifier(CoherentCoinage.MOD_ID, name), item);
    }

    public static void registerModItems() {
        CoherentCoinage.LOGGER.info("Registering ModItems for " + CoherentCoinage.MOD_ID);
    }
}
