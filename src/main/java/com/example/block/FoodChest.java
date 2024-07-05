package com.example.block;

import com.example.block.entity.FoodChestBlockEntity;
import com.mojang.serialization.MapCodec;
import net.minecraft.block.BellBlock;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

// This is the block class for the food chest.
// It will provide the block entity and shape.
public class FoodChest extends BlockWithEntity {
    public static final MapCodec<FoodChest> CODEC = BellBlock.createCodec(FoodChest::new);

    public FoodChest(Settings settings) {
        super(settings);
    }

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return CODEC;
    }

    // Returns the block entity for this block.
    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new FoodChestBlockEntity(pos, state);
    }

    // Override getRenderType because BlockWithEntity make it return invisable
    @Override
    protected BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    // Opens up the screen for the food chest.
    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if(!world.isClient) {
            // Calls the createScreenHandlerFactory method from BlockWithEntity.
            // It will return the BlockEntity casted to a NamedScreenHandlerFactory.
            NamedScreenHandlerFactory screenHandlerFactory = state.createScreenHandlerFactory(world, pos);

            // If it could get a NamedScreenHandlerFactory then open it for the player who used the block.
            if(screenHandlerFactory != null)
                player.openHandledScreen(screenHandlerFactory);
        }

        // Return success because no mater what this succeeded.
        return ActionResult.SUCCESS;
    }

    // This method makes all the stored items drop onto the ground once broken.
    @Override
    protected void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        // If the block is actually different...
        if(state.getBlock() != newState.getBlock()) {
            // Get the food chest BlockEntity.
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if(blockEntity instanceof FoodChestBlockEntity) {
                // Then scatter the items on the ground.
                ItemScatterer.spawn(world, pos, (FoodChestBlockEntity)blockEntity);
                // and update comparators.
                world.updateComparators(pos, this);
            }
            // Also call the super of onStateReplaced.
            super.onStateReplaced(state, world, pos, newState, moved);
        }
    }

    // Enable comparator output on this block.
    @Override
    protected boolean hasComparatorOutput(BlockState state) {
        return true;
    }

    // Return the calculated comparator output from the blockEntity

    @Override
    protected int getComparatorOutput(BlockState state, World world, BlockPos pos) {
        return ScreenHandler.calculateComparatorOutput(world.getBlockEntity(pos));
    }
}
