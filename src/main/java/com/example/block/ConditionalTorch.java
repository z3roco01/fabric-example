package com.example.block;

import com.example.block.entity.ExampleBlockEntities;
import com.example.FabricExample;
import com.example.block.entity.ConditionalTorchBlockEntity;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

// This class is for the conditional torch.
// It extends BlockWithEntity meaning it will have a block blockEntity
public class ConditionalTorch extends TorchBlock implements BlockEntityProvider {
    // Used to store data on the block and modify it from anywhere.
    // Lets the blockentity share data with the block
    public static final BooleanProperty IS_LIT = BooleanProperty.of("is_lit");

    public ConditionalTorch(Settings settings) {
        // The TorchBlock needs a particle type for its constructor so we pass in the flame particle type.
        this(ParticleTypes.FLAME, settings);
    }

    // Constructor used in the wall conditional torch block
    protected ConditionalTorch(SimpleParticleType simpleParticleType, Settings settings) {
        super(simpleParticleType, settings);
        this.setDefaultState();
    }

    protected void setDefaultState() {
        this.setDefaultState(this.getDefaultState().with(IS_LIT, false));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(IS_LIT);
    }

    // Used by the game to make a new block blockEntity when making this block.
    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new ConditionalTorchBlockEntity(pos, state);
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        // Only make flames if lit.
        if(state.get(IS_LIT)) return;
            super.randomDisplayTick(state, world, pos, random);
    }

    // Returns the ticker of the block entity.
    // It is ran every game tikc ( 1/20th of a second ).
    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        // make sure its the conditional torch block entity like its supposed to be.
        // this is similar to the method validateTicker in BlockWithEntity but we cant extend that since we extend TorchBlock
        if(type == ExampleBlockEntities.CONDITIONAL_TORCH_ENTITY)
            return ConditionalTorchBlockEntity::tick;
        return null;
    }
}
