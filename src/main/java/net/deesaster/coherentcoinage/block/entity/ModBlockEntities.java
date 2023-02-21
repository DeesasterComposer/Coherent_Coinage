package net.deesaster.coherentcoinage.block.entity;

import net.deesaster.coherentcoinage.CoherentCoinage;
import net.deesaster.coherentcoinage.block.ModBlocks;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModBlockEntities {
    public static BlockEntityType<PiggyBankBlockEntity> PIGGY_BANK;
    public static BlockEntityType<SafeBlockEntity> SAFE;

    public static void registerBlockEntities() {
        PIGGY_BANK = Registry.register(Registry.BLOCK_ENTITY_TYPE,
                new Identifier(CoherentCoinage.MOD_ID, "piggy_bank"),
                FabricBlockEntityTypeBuilder.create(PiggyBankBlockEntity::new,
                ModBlocks.PIGGY_BANK).build(null));
        SAFE = Registry.register(Registry.BLOCK_ENTITY_TYPE,
                new Identifier(CoherentCoinage.MOD_ID, "safe"),
                FabricBlockEntityTypeBuilder.create(SafeBlockEntity::new,
                ModBlocks.SAFE).build(null));
    }
}
