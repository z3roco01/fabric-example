package com.example;

import com.example.block.entity.ExampleBlockEntities;
import com.example.item.ExampleItems;
import com.example.block.ExampleBlocks;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


// This class is used to initialise the mod on the server side.
// it does most of the registering for things on both the client and server like blocks and items
public class FabricExample implements ModInitializer {
	// This is the id used ingame for the mod.
	// its used when registering anything to differentiate it from other mods and vanilla.
	public static final String MOD_ID = "example";

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
		LOGGER.info("Server init started !");

		// These are all the register functions.
		ExampleBlocks.register();
		ExampleItems.register();
		ExampleBlockEntities.register();

		LOGGER.info("Server init finished !");
	}
}