package com.zloydrakon.create_bnb.block;

import com.zloydrakon.create_bnb.CreateBnB;
import com.zloydrakon.create_bnb.block.custom.MoldingMachineBlock;
import com.zloydrakon.create_bnb.block.custom.entity.MoldingMachineBlockEntity;
import com.zloydrakon.create_bnb.item.ModItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Function;
import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS =
            DeferredRegister.createBlocks(CreateBnB.MOD_ID);

//    public static final DeferredBlock<Block> STEEL_BLOCK = registerBlock("steel_block",
//            () -> new Block(BlockBehaviour.Properties.of()
//                    .strength(4f).requiresCorrectToolForDrops().sound(SoundType.METAL)));

    public static final DeferredBlock<Block> MOLDING_MACHINE = registerBlock("molding_machine", MoldingMachineBlock::new);

    private static <T extends Block> DeferredBlock<T> registerBlock(String name, Function<BlockBehaviour.Properties, T> function) {
        DeferredBlock<T> toReturn = BLOCKS.registerBlock(name, function);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> void registerBlockItem(String name, DeferredBlock<T> block) {
        ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
