package net.deesaster.coherentcoinage.util;

import net.deesaster.coherentcoinage.item.ModItems;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.minecraft.entity.EntityType;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.condition.EntityPropertiesLootCondition;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.util.Identifier;

public class ModLootTableModifiers {
    private static final Identifier ANCIENT_CITY_ID
            = new Identifier("minecraft", "chests/ancient_city");
    private static final Identifier BASTION_BRIDGE_ID
            = new Identifier("minecraft", "chests/bastion_bridge");
    private static final Identifier BASTION_HOGLIN_STABLE_ID
            = new Identifier("minecraft", "chests/bastion_hoglin_stable");
    private static final Identifier BASTION_OTHER_ID
            = new Identifier("minecraft", "chests/bastion_other");
    private static final Identifier BASTION_TREASURE_ID
            = new Identifier("minecraft", "chests/bastion_treasure");
    private static final Identifier BURIED_TREASURE_ID
            = new Identifier("minecraft", "chests/buried_treasure");
    private static final Identifier DESERT_PYRAMID_ID
            = new Identifier("minecraft", "chests/desert_pyramid");
    private static final Identifier END_CITY_TREASURE_ID
            = new Identifier("minecraft", "chests/end_city_treasure");
    private static final Identifier JUNGLE_TEMPLE_ID
            = new Identifier("minecraft", "chests/jungle_temple");
    private static final Identifier NETHER_BRIDGE_ID
            = new Identifier("minecraft", "chests/nether_bridge");
    private static final Identifier PILLAGER_OUTPOST_ID
            = new Identifier("minecraft", "chests/pillager_outpost");
    private static final Identifier RUINED_PORTAL_ID
            = new Identifier("minecraft", "chests/ruined_portal");
    private static final Identifier SHIPWRECK_TREASURE_ID
            = new Identifier("minecraft", "chests/shipwreck_treasure");
    private static final Identifier SIMPLE_DUNGEON_ID
            = new Identifier("minecraft", "chests/simple_dungeon");
    private static final Identifier STRONGHOLD_CORRIDOR_ID
            = new Identifier("minecraft", "chests/stronghold_corridor");
    private static final Identifier STRONGHOLD_CROSSING_ID
            = new Identifier("minecraft", "chests/stronghold_crossing");
    private static final Identifier UNDERWATER_RUIN_BIG_ID
            = new Identifier("minecraft", "chests/underwater_ruin_big");
    private static final Identifier UNDERWATER_RUIN_SMALL_ID
            = new Identifier("minecraft", "chests/underwater_ruin_small");
    private static final Identifier WOODLAND_MANSION_ID
            = new Identifier("minecraft", "chests/woodland_mansion");

    private static final Identifier ZOMBIE_ID
            = new Identifier("minecraft", "entities/zombie");
    private static final Identifier ZOMBIE_VILLAGER_ID
            = new Identifier("minecraft", "entities/zombie_villager");
    private static final Identifier HUSK_ID
            = new Identifier("minecraft", "entities/husk");
    private static final Identifier DROWNED_ID
            = new Identifier("minecraft", "entities/drowned");
    private static final Identifier SPIDER_ID
            = new Identifier("minecraft", "entities/spider");
    private static final Identifier CAVE_SPIDER_ID
            = new Identifier("minecraft", "entities/cave_spider");
    private static final Identifier CREEPER_ID
            = new Identifier("minecraft", "entities/creeper");
    private static final Identifier SKELETON_ID
            = new Identifier("minecraft", "entities/skeleton");
    private static final Identifier WITHER_SKELETON_ID
            = new Identifier("minecraft", "entities/wither_skeleton");
    private static final Identifier STRAY_ID
            = new Identifier("minecraft", "entities/stray");
    private static final Identifier BLAZE_ID
            = new Identifier("minecraft", "entities/blaze");
    private static final Identifier ENDERMAN_ID
            = new Identifier("minecraft", "entities/enderman");
    private static final Identifier ENDERMITE_ID
            = new Identifier("minecraft", "entities/endermite");
    private static final Identifier SILVERFISH_ID
            = new Identifier("minecraft", "entities/silverfish");
    private static final Identifier EVOKER_ID
            = new Identifier("minecraft", "entities/evoker");
    private static final Identifier ILLUSIONER_ID
            = new Identifier("minecraft", "entities/illusioner");
    private static final Identifier PILLAGER_ID
            = new Identifier("minecraft", "entities/pillager");
    private static final Identifier RAVAGER_ID
            = new Identifier("minecraft", "entities/ravager");
    private static final Identifier VEX_ID
            = new Identifier("minecraft", "entities/vex");
    private static final Identifier VINDICATOR_ID
            = new Identifier("minecraft", "entities/vindicator");
    private static final Identifier GUARDIAN_ID
            = new Identifier("minecraft", "entities/guardian");
    private static final Identifier ELDER_GUARDIAN_ID
            = new Identifier("minecraft", "entities/elder_guardian");
    private static final Identifier ENDER_DRAGON_ID
            = new Identifier("minecraft", "entities/ender_dragon");
    private static final Identifier WITHER_ID
            = new Identifier("minecraft", "entities/wither");
    private static final Identifier GHAST_ID
            = new Identifier("minecraft", "entities/ghast");
    private static final Identifier HOGLIN_ID
            = new Identifier("minecraft", "entities/hoglin");
    private static final Identifier ZOGLIN_ID
            = new Identifier("minecraft", "entities/zoglin");
    private static final Identifier IRON_GOLEM_ID
            = new Identifier("minecraft", "entities/iron_golem");
    private static final Identifier SLIME_ID
            = new Identifier("minecraft", "entities/slime");
    private static final Identifier MAGMA_CUBE_ID
            = new Identifier("minecraft", "entities/magma_cube");
    private static final Identifier PHANTOM_ID
            = new Identifier("minecraft", "entities/phantom");
    private static final Identifier PIGLIN_ID
            = new Identifier("minecraft", "entities/piglin");
    private static final Identifier ZOMBIFIED_PIGLIN_ID
            = new Identifier("minecraft", "entities/zombified_piglin");
    private static final Identifier PIGLIN_BRUTE_ID
            = new Identifier("minecraft", "entities/piglin_brute");
    private static final Identifier SHULKER_ID
            = new Identifier("minecraft", "entities/shulker");
    private static final Identifier WARDEN_ID
            = new Identifier("minecraft", "entities/warden");
    private static final Identifier WITCH_ID
            = new Identifier("minecraft", "entities/witch");

    private static final Identifier FISHING_JUNK_ID
            = new Identifier("minecraft", "gameplay/fishing/junk");
    private static final Identifier FISHING_TREASURE_ID
            = new Identifier("minecraft", "gameplay/fishing/treasure");

    public static void modifyLootTables() {
        LootTableEvents.MODIFY.register((resourceManager, lootManager, id, tableBuilder, source) -> {

            /* CHESTS */
            if (ANCIENT_CITY_ID.equals(id) || DESERT_PYRAMID_ID.equals(id) || JUNGLE_TEMPLE_ID.equals(id)
                    || STRONGHOLD_CORRIDOR_ID.equals(id) || STRONGHOLD_CROSSING_ID.equals(id) || WOODLAND_MANSION_ID.equals(id)) {
                LootPool.Builder poolBuilder = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(2))
                        .conditionally(RandomChanceLootCondition.builder(0.7f))
                        .with(ItemEntry.builder(ModItems.IRON_COIN))
                        .rolls(ConstantLootNumberProvider.create(1))
                        .conditionally(RandomChanceLootCondition.builder(0.2f))
                        .with(ItemEntry.builder(ModItems.GOLDEN_COIN))
                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f, 8.0f)).build());
                tableBuilder.pool(poolBuilder.build());
            }
            else if (BASTION_BRIDGE_ID.equals(id) || BASTION_OTHER_ID.equals(id) || BASTION_TREASURE_ID.equals(id)
                    || BASTION_HOGLIN_STABLE_ID.equals(id) || RUINED_PORTAL_ID.equals(id) || NETHER_BRIDGE_ID.equals(id)
                    || END_CITY_TREASURE_ID.equals(id)) {
                LootPool.Builder poolBuilder = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(2))
                        .conditionally(RandomChanceLootCondition.builder(0.2f))
                        .with(ItemEntry.builder(ModItems.GOLDEN_COIN))
                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f, 8.0f)).build());
                tableBuilder.pool(poolBuilder.build());
            }
            else if (BURIED_TREASURE_ID.equals(id)) {
                LootPool.Builder poolBuilder = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(4))
                        .conditionally(RandomChanceLootCondition.builder(1f))
                        .with(ItemEntry.builder(ModItems.IRON_COIN))
                        .rolls(ConstantLootNumberProvider.create(2))
                        .conditionally(RandomChanceLootCondition.builder(0.5f))
                        .with(ItemEntry.builder(ModItems.GOLDEN_COIN))
                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f, 8.0f)).build());
                tableBuilder.pool(poolBuilder.build());
            }
            else if (SHIPWRECK_TREASURE_ID.equals(id) || UNDERWATER_RUIN_SMALL_ID.equals(id) || UNDERWATER_RUIN_BIG_ID.equals(id)
                    || SIMPLE_DUNGEON_ID.equals(id) || PILLAGER_OUTPOST_ID.equals(id)) {
                LootPool.Builder poolBuilder = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(4))
                        .conditionally(RandomChanceLootCondition.builder(1f))
                        .with(ItemEntry.builder(ModItems.COPPER_COIN))
                        .rolls(ConstantLootNumberProvider.create(2))
                        .conditionally(RandomChanceLootCondition.builder(0.7f))
                        .with(ItemEntry.builder(ModItems.IRON_COIN))
                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f, 8.0f)).build());
                tableBuilder.pool(poolBuilder.build());
            }

            /* FISHING */
            else if (FISHING_JUNK_ID.equals(id)) {
                LootPool.Builder poolBuilder = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .conditionally(RandomChanceLootCondition.builder(0.4f))
                        .with(ItemEntry.builder(ModItems.COPPER_COIN))
                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f, 8.0f)).build());
                tableBuilder.pool(poolBuilder.build());
            }
            else if (FISHING_TREASURE_ID.equals(id)) {
                LootPool.Builder poolBuilder = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .conditionally(RandomChanceLootCondition.builder(0.7f))
                        .with(ItemEntry.builder(ModItems.IRON_COIN))
                        .rolls(ConstantLootNumberProvider.create(1))
                        .conditionally(RandomChanceLootCondition.builder(0.4f))
                        .with(ItemEntry.builder(ModItems.GOLDEN_COIN))
                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f, 4.0f)).build());
                tableBuilder.pool(poolBuilder.build());
            }

            /* ENTITIES */
            else if (SLIME_ID.equals(id) || MAGMA_CUBE_ID.equals(id)) {
                LootPool.Builder poolBuilder = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .conditionally(EntityPropertiesLootCondition.builder(LootContext.EntityTarget.KILLER,
                                new EntityPredicate.Builder().type(EntityType.PLAYER).build()).build())
                        /*.conditionally(EntityPropertiesLootCondition.builder(LootContext.EntityTarget.THIS,
                                new EntityPredicate.Builder().typeSpecific().build).build())*/
                        .with(ItemEntry.builder(ModItems.COPPER_COIN))
                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(0.0f, 1.0f)).build());
                tableBuilder.pool(poolBuilder.build());
            }
            else if (ENDERMITE_ID.equals(id) || SILVERFISH_ID.equals(id) || VEX_ID.equals(id)) {
                LootPool.Builder poolBuilder = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .conditionally(EntityPropertiesLootCondition.builder(LootContext.EntityTarget.KILLER,
                                new EntityPredicate.Builder().type(EntityType.PLAYER).build()).build())
                        .with(ItemEntry.builder(ModItems.COPPER_COIN))
                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(0.0f, 2.0f)).build());
                tableBuilder.pool(poolBuilder.build());
            }
            else if (ZOMBIE_ID.equals(id) || ZOMBIE_VILLAGER_ID.equals(id) || SPIDER_ID.equals(id) ||
                    CREEPER_ID.equals(id) || SKELETON_ID.equals(id) || PHANTOM_ID.equals(id) || ZOMBIFIED_PIGLIN_ID.equals(id)) {
                LootPool.Builder poolBuilder = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .conditionally(EntityPropertiesLootCondition.builder(LootContext.EntityTarget.KILLER,
                                new EntityPredicate.Builder().type(EntityType.PLAYER).build()).build())
                        .with(ItemEntry.builder(ModItems.COPPER_COIN))
                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f, 8.0f)).build());
                tableBuilder.pool(poolBuilder.build());
            }
            else if (HUSK_ID.equals(id) || DROWNED_ID.equals(id) || CAVE_SPIDER_ID.equals(id) || STRAY_ID.equals(id) ||
                    PILLAGER_ID.equals(id) || PIGLIN_ID.equals(id) || WITCH_ID.equals(id)) {
                LootPool.Builder poolBuilder = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .conditionally(EntityPropertiesLootCondition.builder(LootContext.EntityTarget.KILLER,
                                new EntityPredicate.Builder().type(EntityType.PLAYER).build()).build())
                        .with(ItemEntry.builder(ModItems.COPPER_COIN))
                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(3.0f, 10.0f)).build());
                tableBuilder.pool(poolBuilder.build());
            }
            else if (GUARDIAN_ID.equals(id) || ENDERMAN_ID.equals(id) || BLAZE_ID.equals(id) || WITHER_SKELETON_ID.equals(id) ||
                    VINDICATOR_ID.equals(id) || GHAST_ID.equals(id) || HOGLIN_ID.equals(id) || ZOGLIN_ID.equals(id) ||
                    SHULKER_ID.equals(id) || PIGLIN_BRUTE_ID.equals(id)) {
                LootPool.Builder poolBuilder = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .conditionally(EntityPropertiesLootCondition.builder(LootContext.EntityTarget.KILLER,
                                new EntityPredicate.Builder().type(EntityType.PLAYER).build()).build())
                        .with(ItemEntry.builder(ModItems.COPPER_COIN))
                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(6.0f, 14.0f)).build());
                tableBuilder.pool(poolBuilder.build());
            }
            else if (EVOKER_ID.equals(id) || ILLUSIONER_ID.equals(id) || RAVAGER_ID.equals(id)) {
                LootPool.Builder poolBuilder = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .conditionally(EntityPropertiesLootCondition.builder(LootContext.EntityTarget.KILLER,
                                new EntityPredicate.Builder().type(EntityType.PLAYER).build()).build())
                        .with(ItemEntry.builder(ModItems.COPPER_COIN))
                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(12.0f, 20.0f)).build());
                tableBuilder.pool(poolBuilder.build());
            }
            else if (IRON_GOLEM_ID.equals(id)) {
                LootPool.Builder poolBuilder = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .conditionally(EntityPropertiesLootCondition.builder(LootContext.EntityTarget.KILLER,
                                new EntityPredicate.Builder().type(EntityType.PLAYER).build()).build())
                        .with(ItemEntry.builder(ModItems.IRON_COIN))
                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f, 3.0f)).build());
                tableBuilder.pool(poolBuilder.build());
            }
            else if (ELDER_GUARDIAN_ID.equals(id) || WARDEN_ID.equals(id)) {
                LootPool.Builder poolBuilder = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .conditionally(EntityPropertiesLootCondition.builder(LootContext.EntityTarget.KILLER,
                                new EntityPredicate.Builder().type(EntityType.PLAYER).build()).build())
                        .with(ItemEntry.builder(ModItems.IRON_COIN))
                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(8.0f, 16.0f)).build());
                tableBuilder.pool(poolBuilder.build());
            }
            else if (ENDER_DRAGON_ID.equals(id) || WITHER_ID.equals(id)) {
                LootPool.Builder poolBuilder = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(6))
                        .conditionally(EntityPropertiesLootCondition.builder(LootContext.EntityTarget.KILLER,
                                new EntityPredicate.Builder().type(EntityType.PLAYER).build()).build())
                        .with(ItemEntry.builder(ModItems.GOLDEN_COIN))
                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f, 1.0f)).build());
                tableBuilder.pool(poolBuilder.build());
            }
        });
    }
}
