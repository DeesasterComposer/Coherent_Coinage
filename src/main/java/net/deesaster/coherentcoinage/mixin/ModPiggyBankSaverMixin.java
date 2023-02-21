package net.deesaster.coherentcoinage.mixin;

import net.deesaster.coherentcoinage.block.entity.PiggyBankInventory;
import net.deesaster.coherentcoinage.util.IPiggyBankSaver;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public abstract class ModPiggyBankSaverMixin implements IPiggyBankSaver {
    protected PiggyBankInventory piggyBankInventory;

    @Override
    public PiggyBankInventory getPiggyBankInventory() {
        if(this.piggyBankInventory == null) {
            this.piggyBankInventory = new PiggyBankInventory(18);
        }
        return piggyBankInventory;
    }

    @Inject(method = "writeCustomDataToNbt", at = @At("HEAD"))
    protected void injectWriteMethod(NbtCompound nbt, CallbackInfo info) {
        if (piggyBankInventory != null) {
            nbt.put("coherentcoinage.PiggyBankItems", piggyBankInventory.toNbtList());
        }
    }

    @Inject(method = "readCustomDataFromNbt", at = @At("HEAD"))
    protected void injectReadMethod(NbtCompound nbt, CallbackInfo info) {
        if (nbt.contains("coherentcoinage.PiggyBankItems", 9)) {
            if (piggyBankInventory == null) {
                this.piggyBankInventory = new PiggyBankInventory(27);
            }
            piggyBankInventory.readNbtList(nbt.getList("coherentcoinage.PiggyBankItems", 10));
        }
    }
}
