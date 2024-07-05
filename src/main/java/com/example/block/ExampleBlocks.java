package com.example.block;

import com.example.FabricExample;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

// A class used to store all our registered blocks
// Registering a block actually puts it into the game
public class ExampleBlocks {
    // This defines a CountingFurnace block with block settings that copy the vanilla furnace.
    public static final Block COUNTING_FURNACE = new CountingFurnace(AbstractBlock.Settings.copy(Blocks.FURNACE));
    // defines a FoodChest block with settigns that copy the vanilla chest
    public static final Block FOOD_CHEST = new FoodChest(AbstractBlock.Settings.copy(Blocks.CHEST));
    // Defines the conditional torch block, in the block settings we make the luminance equal to what the getLuminance functions returns.
    public static final Block CONDITIONAL_TORCH = new ConditionalTorch(AbstractBlock.Settings.copy(Blocks.TORCH).luminance(state -> state.get(ConditionalTorch.IS_LIT) ? 15 : 0));
    // Same thing as the conditional torch block but for the wall variant
    public static final Block CONDITIONAL_WALL_TORCH = new WallConditionalTorch(AbstractBlock.Settings.copy(CONDITIONAL_TORCH));

    // This method is called on initialization, it ensures all our blocks are registers
    public static void register() {
        register("counting_furnace", COUNTING_FURNACE);
        register("food_chest", FOOD_CHEST);
        register("conditional_torch", CONDITIONAL_TORCH);
        register("conditional_wall_torch", CONDITIONAL_WALL_TORCH);
    }

    // A helper method to make registering blocks
    private static Block register(String id, Block block) {
        return Registry.register(Registries.BLOCK, new Identifier(FabricExample.MOD_ID, id), block);
    }
}
