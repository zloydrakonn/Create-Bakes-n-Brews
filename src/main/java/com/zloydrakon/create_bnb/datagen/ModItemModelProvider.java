package com.zloydrakon.create_bnb.datagen;

import com.zloydrakon.create_bnb.CreateBnB;
import com.zloydrakon.create_bnb.item.ModItems;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, CreateBnB.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        basicItem(ModItems.SWEET_BERRIES_JUICE_BOTTLE.get());
//        basicItem(ModItems.SWEET_BERRIES_JUICE_BUCKET.get());

    }
}
