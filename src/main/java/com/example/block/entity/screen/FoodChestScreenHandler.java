package com.example.block.entity.screen;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;

// This method will be the screen handler for the food chest and extends the GenericContainerScreenHandler which does almost everything.
// It
public class FoodChestScreenHandler extends ScreenHandler {
    private final Inventory inventory;
    public FoodChestScreenHandler(int syncId, PlayerInventory playerInventory) {
        // Calls this constructor with empty simple inventory
        this(syncId, playerInventory, new SimpleInventory(27));
    }

    public FoodChestScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory) {
        // Calls super passing all the parameters and the screen handler type
        super(ExampleScreenHandlers.FOOD_CHEST_SCREEN_HANDLER, syncId);
        int k;
        int j;
        GenericContainerScreenHandler.checkSize(inventory, 27);
        this.inventory = inventory;
        inventory.onOpen(playerInventory.player);
        // ths creates all the slots for this inventory but doesnt render them, thats the job of the screen.
        int i = -18;
        for (j = 0; j < 3; ++j) {
            for (k = 0; k < 9; ++k) {
                this.addSlot(new FoodOnlySlot(inventory, k + j * 9, 8 + k * 18, 18 + j * 18));
            }
        }
        for (j = 0; j < 3; ++j) {
            for (k = 0; k < 9; ++k) {
                this.addSlot(new Slot(playerInventory, k + j * 9 + 9, 8 + k * 18, 103 + j * 18 + i));
            }
        }
        for (j = 0; j < 9; ++j) {
            this.addSlot(new Slot(playerInventory, j, 8 + j * 18, 161 + i));
        }
        // Sets each slot to a custom slot that will only allow items to be inserted if they are valid.

    }

    // Returns if the player can use this screen.
    @Override
    public boolean canUse(PlayerEntity player) {
        return this.inventory.canPlayerUse(player);
    }

    // Called on shift+click.
    @Override
    public ItemStack quickMove(PlayerEntity player, int slot) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot2 = this.slots.get(slot);
        if (slot2.hasStack()) {
            ItemStack itemStack2 = slot2.getStack();
            itemStack = itemStack2.copy();
            if (slot < 27 ? !this.insertItem(itemStack2, 27, this.slots.size(), true) : !this.insertItem(itemStack2, 0, 27, false)) {
                return ItemStack.EMPTY;
            }
            if (itemStack2.isEmpty()) {
                slot2.setStack(ItemStack.EMPTY);
            } else {
                slot2.markDirty();
            }
        }
        return itemStack;
    }

    // Calls when the screen is closed.
    @Override
    public void onClosed(PlayerEntity player) {
        super.onClosed(player);
        this.inventory.onClose(player);
    }

    // This class is the custom slot for this inventory.
    // It only allows food to be inserted.
    private class FoodOnlySlot extends Slot{
        public FoodOnlySlot(Inventory inventory, int index, int x, int y) {
            super(inventory, index, x, y);
        }

        @Override
        public boolean canInsert(ItemStack stack) {
            // Returns if this stack has a food component meaning its food.
            return stack.get(DataComponentTypes.FOOD) != null;
        }
    }
}
