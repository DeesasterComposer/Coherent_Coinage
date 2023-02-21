//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package net.deesaster.coherentcoinage.item;

import net.deesaster.coherentcoinage.sound.ModSounds;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.StackReference;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.screen.slot.Slot;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.util.ClickType;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class CoinPurseItem extends Item {
    public float MAX_STORAGE;

    public CoinPurseItem(Settings settings, Float maxStorage) {
        super(settings);
        this.MAX_STORAGE = maxStorage * 262144;
    }

    public boolean onStackClicked(ItemStack stack, Slot slot, ClickType clickType, PlayerEntity player) {
        if (clickType != ClickType.RIGHT) {
            return false;
        } else {
            ItemStack itemStack = slot.getStack();
            if (itemStack.isEmpty()) {
                ItemStack removeStack = removeTopCoins(stack, slot, player);
                if (removeStack != ItemStack.EMPTY) {
                    slot.insertStack(removeStack);
                    this.playRemoveOneSound(player);
                }
            } else if (itemStack.getItem() instanceof CoinItem) {
                int remove = addToPurse(stack, itemStack, player);
                itemStack.decrement(remove);
                if (remove > 0) {
                    if (getPurseContents(stack) >= MAX_STORAGE) {
                        this.playInsertFullSound(player);
                    } else {
                        this.playInsertSound(player);
                    }
                }
            }

            return true;
        }
    }

    public boolean onClicked(ItemStack stack, ItemStack otherStack, Slot slot, ClickType clickType, PlayerEntity player, StackReference cursorStackReference) {
        if (clickType == ClickType.RIGHT && slot.canTakePartial(player)) {
            if (otherStack.isEmpty()) {
                ItemStack removeStack = removeTopCoins(stack, slot, player);
                if (removeStack != ItemStack.EMPTY) {
                    cursorStackReference.set(removeStack);
                    this.playRemoveOneSound(player);
                }
            } else {
                int remove = addToPurse(stack, otherStack, player);
                otherStack.decrement(remove);
                if (remove > 0) {
                    if (getPurseContents(stack) >= MAX_STORAGE) {
                        this.playInsertFullSound(player);
                    } else {
                        this.playInsertSound(player);
                    }
                }
            }

            return true;
        } else {
            return false;
        }
    }

    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack purse = user.getStackInHand(hand);
        if (getPurseContents(purse) != 0 && dropAllCoins(purse, user)) {
            this.playDropContentsSound(user);
            user.incrementStat(Stats.USED.getOrCreateStat(this));
            return TypedActionResult.success(purse, world.isClient());
        } else {
            return TypedActionResult.fail(purse);
        }
    }

    private int addToPurse(ItemStack purse, ItemStack stack, PlayerEntity player) {
        NbtCompound nbtCompound = purse.getOrCreateNbt();
        if (!nbtCompound.contains("coherentcoinage.Coins")) {
            nbtCompound.putFloat("coherentcoinage.Coins", 0);
        }

        int contents = getPurseContents(purse);
        int add = getCoinValue(stack);
        int remainder = 0;

        if (contents >= this.MAX_STORAGE) {
            nbtCompound.putFloat("coherentcoinage.Coins", this.MAX_STORAGE);
            return remainder;
        }
        else if ((contents + add) >= this.MAX_STORAGE) {
            nbtCompound.putFloat("coherentcoinage.Coins", this.MAX_STORAGE);
            remainder = (int) ((contents + add) - this.MAX_STORAGE);

            if (stack.getItem() == ModItems.COPPER_COIN) {
                return stack.getCount()-remainder;
            }
            else {
                int copperCoins = 0;
                int ironCoins = 0;
                int goldCoins = 0;
                int platinumCoins = 0;

                if (remainder >= 262144) {
                    platinumCoins = remainder / 262144;
                    remainder = remainder % 262144;
                }
                if (remainder >= 4096) {
                    goldCoins = remainder / 4096;
                    remainder = remainder % 4096;
                }
                if (remainder >= 64) {
                    ironCoins = remainder / 64;
                    remainder = remainder % 64;
                }
                copperCoins = remainder;

                if (stack.getItem() == ModItems.IRON_COIN) {
                    giveRemainderCoins(0, 0, 0, copperCoins, player);
                    return stack.getCount()-ironCoins;
                }
                else if (stack.getItem() == ModItems.GOLDEN_COIN) {
                    giveRemainderCoins(0, 0, ironCoins, copperCoins, player);
                    return stack.getCount()-goldCoins;
                }
                else if (stack.getItem() == ModItems.PLATINUM_COIN) {
                    giveRemainderCoins(0, goldCoins, ironCoins, copperCoins, player);
                    return stack.getCount()-platinumCoins;
                }
            }
        } else {
            nbtCompound.putFloat("coherentcoinage.Coins", contents + add);
            return stack.getCount();
        }
        return 0;
    }

    private ItemStack removeTopCoins(ItemStack purse, Slot slot, PlayerEntity player) {
        NbtCompound nbtCompound = purse.getOrCreateNbt();
        if (!nbtCompound.contains("coherentcoinage.Coins")) {
            nbtCompound.putFloat("coherentcoinage.Coins", 0);
            return ItemStack.EMPTY;
        } else {
            int contents = getPurseContents(purse);
            if (contents == 0) {
                return ItemStack.EMPTY;
            } else {
                int coins = 0;
                ItemStack itemStack = null;

                if (contents >= 262144) {
                    coins = contents / 262144;
                    contents = contents % 262144;
                    itemStack = new ItemStack(ModItems.PLATINUM_COIN, coins);
                }
                else if (contents >= 4096) {
                    coins = contents / 4096;
                    contents = contents % 4096;
                    itemStack = new ItemStack(ModItems.GOLDEN_COIN, coins);
                }
                else if (contents >= 64) {
                    coins = contents / 64;
                    contents = contents % 64;
                    itemStack = new ItemStack(ModItems.IRON_COIN, coins);
                }
                else if (contents <= 63) {
                    coins = contents;
                    contents = 0;
                    itemStack = new ItemStack(ModItems.COPPER_COIN, coins);
                }

                nbtCompound.putFloat("coherentcoinage.Coins", contents);
                return itemStack;
            }
        }
    }

    private boolean dropAllCoins(ItemStack purse, PlayerEntity player) {
        NbtCompound nbtCompound = purse.getOrCreateNbt();
        if (!nbtCompound.contains("coherentcoinage.Coins")) {
            nbtCompound.putFloat("coherentcoinage.Coins", 0);
            return false;
        } else {
            if (player instanceof ServerPlayerEntity) {
                int contents = getPurseContents(purse);
                int copperCoins = 0;
                int ironCoins = 0;
                int goldCoins = 0;
                int platinumCoins = 0;

                if (contents >= 262144) {
                    platinumCoins = contents / 262144;
                    contents = contents % 262144;
                }
                if (contents >= 4096) {
                    goldCoins = contents / 4096;
                    contents = contents % 4096;
                }
                if (contents >= 64) {
                    ironCoins = contents / 64;
                    contents = contents % 64;
                }
                copperCoins = contents;

                player.dropItem(new ItemStack(ModItems.COPPER_COIN, copperCoins), true);
                player.dropItem(new ItemStack(ModItems.IRON_COIN, ironCoins), true);
                player.dropItem(new ItemStack(ModItems.GOLDEN_COIN, goldCoins), true);
                player.dropItem(new ItemStack(ModItems.PLATINUM_COIN, platinumCoins), true);
            }

            nbtCompound.putFloat("coherentcoinage.Coins", 0);
            return true;
        }
    }

    private void giveRemainderCoins(Integer platinum, Integer gold, Integer iron, Integer copper, PlayerEntity player) {
        player.giveItemStack(new ItemStack(ModItems.COPPER_COIN, copper));
        player.giveItemStack(new ItemStack(ModItems.IRON_COIN, iron));
        player.giveItemStack(new ItemStack(ModItems.GOLDEN_COIN, gold));
        player.giveItemStack(new ItemStack(ModItems.PLATINUM_COIN, platinum));
    }

    public int getPurseContents(ItemStack purse) {
        NbtCompound nbtCompound = purse.getOrCreateNbt();
        if (!nbtCompound.contains("coherentcoinage.Coins")) {
            nbtCompound.putFloat("coherentcoinage.Coins", 0);
        }

        return nbtCompound.getInt("coherentcoinage.Coins");
    }

    private int getCoinValue(ItemStack stack) {
        if (stack.getItem() == ModItems.COPPER_COIN) {
            return stack.getCount();
        }
        else if (stack.getItem() == ModItems.IRON_COIN) {
            return 64*stack.getCount();
        }
        else if (stack.getItem() == ModItems.GOLDEN_COIN) {
            return (64*64)*stack.getCount();
        }
        else if (stack.getItem() == ModItems.PLATINUM_COIN) {
            return (64*64*64)*stack.getCount();
        } else {
            return 0;
        }
    }

    public void onItemEntityDestroyed(ItemEntity entity) {
        ItemStack purse = entity.getStack();
        int contents = getPurseContents(purse);
        ItemStack copperCoins = ItemStack.EMPTY;
        ItemStack ironCoins = ItemStack.EMPTY;
        ItemStack goldCoins = ItemStack.EMPTY;
        ItemStack platinumCoins = ItemStack.EMPTY;

        if (contents >= 262144) {
            platinumCoins = new ItemStack(ModItems.PLATINUM_COIN, contents / 262144);
            contents = contents % 262144;
        }
        if (contents >= 4096) {
            goldCoins = new ItemStack(ModItems.GOLDEN_COIN, contents / 4096);
            contents = contents % 4096;
        }
        if (contents >= 64) {
            ironCoins = new ItemStack(ModItems.IRON_COIN, contents / 64);
            contents = contents % 64;
        }
        copperCoins = new ItemStack(ModItems.COPPER_COIN, contents);

        World world = entity.world;
        if (!world.isClient) {
            world.spawnEntity(new ItemEntity(world, entity.getX(), entity.getY(), entity.getZ(), platinumCoins));
            world.spawnEntity(new ItemEntity(world, entity.getX(), entity.getY(), entity.getZ(), goldCoins));
            world.spawnEntity(new ItemEntity(world, entity.getX(), entity.getY(), entity.getZ(), ironCoins));
            world.spawnEntity(new ItemEntity(world, entity.getX(), entity.getY(), entity.getZ(), copperCoins));
        }
    }

    public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
        int contents = getPurseContents(stack);

        if (contents == 0) {
            tooltip.add(Text.translatable("item.coherentcoinage.coin_purse.fullness.empty").formatted(Formatting.GRAY).formatted(Formatting.ITALIC));
        } else {
            int copperCoins = 0;
            int ironCoins = 0;
            int goldCoins = 0;
            int platinumCoins = 0;

            if (contents >= 262144) {
                platinumCoins = contents / 262144;
                contents = contents % 262144;
            }
            if (contents >= 4096) {
                goldCoins = contents / 4096;
                contents = contents % 4096;
            }
            if (contents >= 64) {
                ironCoins = contents / 64;
                contents = contents % 64;
            }
            copperCoins = contents;


            if (platinumCoins != 0) {
                tooltip.add(Text.translatable("item.coherentcoinage.coin_purse.fullness.platinum", platinumCoins));
            }
            if (goldCoins != 0) {
                tooltip.add(Text.translatable("item.coherentcoinage.coin_purse.fullness.gold", goldCoins));
            }
            if (ironCoins != 0) {
                tooltip.add(Text.translatable("item.coherentcoinage.coin_purse.fullness.iron", ironCoins));
            }
            if (copperCoins != 0) {
                tooltip.add(Text.translatable("item.coherentcoinage.coin_purse.fullness.copper", copperCoins));
            }
        }
    }

    public boolean isItemBarVisible(ItemStack stack) {
        return getPurseContents(stack) > 0;
    }

    public int getItemBarStep(ItemStack stack) {
        int contents = getPurseContents(stack);
        if (contents <= 63) {
            return Math.min(1 + 12 * getPurseContents(stack) / 64, 13);
        }
        else if (contents <= 4095) {
            return Math.min(1 + 12 * (getPurseContents(stack)/64) / 64, 13);
        }
        else if (contents <= 262143) {
            return Math.min(1 + 12 * (getPurseContents(stack)/(64*64)) / 64, 13);
        } else {
            return Math.min((int) (1 + 12 * (getPurseContents(stack)/(64*64*64)) / (this.MAX_STORAGE/(64*64*64))), 13);
        }
    }

    public int getItemBarColor(ItemStack stack) {
        int contents = getPurseContents(stack);
        if (contents <= 63) {
            return MathHelper.packRgb(231, 124, 47);
        }
        else if (contents <= 4095) {
            return MathHelper.packRgb(216, 216, 216);
        }
        else if (contents <= 262143) {
            return MathHelper.packRgb(250, 214, 39);
        } else {
            return MathHelper.packRgb(151, 196, 220);
        }
    }

    private void playRemoveOneSound(Entity entity) {
        entity.playSound(ModSounds.ITEM_COIN_PURSE_REMOVE_ONE, 0.8F, 0.8F + entity.getWorld().getRandom().nextFloat() * 0.4F);
    }

    private void playInsertSound(Entity entity) {
        entity.playSound(ModSounds.ITEM_COIN_PURSE_INSERT, 0.8F, 0.8F + entity.getWorld().getRandom().nextFloat() * 0.4F);
    }

    private void playInsertFullSound(Entity entity) {
        entity.playSound(ModSounds.ITEM_COIN_PURSE_INSERT_FULL, 0.8F, 0.8F + entity.getWorld().getRandom().nextFloat() * 0.4F);
    }

    private void playDropContentsSound(Entity entity) {
        entity.playSound(ModSounds.ITEM_COIN_PURSE_DROP_CONTENTS, 0.8F, 0.8F + entity.getWorld().getRandom().nextFloat() * 0.4F);
    }
}
