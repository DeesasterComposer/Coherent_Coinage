package net.deesaster.coherentcoinage.block.entity;

import net.deesaster.coherentcoinage.item.CoinItem;
import net.deesaster.coherentcoinage.util.PiggyBankScreenHandler;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;

public class BankSlot extends Slot {
    private final ScreenHandler handler;

    public BankSlot(ScreenHandler handler, Inventory inventory, int index, int x, int y) {
        super(inventory, index, x, y);
        this.handler = handler;
    }

    public boolean canInsert(ItemStack stack) {
        return stack.getItem() instanceof CoinItem;
    }

    public int getMaxItemCount(ItemStack stack) {
        return super.getMaxItemCount(stack);
    }
}
