package net.deesaster.coherentcoinage;

import net.deesaster.coherentcoinage.block.ModBlocks;
import net.deesaster.coherentcoinage.block.entity.ModBlockEntities;
import net.deesaster.coherentcoinage.item.ModItems;
import net.deesaster.coherentcoinage.sound.ModSounds;
import net.deesaster.coherentcoinage.util.ModLootTableModifiers;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CoherentCoinage implements ModInitializer {
	public static final String MOD_ID = "coherentcoinage";
	public static final Logger LOGGER = LoggerFactory.getLogger("coherentcoinage");

	@Override
	public void onInitialize() {
		ModItems.registerModItems();
		ModBlocks.registerModBlocks();
		ModBlockEntities.registerBlockEntities();
		ModSounds.registerModSounds();
		ModLootTableModifiers.modifyLootTables();
	}
}
