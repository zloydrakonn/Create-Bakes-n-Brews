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
            .effect(() -> new MobEffectInstance(MobEffects.HEALTH_BOOST, 200), 1)
            .build();


    public static final FoodProperties SWEET_BERRIES_ROLL = new FoodProperties
            .Builder()
            .nutrition(6)
            .saturationModifier(0.8f)
            .effect(() -> new MobEffectInstance(MobEffects.HEALTH_BOOST, 100), 1)
            .build();


    public static final FoodProperties VARENYKY = new FoodProperties
            .Builder()
            .nutrition(5)
            .effect(() -> new MobEffectInstance(MobEffects.ABSORPTION, 200), 1)
            .saturationModifier(0.6f)
            .build();


    public static final FoodProperties PELMENI = new FoodProperties
            .Builder()
            .nutrition(5)
            .effect(() -> new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 200), 1)
            .saturationModifier(0.6f)
            .build();

}

