package com.zloydrakon.create_bnb.recipe;

import com.zloydrakon.create_bnb.CreateBnB;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModRecipes {
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
            DeferredRegister.create(Registries.RECIPE_SERIALIZER, CreateBnB.MOD_ID);
    public static final DeferredRegister<RecipeType<?>> TYPES =
            DeferredRegister.create(Registries.RECIPE_TYPE, CreateBnB.MOD_ID);

    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<MoldingMachineRecipe>> MOLDING_MACHINE_SERIALIZER =
            SERIALIZERS.register("molding_machine", MoldingMachineRecipe.Serializer::new);
    public static final DeferredHolder<RecipeType<?>, RecipeType<MoldingMachineRecipe>> MOLDING_MACHINE_TYPE =
            TYPES.register("molding_machine", () -> new RecipeType<MoldingMachineRecipe>() {
                @Override
                public String toString() {
                    return "molding_machine";
                }
            });


    public static void register(IEventBus eventBus) {
        SERIALIZERS.register(eventBus);
        TYPES.register(eventBus);
    }
}
