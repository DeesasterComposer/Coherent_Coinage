package net.deesaster.coherentcoinage.sound;

import net.deesaster.coherentcoinage.CoherentCoinage;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModSounds {

    public static SoundEvent BLOCK_PIGGY_BANK_DEPOSIT = registerSoundEvent("block.piggy_bank.deposit");
    public static SoundEvent BLOCK_SAFE_OPEN = registerSoundEvent("block.safe.open");
    public static SoundEvent BLOCK_SAFE_CLOSE = registerSoundEvent("block.safe.close");

    public static SoundEvent ITEM_COIN_DROP = registerSoundEvent("item.coin.drop");
    public static SoundEvent ITEM_COIN_PURSE_REMOVE_ONE = registerSoundEvent("item.coin_purse.remove_one");
    public static SoundEvent ITEM_COIN_PURSE_INSERT = registerSoundEvent("item.coin_purse.insert");
    public static SoundEvent ITEM_COIN_PURSE_INSERT_FULL = registerSoundEvent("item.coin_purse.insert_full");
    public static SoundEvent ITEM_COIN_PURSE_DROP_CONTENTS = registerSoundEvent("item.coin_purse.drop_contents");

    private static SoundEvent registerSoundEvent(String name) {
        Identifier id = new Identifier(CoherentCoinage.MOD_ID, name);
        return Registry.register(Registry.SOUND_EVENT, id, new SoundEvent(id));
    }

    public static void registerModSounds() {
        CoherentCoinage.LOGGER.info("Registering ModSounds for " + CoherentCoinage.MOD_ID);
    }
}
