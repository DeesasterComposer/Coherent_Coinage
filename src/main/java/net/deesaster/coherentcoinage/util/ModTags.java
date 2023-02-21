package net.deesaster.coherentcoinage.util;

import net.deesaster.coherentcoinage.CoherentCoinage;
import net.minecraft.item.Item;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModTags {
    public static class Items {
        public static final TagKey<Item> COINS =
                createTag("coins");
        public static final TagKey<Item> PIGGY_BANK_INSERTABLE =
                createTag("piggy_bank_insertable");

        private static TagKey<Item> createTag(String name) {
            return TagKey.of(Registry.ITEM_KEY, new Identifier(CoherentCoinage.MOD_ID, name));
        }

        private static TagKey<Item> createCommonTag(String name) {
            return TagKey.of(Registry.ITEM_KEY, new Identifier("c", name));
        }
    }
}
