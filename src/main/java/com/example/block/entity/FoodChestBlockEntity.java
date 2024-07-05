package com.example.block.entity;

import com.example.block.entity.inventory.ImplementedInventory;
import com.example.block.entity.screen.FoodChestScreenHandler;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

public class FoodChestBlockEntity extends BlockEntity implements NamedScreenHandlerFactory, ImplementedInventory {
    // Defines the inventory, has 27 slots like a normal chest.
    // Also initialises all slots to empty.
    private DefaultedList<ItemStack> inventory = DefaultedList.ofSize(27, ItemStack.EMPTY);

    // Constructor for the BlockEntity.
    public FoodChestBlockEntity(BlockPos pos, BlockState state) {
        super(ExampleBlockEntities.FOOD_CHEST_ENTITY, pos, state);
    }

    // From ImplementedInventory, returns the items stored.
    @Override
    public DefaultedList<ItemStack> getItems() {
       return this.inventory;
    }


    @Override
    public boolean canTransferTo(Inventory hopperInventory, int slot, ItemStack stack) {
        return this.isValid(slot, stack);
    }

    // Gets the translated name from the block and returns it as the display name in the ui.
    @Override
    public Text getDisplayName() {
        return Text.translatable(getCachedState().getBlock().getTranslationKey());
    }

    // Creates the screen handler for this BlockEntity
    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new FoodChestScreenHandler(syncId, playerInventory, this);
    }

    // Reads nbt from storage/a packet
    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.readNbt(nbt, registryLookup);
        Inventories.readNbt(nbt, this.inventory, registryLookup);
    }

    // Writes nbt to storage/a packet
    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.writeNbt(nbt, registryLookup);
        Inventories.readNbt(nbt, this.inventory, registryLookup);
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
}
