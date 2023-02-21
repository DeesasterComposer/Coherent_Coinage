package net.deesaster.coherentcoinage.mixin;

import net.deesaster.coherentcoinage.block.entity.PiggyBankInventory;
import net.deesaster.coherentcoinage.block.entity.SafeInventory;
import net.deesaster.coherentcoinage.util.IPiggyBankSaver;
import net.deesaster.coherentcoinage.util.ISafeSaver;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class ModSafeSaverMixin implements ISafeSaver {
    protected SafeInventory safeInventory;

    @Override
    public SafeInventory getSafeInventory() {
        if(this.safeInventory == null) {
            this.safeInventory = new SafeInventory(54);
        }
        return safeInventory;
    }

    @Inject(method = "writeCustomDataToNbt", at = @At("HEAD"))
    protected void injectWriteMethod(NbtCompound nbt, CallbackInfo info) {
        if (safeInventory != null) {
            nbt.put("coherentcoinage.SafeItems", safeInventory.toNbtList());
        }
    }

    @Inject(method = "readCustomDataFromNbt", at = @At("HEAD"))
    protected void injectReadMethod(NbtCompound nbt, CallbackInfo info) {
        if (nbt.contains("coherentcoinage.SafeItems", 9)) {
            if (safeInventory == null) {
                this.safeInventory = new SafeInventory(54);
            }
            safeInventory.readNbtList(nbt.getList("coherentcoinage.SafeItems", 10));
        }
    }
}
