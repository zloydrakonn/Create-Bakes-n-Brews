package com.zloydrakon.create_bnb.datagen;

//import com.zloydrakon.create_bnb.block.ModBlocks;
import com.zloydrakon.create_bnb.item.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.ShapelessRecipe;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.conditions.IConditionBuilder;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public ModRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

//    @Override
//    protected void buildRecipes(RecipeOutput recipeOutput) {
//        List<ItemLike> STEEL_SMELTABLES = List.of(ModItems.RAW_STEEL,
//                ModBlocks.STEEL_ORE, ModBlocks.DEEPSLATE_STEEL_ORE);
//
//        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.TERMINAL)
//                .pattern("AGA")
//                .pattern("AGA")
//                .pattern(" A ")
//                .define('A', ModItems.ALUMINIUM)
//                .define('G', Items.TINTED_GLASS)
//                .unlockedBy("has_aluminium", has(ModItems.ALUMINIUM))
//                .save(recipeOutput, "zloymod:terminal_from_aluminium_tinted");
//
//        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.ALUMINIUM, 8)
//                .pattern("ISI")
//                .pattern("ISI")
//                .pattern("ISI")
//                .define('S', ModItems.STEEL_INGOT)
//                .define('I', Items.IRON_INGOT)
//                .unlockedBy("has_steel", has(ModItems.STEEL_INGOT))
//                .save(recipeOutput, "zloymod:aluminium_from_steel_iron");
//
//        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.STEEL_BLOCK, 1)
//                .pattern("SSS")
//                .pattern("SSS")
//                .pattern("SSS")
//                .define('S', ModItems.STEEL_INGOT)
//                .unlockedBy("has_steel", has(ModItems.STEEL_INGOT))
//                .save(recipeOutput, "zloymod:steel_block_from_steel");
//
//
//        oreSmelting(recipeOutput, STEEL_SMELTABLES, RecipeCategory.MISC, ModItems.STEEL_INGOT, 0.25f
//                , 200, "steel");
//        oreBlasting(recipeOutput, STEEL_SMELTABLES, RecipeCategory.MISC, ModItems.STEEL_INGOT, 0.25f
//                , 100, "steel");
//        SimpleCookingRecipeBuilder.smelting(
//                Ingredient.of(
//                        ModBlocks.TERMINAL
//                ),
//                RecipeCategory.MISC,
//                ModItems.ALUMINIUM,
//                0.1F,
//                200);

//    }
}
