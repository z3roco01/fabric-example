package com.example.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.screen.FurnaceScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;


// This is the counting furnaces block entity, a block entity lets blocks do anything more complex than just exist.
// This one extends the AbstractFurnaceBlockEntity and has most of the same code as the FurnaceBlockEntity.
// This means it will act the same as the furnace.
public class CountingFurnaceBlockEntity extends AbstractFurnaceBlockEntity {
    public static final String SMELTED_ITEMS_KEY = "smelted_items";
    private long smeltedItems = 0;

    public CountingFurnaceBlockEntity(BlockPos pos, BlockState state) {
        super(ExampleBlockEntities.COUNTING_FURNACE_ENTITY, pos, state, RecipeType.SMELTING);
    }

    // Returns the display name which will be the same as the blocks translation key
    @Override
    protected Text getContainerName() {
        return Text.translatable(getCachedState().getBlock().getTranslationKey());
    }

    // The overridden method give the game the screen handler for this blockentity if it has one.
    // If you wanted your own custom screen youd change this to use that.
    @Override
    protected ScreenHandler createScreenHandler(int syncId, PlayerInventory playerInventory) {
        return new FurnaceScreenHandler(syncId, playerInventory, this, this.propertyDelegate);
    }

    // Used to store custom data to the supplied nbt so it can be synced
    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.readNbt(nbt, registryLookup);

        // Read smeltedItems from nbt
        this.smeltedItems = nbt.getLong(SMELTED_ITEMS_KEY);
    }

    // Used to read the custom data from supplied nbt
    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.writeNbt(nbt, registryLookup);

        // Store smeltedItems in the nbt so it can be read again
        nbt.putLong(SMELTED_ITEMS_KEY, this.smeltedItems);
    }

    // Send a packet over to the client when this blockentity has updated.
    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    // Create the data for the packet sent to the client.
    @Override
    public NbtCompound toInitialChunkDataNbt(RegistryWrapper.WrapperLookup registryLookup) {
        return createNbt(registryLookup);
    }

    // Override setLastRecipe because it is called everytime something is smelted and so we can count each smelted item
    @Override
    public void setLastRecipe(@Nullable RecipeEntry<?> recipe) {
        super.setLastRecipe(recipe);
        // Increment the smelted items count then mark it dirty so it will be synced.
        this.smeltedItems++;
        this.markDirty();

        // Ensure everything isnt null.
        if(this.world == null || this.world.getServer() == null || this.world.getServer().getPlayerManager() == null)
            return;

        // Sends a message to every play with the number of smelted items
        this.world.getServer().getPlayerManager().broadcast(
                Text.translatable("example.container.counting_furnace.smelted_msg")
                        .append(Text.of(" " + this.smeltedItems + " items !")), false);
    }
}
