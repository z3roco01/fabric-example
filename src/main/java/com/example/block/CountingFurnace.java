package com.example.block;

import com.example.block.entity.CountingFurnaceBlockEntity;
import com.example.block.entity.ExampleBlockEntities;
import net.minecraft.block.BlockState;
import net.minecraft.block.FurnaceBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

// A furnace block that act like the vanilla furnace by extending the FurnaceBlock
public class CountingFurnace extends FurnaceBlock {
    public CountingFurnace(Settings settings) {
        super(settings);
    }

    // This overrides the createBlockEntity class in the FurnaceBlock class.
    // Its so that it can use the custom CountingFurnaceBlockEntity instead of the vanilla FurnaceBlockEntity.
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new CountingFurnaceBlockEntity(pos, state);
    }

    // Override the getTicker method so we can change the blockEntityType to the proper one
    @Override
    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return FurnaceBlock.validateTicker(world, type, ExampleBlockEntities.COUNTING_FURNACE_ENTITY);
    }

    // This overrides the vanilla openScreen in the FurnaceBlock.
    // Its so that the screen will actually open as in the vanilla class it will only open on block with a FurnaceBlockEntity
    @Override
    protected void openScreen(World world, BlockPos pos, PlayerEntity player) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof CountingFurnaceBlockEntity) {
            player.openHandledScreen((NamedScreenHandlerFactory)blockEntity);
        }
    }
}
