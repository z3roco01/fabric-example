package com.example;

import com.example.block.ExampleBlocks;
import com.example.block.entity.screen.ExampleScreenHandlers;
import com.example.screen.FoodChestScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.render.RenderLayer;

// This class is like the FabricExample class but instead of for the server its for client initialization.
// Its for initializing rendering things and stuff like that.
public class FabricExampleClient implements ClientModInitializer {
	// This entrypoint is suitable for setting up client-specific logic, such as rendering.
	@Override
	public void onInitializeClient() {
		// Register the screen for the food chest with the screen handler.
		HandledScreens.register(ExampleScreenHandlers.FOOD_CHEST_SCREEN_HANDLER, FoodChestScreen::new);
		// Allows for a block to have transparency in its textures
		BlockRenderLayerMap.INSTANCE.putBlock(ExampleBlocks.CONDITIONAL_TORCH, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(ExampleBlocks.CONDITIONAL_WALL_TORCH, RenderLayer.getCutout());
	}
}