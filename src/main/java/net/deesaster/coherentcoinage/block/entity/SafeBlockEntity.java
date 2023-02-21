package net.deesaster.coherentcoinage.block.entity;

import net.deesaster.coherentcoinage.block.ModBlocks;
import net.deesaster.coherentcoinage.sound.ModSounds;
import net.deesaster.coherentcoinage.util.ISafeSaver;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.ViewerCountManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class SafeBlockEntity extends BlockEntity implements NamedScreenHandlerFactory {
    private final ViewerCountManager stateManager = new ViewerCountManager() {
        protected void onContainerOpen(World world, BlockPos pos, BlockState state) {
            world.playSound((PlayerEntity) null, (double) pos.getX() + 0.5D, (double) pos.getY() + 0.5D, (double) pos.getZ() + 0.5D,
                    ModSounds.BLOCK_SAFE_OPEN, SoundCategory.BLOCKS, 0.5F, world.random.nextFloat() * 0.1F + 0.9F);
        }

        protected void onContainerClose(World world, BlockPos pos, BlockState state) {
            world.playSound((PlayerEntity) null, (double) pos.getX() + 0.5D, (double) pos.getY() + 0.5D, (double) pos.getZ() + 0.5D,
                    ModSounds.BLOCK_SAFE_CLOSE, SoundCategory.BLOCKS, 0.5F, world.random.nextFloat() * 0.1F + 0.9F);
        }

        protected void onViewerCountUpdate(World world, BlockPos pos, BlockState state, int oldViewerCount, int newViewerCount) {
            world.addSyncedBlockEvent(SafeBlockEntity.this.pos, ModBlocks.SAFE, 1, newViewerCount);
        }

        protected boolean isPlayerViewing(PlayerEntity player) {
            return ((ISafeSaver) player).getSafeInventory().isActiveBlockEntity(SafeBlockEntity.this);
        }
    };

    public SafeBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.SAFE, pos, state);
    }

    public void onOpen(PlayerEntity player) {
        if (!this.removed && !player.isSpectator()) {
            this.stateManager.openContainer(player, this.getWorld(), this.getPos(), this.getCachedState());
        }

    }

    public void onClose(PlayerEntity player) {
        if (!this.removed && !player.isSpectator()) {
            this.stateManager.closeContainer(player, this.getWorld(), this.getPos(), this.getCachedState());
        }
    }

    public boolean canPlayerUse(PlayerEntity player) {
        if (this.world.getBlockEntity(this.pos) != this) {
            return false;
        } else {
            return !(player.squaredDistanceTo((double)this.pos.getX() + 0.5D, (double)this.pos.getY() + 0.5D, (double)this.pos.getZ() + 0.5D) > 64.0D);
        }
    }

    public void onScheduledTick() {
        if (!this.removed) {
            this.stateManager.updateViewerCount(this.getWorld(), this.getPos(), this.getCachedState());
        }
    }

    @Override
    public Text getDisplayName() {
        return Text.translatable("block.coherentcoinage.safe");
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
        return null;
    }
}
