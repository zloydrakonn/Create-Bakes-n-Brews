package com.zloydrakon.create_bnb.datagen;

import com.zloydrakon.create_bnb.CreateBnB;
// import com.zloydrakon.create_bnb.block.ModBlocks;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, CreateBnB.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
//        blockWithItem(ModBlocks.STEEL_BLOCK);
//        blockWithItem(ModBlocks.STEEL_ORE);
//        blockWithItem(ModBlocks.DEEPSLATE_STEEL_ORE);


    }

    private void blockWithItem(DeferredBlock<?> deferredBlock) {
        simpleBlockWithItem(deferredBlock.get(), cubeAll(deferredBlock.get()));
    }
}
