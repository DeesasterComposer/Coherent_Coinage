package net.deesaster.coherentcoinage.item;

import net.deesaster.coherentcoinage.CoherentCoinage;
import net.deesaster.coherentcoinage.sound.ModSounds;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class CoinItem extends Item {
    public CoinItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack stack = player.getStackInHand(hand);
        player.setCurrentHand(hand);
        Item coinType = stack.getItem();
        Item coinConvertType = null;
        Integer conversionAmt = 0;

        if (!world.isClient) {
            Integer coinCount = stack.getCount();

            if (coinCount == 1 && coinType == ModItems.PLATINUM_COIN) {
                coinConvertType = ModItems.GOLDEN_COIN;
                conversionAmt = 64;
            }
            else if (coinCount == 1 && coinType == ModItems.GOLDEN_COIN) {
                coinConvertType = ModItems.IRON_COIN;
                conversionAmt = 64;
            }
            else if (coinCount == 1 && coinType == ModItems.IRON_COIN) {
                coinConvertType = ModItems.COPPER_COIN;
                conversionAmt = 64;
            }
            else if (coinCount == 64 && coinType == ModItems.COPPER_COIN) {
                coinConvertType = ModItems.IRON_COIN;
                conversionAmt = 1;
            }
            else if (coinCount == 64 && coinType == ModItems.IRON_COIN) {
                coinConvertType = ModItems.GOLDEN_COIN;
                conversionAmt = 1;
            }
            else if (coinCount == 64 && coinType == ModItems.GOLDEN_COIN) {
                coinConvertType = ModItems.PLATINUM_COIN;
                conversionAmt = 1;
            } else {
                return TypedActionResult.fail(stack);
            }

            if (coinConvertType != null) {
                if (!player.getAbilities().creativeMode) {
                    stack.decrement(coinCount);
                }
                player.giveItemStack(new ItemStack(coinConvertType, conversionAmt));

                BlockPos pos = player.getBlockPos();
                world.playSound((PlayerEntity) null, (double) pos.getX() + 0.5D, (double) pos.getY() + 0.5D, (double) pos.getZ() + 0.5D,
                        ModSounds.ITEM_COIN_DROP, SoundCategory.BLOCKS, 0.5F, world.random.nextFloat() * 0.1F + 0.9F);
                return TypedActionResult.success(player.getStackInHand(hand));
            } else {
                return TypedActionResult.fail(stack);
            }
        } else {
            return TypedActionResult.fail(stack);
        }
    }

    public boolean canBeNested() {
        return false;
    }
}
