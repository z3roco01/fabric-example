package com.example.block.entity;

import com.example.block.ConditionalTorch;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

import java.util.List;

public class ConditionalTorchBlockEntity extends BlockEntity {
    // Key used for nbt to store the isLit variable
    public static final String IS_LIT_KEY = "is_lit";
    public static final String OLD_IS_LIT_KEY = "old_is_lit";
    // Variable used to keep track if the torch is lit.
    private boolean isLit = true;
    // Used to check if isLit had changed and if we need to sync it.
    private boolean oldIsLit = true;

    public ConditionalTorchBlockEntity(BlockPos pos, BlockState state) {
        super(ExampleBlockEntities.CONDITIONAL_TORCH_ENTITY, pos, state);
    }

    // Used to store custom data to the supplied nbt so it can be synced
    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.readNbt(nbt, registryLookup);

        // Read isLit from the nbt using the key.
        this.isLit = nbt.getBoolean(IS_LIT_KEY);
        this.oldIsLit = nbt.getBoolean(OLD_IS_LIT_KEY);
    }

    // Used to read the custom data from supplied nbt
    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.writeNbt(nbt, registryLookup);

        // Store the isLit variable in the nbt with the key
        nbt.putBoolean(IS_LIT_KEY, this.isLit);
        nbt.putBoolean(OLD_IS_LIT_KEY, this.oldIsLit);
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

    // This runs every tick or 1/20th of a second.
    // It is called in the ConditionalBlock class and it is used in the getTicker of the conditional torch block
    // It will make the torch lit if theres more than 2 entities in a 16 cube around the torch.
    public static <T extends BlockEntity> void tick(World world, BlockPos pos, BlockState state, T entityArg) {
        // Make sure its been passed a condition torch block entity
        if(!(entityArg instanceof ConditionalTorchBlockEntity))
            return;

        ConditionalTorchBlockEntity entity = (ConditionalTorchBlockEntity)entityArg;
        // Get a list of all entities in a 16x16x16 cube around the torch.
        List<Entity> entities = world.getOtherEntities(null, Box.of(entity.pos.toCenterPos(), 16, 16,16));

        // If theres more than 2 entities around the torch...
        if(entities.size() > 2) {
            // set isLit to true.
           entity.isLit = true;
        }else { // else...
            // Set isLit back to false.
            entity.isLit = false;
        }

        // If isLit has changed since last tick...
        if(entity.oldIsLit != entity.isLit) {
            // Then mark the data as dirty so that the nbt will be resynced.
            entity.markDirty();
            // Set the is_lit property to the current isLit property of the block entity.
            world.setBlockState(pos, state.with(ConditionalTorch.IS_LIT, entity.isLit));
        }

        // Set oldIsLit to isLit so that we can compare it next tick
        entity.oldIsLit = entity.isLit;
    }
}
