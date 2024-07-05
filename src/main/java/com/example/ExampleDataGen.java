package com.example;

import com.example.datagen.ExampleLanguageProvider;
import com.example.datagen.ExampleModelProvider;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

// This is the data generator class.
// It handles all the data generators.
// Data generators generate things like models and translations with code.
public class ExampleDataGen implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        // Get the pack for our mod.
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

        // Add all the generators to the pack.
        pack.addProvider(ExampleModelProvider::new);
        pack.addProvider(ExampleLanguageProvider::new);
    }
}
