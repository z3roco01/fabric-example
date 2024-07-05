package com.example.datagen;

import com.example.block.ExampleBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.*;

// This will generate all the models for the blocks.
public class ExampleModelProvider extends FabricModelProvider {
    public ExampleModelProvider(FabricDataOutput fabricDataOutput) {
        super(fabricDataOutput);
    }

    // This generates the models and the blockstates for the blocks.
    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        // Creates a blockstate and models for a torch and wall torch block also generates the item texture
        blockStateModelGenerator.registerTorch(ExampleBlocks.CONDITIONAL_TORCH, ExampleBlocks.CONDITIONAL_WALL_TORCH);
        // Creates a blockstate and models for a furnace like block
        blockStateModelGenerator.registerCooker(ExampleBlocks.COUNTING_FURNACE, TexturedModel.ORIENTABLE);
        // Creates a blockstate and model for a cube with the same texture on each side
        blockStateModelGenerator.registerSimpleCubeAll(ExampleBlocks.FOOD_CHEST);
    }

    // This generates models for items.
    // It isnt need in this mod because the block generation also makes the item models.
    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
    }
}
