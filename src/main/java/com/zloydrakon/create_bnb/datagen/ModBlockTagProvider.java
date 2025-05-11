package com.zloydrakon.create_bnb.datagen;

import com.zloydrakon.create_bnb.CreateBnB;
//import com.zloydrakon.create_bnb.block.ModBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends BlockTagsProvider {
    public ModBlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, CreateBnB.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
//        tag(BlockTags.MINEABLE_WITH_PICKAXE)
//                .add(ModBlocks.STEEL_BLOCK.get())
//                .add(ModBlocks.STEEL_ORE.get())
//                .add(ModBlocks.DEEPSLATE_STEEL_ORE.get())
//                .add(ModBlocks.TERMINAL.get());
//                // .add(ModBlocks.TERMINAL.get())
//        tag(BlockTags.NEEDS_IRON_TOOL)
//                .add(ModBlocks.STEEL_BLOCK.get())
//                .add(ModBlocks.STEEL_ORE.get())
//                .add(ModBlocks.DEEPSLATE_STEEL_ORE.get())
//                .add(ModBlocks.TERMINAL.get());
                // .add(ModBlocks.TERMINAL.get())
    }
}
