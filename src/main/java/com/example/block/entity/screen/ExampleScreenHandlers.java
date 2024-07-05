package com.example.block.entity.screen;

import com.example.FabricExample;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.resource.featuretoggle.FeatureFlags;
import net.minecraft.resource.featuretoggle.FeatureSet;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

// This holds all the screen handler types
public class ExampleScreenHandlers {
    public static final ScreenHandlerType<FoodChestScreenHandler> FOOD_CHEST_SCREEN_HANDLER;

    static {
        FOOD_CHEST_SCREEN_HANDLER = Registry.register(Registries.SCREEN_HANDLER,
                new Identifier(FabricExample.MOD_ID, "food_chest"),
                new ScreenHandlerType<FoodChestScreenHandler>(FoodChestScreenHandler::new, FeatureSet.of(FeatureFlags.VANILLA)));
    }
}
