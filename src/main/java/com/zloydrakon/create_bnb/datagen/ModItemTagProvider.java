package com.zloydrakon.create_bnb.datagen;

import com.zloydrakon.create_bnb.CreateBnB;
import com.zloydrakon.create_bnb.item.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends ItemTagsProvider {
    public ModItemTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider,
                              CompletableFuture<TagLookup<Block>> blockTags, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, blockTags, CreateBnB.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(Tags.Items.FOODS_BERRY)
                .add(ModItems.SWEET_BERRIES_JUICE_BOTTLE.get())
                .add(ModItems.SWEET_BERRIES_ROLL.get())
                .add(ModItems.BAR_OF_CHOCOLATE_WITH_BERRIES.get());

        tag(Tags.Items.FOODS_BREAD)
                .add(ModItems.PELMENI_WITH_PORKCHOP.get())
                .add(ModItems.PELMENI_WITH_BEEF.get())

                .add(ModItems.VARENYK_WITH_PORKCHOP.get())
                .add(ModItems.VARENYK_WITH_BEEF.get())
                .add(ModItems.VARENYK_WITH_POTATO.get());


    }
}
