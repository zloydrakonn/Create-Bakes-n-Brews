package com.zloydrakon.create_bnb.item;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Items;

public class ModFoodProperties {
    public static final FoodProperties SWEET_BERRIES_JUICE_BOTTLE = new FoodProperties
            .Builder()
            .nutrition(4)
            .saturationModifier(0.5f)
            .usingConvertsTo(Items.GLASS_BOTTLE)
            .effect(() -> new MobEffectInstance(MobEffects.HEALTH_BOOST, 400), 1)
            .build();
}

