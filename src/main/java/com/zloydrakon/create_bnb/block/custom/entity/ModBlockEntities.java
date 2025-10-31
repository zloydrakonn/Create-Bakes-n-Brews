package com.zloydrakon.create_bnb.block.custom.entity;

import com.zloydrakon.create_bnb.CreateBnB;
import com.zloydrakon.create_bnb.block.ModBlocks;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, CreateBnB.MOD_ID);

    public static final Supplier<BlockEntityType<MoldingMachineBlockEntity>> MOLDING_MACHINE_BE =
            BLOCK_ENTITIES.register("molding_machine_be", () -> BlockEntityType.Builder.of(
                    MoldingMachineBlockEntity::new, ModBlocks.MOLDING_MACHINE.get()).build(null));

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}