package net.deesaster.coherentcoinage.mixin;

import net.deesaster.coherentcoinage.item.CoinItem;
import net.deesaster.coherentcoinage.sound.ModSounds;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public class ModCoinThrowMixin {

    @Inject(method = "dropItem(Lnet/minecraft/item/ItemStack;ZZ)Lnet/minecraft/entity/ItemEntity;", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/entity/ItemEntity;setPickupDelay(I)V", shift = At.Shift.AFTER))
    private void injectedCoinDropSound(ItemStack stack, boolean throwRandomly, boolean retainOwnership,
                                       CallbackInfoReturnable<ItemEntity> info) {
        if (stack.getItem() instanceof CoinItem) {
            World world = stack.getHolder().getWorld();
            BlockPos pos = stack.getHolder().getBlockPos();
            world.playSound((PlayerEntity) null, (double) pos.getX(), (double) pos.getY(), (double) pos.getZ(),
                    ModSounds.ITEM_COIN_DROP, SoundCategory.BLOCKS, 0.5F, world.random.nextFloat() * 0.1F + 0.9F);
        }
    }
}
