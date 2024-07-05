package com.example.datagen;

import com.example.block.ExampleBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

// Will generate all the translations for one language.
public class ExampleLanguageProvider extends FabricLanguageProvider {
    public ExampleLanguageProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        // You specify the language here with the language code, in this case en_us with is american english.
        super(dataOutput, "en_us", registryLookup);
    }

    // This create the translations for everything.
    @Override
    public void generateTranslations(RegistryWrapper.WrapperLookup registryLookup, TranslationBuilder translationBuilder) {
        // These are the translations for the placed blocks.
        translationBuilder.add(ExampleBlocks.CONDITIONAL_TORCH, "Conditional Torch");
        translationBuilder.add(ExampleBlocks.COUNTING_FURNACE, "Counting Furnace");
        translationBuilder.add(ExampleBlocks.FOOD_CHEST, "Food Chest");
        // The items have the same translation key as their block.

        // This is a translation for a raw translation key.
        translationBuilder.add("example.container.counting_furnace.smelted_msg", "Smelted");
    }
}
