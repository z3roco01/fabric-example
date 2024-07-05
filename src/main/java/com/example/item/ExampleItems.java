package com.example.item;

import com.example.FabricExample;
import com.example.block.ExampleBlocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.VerticallyAttachableBlockItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;

public class ExampleItems {
    // This defines a item form of the coutning furnace block so it can be used in inventories.
    public static final BlockItem COUNTING_FURNACE_ITEM = new BlockItem(ExampleBlocks.COUNTING_FURNACE, new Item.Settings());
    public static final BlockItem FOOD_CHEST_ITEM = new BlockItem(ExampleBlocks.FOOD_CHEST, new Item.Settings());
    public static final Item CONDITIONAL_TORCH_ITEM = new VerticallyAttachableBlockItem(ExampleBlocks.CONDITIONAL_TORCH, ExampleBlocks.CONDITIONAL_WALL_TORCH, new Item.Settings().maxCount(33), Direction.DOWN);

    // This method is called on initialization, it ensures all the items are registered.
    public static void register() {
        register("counting_furnace", COUNTING_FURNACE_ITEM);
        register("food_chest", FOOD_CHEST_ITEM);
        register("conditional_torch", CONDITIONAL_TORCH_ITEM);
    }

    // A helper method to make the registration of items simpler
    private static Item register(String id, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(FabricExample.MOD_ID, id), item);
    }
}
