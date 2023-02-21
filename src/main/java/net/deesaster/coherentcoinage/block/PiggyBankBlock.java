package net.deesaster.coherentcoinage.block;

import net.deesaster.coherentcoinage.block.entity.PiggyBankBlockEntity;
import net.deesaster.coherentcoinage.block.entity.PiggyBankInventory;
import net.deesaster.coherentcoinage.util.IPiggyBankSaver;
import net.deesaster.coherentcoinage.util.PiggyBankScreenHandler;
import net.minecraft.block.*;
import net.minecraft.block.entity.*;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.state.property.Property;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

public class PiggyBankBlock extends BlockWithEntity implements Waterloggable, BlockEntityProvider {
    public static final DirectionProperty FACING;
    public static final BooleanProperty WATERLOGGED;
    private static final Text CONTAINER_NAME;
    protected static final VoxelShape NORTH_SHAPE = Block.createCuboidShape(4.5D, 0.0D, 2.5D, 11.5D, 8.0D, 12.5D);
    protected static final VoxelShape EAST_SHAPE = Block.createCuboidShape(3.5D, 0.0D, 4.5D, 13.5D, 8.0D, 11.5D);
    protected static final VoxelShape SOUTH_SHAPE = Block.createCuboidShape(4.5D, 0.0D, 3.5D, 11.5D, 8.0D, 13.5D);
    protected static final VoxelShape WEST_SHAPE = Block.createCuboidShape(2.5D, 0.0D, 4.5D, 12.5D, 8.0D, 11.5D);

    public PiggyBankBlock(Settings settings) {
        super(settings);
        this.setDefaultState((BlockState)((BlockState)((BlockState)this.stateManager.getDefaultState()).with(FACING, Direction.NORTH)).with(WATERLOGGED, false));
    }

    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        switch(((Direction)state.get(FACING))) {
            case NORTH:
            default:
                return NORTH_SHAPE;
            case EAST:
                return EAST_SHAPE;
            case SOUTH:
                return SOUTH_SHAPE;
            case WEST:
                return WEST_SHAPE;
        }
    }

    public BlockState getPlacementState(ItemPlacementContext ctx) {
        FluidState fluidState = ctx.getWorld().getFluidState(ctx.getBlockPos());
        return (BlockState)((BlockState)this.getDefaultState().with(FACING, ctx.getPlayerFacing().getOpposite())).with(WATERLOGGED, fluidState.getFluid() == Fluids.WATER);
    }

    public FluidState getFluidState(BlockState state) {
        return (Boolean)state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(new Property[]{FACING, WATERLOGGED});
    }

    static {
        FACING = HorizontalFacingBlock.FACING;
        WATERLOGGED = Properties.WATERLOGGED;
        CONTAINER_NAME = Text.translatable("block.coherentcoinage.piggy_bank");
    }

    /* BLOCK ENTITY */

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (state.getBlock() != newState.getBlock()) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            super.onStateReplaced(state, world, pos, newState, moved);
        }
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos,
                              PlayerEntity player, Hand hand, BlockHitResult hit) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        PiggyBankInventory piggyBankInventory = ((IPiggyBankSaver) player).getPiggyBankInventory();

        if (!world.isClient && piggyBankInventory != null && blockEntity instanceof PiggyBankBlockEntity) {
            NamedScreenHandlerFactory screenHandlerFactory = state.createScreenHandlerFactory(world, pos);

            if (screenHandlerFactory != null) {
                PiggyBankBlockEntity piggyBankBlockEntity = (PiggyBankBlockEntity)blockEntity;
                piggyBankInventory.setActiveBlockEntity(piggyBankBlockEntity);

                player.openHandledScreen(new SimpleNamedScreenHandlerFactory((syncId, inventory, playerx) -> {
                    return PiggyBankScreenHandler.createGeneric9x2(syncId, inventory, piggyBankInventory);
                }, CONTAINER_NAME));
                return ActionResult.CONSUME;
            }
        }

        return ActionResult.SUCCESS;
    }

    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if ((Boolean)state.get(WATERLOGGED)) {
            world.createAndScheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }

        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) {
        return false;
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new PiggyBankBlockEntity(pos, state);
    }
}