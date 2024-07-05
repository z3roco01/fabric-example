package com.example.block.entity;

import com.example.FabricExample;
import com.example.block.ExampleBlocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

// This class holds all the block entities of this mod.
public class ExampleBlockEntities {
    // BlockEntities are used for any block that has to do something more than being placed.
    // this defines a BlockEntityType for the counting furnace.
    public static final BlockEntityType<CountingFurnaceBlockEntity> COUNTING_FURNACE_ENTITY = BlockEntityType.Builder.create(CountingFurnaceBlockEntity::new, ExampleBlocks.COUNTING_FURNACE).build();
    public static final BlockEntityType<FoodChestBlockEntity> FOOD_CHEST_ENTITY = BlockEntityType.Builder.create(FoodChestBlockEntity::new, ExampleBlocks.FOOD_CHEST).build();
    public static final BlockEntityType<ConditionalTorchBlockEntity> CONDITIONAL_TORCH_ENTITY = BlockEntityType.Builder.create(ConditionalTorchBlockEntity::new, ExampleBlocks.CONDITIONAL_TORCH, ExampleBlocks.CONDITIONAL_WALL_TORCH).build();

    // This method is called on initialization, it ensures all the items are registered.
    public static void register() {
        register("counting_furnace", COUNTING_FURNACE_ENTITY);
        register("food_chest", FOOD_CHEST_ENTITY);
        register("conditional_torch", CONDITIONAL_TORCH_ENTITY);
    }

    // This is a helper class to make registration easier
    private static BlockEntityType<? extends BlockEntity> register(String id, BlockEntityType<? extends BlockEntity> blockEntityType) {
        return Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(FabricExample.MOD_ID, id), blockEntityType);
    }
}
