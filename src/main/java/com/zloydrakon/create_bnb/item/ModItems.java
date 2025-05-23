package com.zloydrakon.create_bnb.item;

import com.zloydrakon.create_bnb.CreateBnB;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;


public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(CreateBnB.MOD_ID);

    public static final DeferredItem<Item> SWEET_BERRIES_JUICE_BOTTLE = ITEMS.register("sweet_berries_juice_bottle", // TODO: make drink sound
            () -> new Item(new Item.Properties()
                    .stacksTo(16)
                    .food(ModFoodProperties.SWEET_BERRIES_JUICE_BOTTLE)));

    public static final DeferredItem<Item> SWEET_BERRIES_ROLL = ITEMS.register("sweet_berries_roll",
            () -> new Item(new Item.Properties().food(ModFoodProperties.SWEET_BERRIES_ROLL)));

    public static final DeferredItem<Item> BAR_OF_CHOCOLATE_WITH_BERRIES = ITEMS.register("bar_of_chocolate_with_berries",
            () -> new Item(new Item.Properties().food(ModFoodProperties.BAR_OF_CHOCOLATE_WITH_BERRIES)));



    public static final DeferredItem<Item> PELMEN_WITH_PORKCHOP = ITEMS.register("pelmen_with_porkchop",
            () -> new Item(new Item.Properties().food(ModFoodProperties.PELMEN_WITH_PORKCHOP)));

    public static final DeferredItem<Item> PELMEN_WITH_BEEF = ITEMS.register("pelmen_with_beef",
            () -> new Item(new Item.Properties().food(ModFoodProperties.PELMEN_WITH_BEEF)));



    public static final DeferredItem<Item> VARENYK_WITH_PORKCHOP = ITEMS.register("varenyk_with_porkchop",
            () -> new Item(new Item.Properties().food(ModFoodProperties.VARENYK_WITH_PORKCHOP)));

    public static final DeferredItem<Item> VARENYK_WITH_BEEF = ITEMS.register("varenyk_with_beef",
            () -> new Item(new Item.Properties().food(ModFoodProperties.VARENYK_WITH_BEEF)));

    public static final DeferredItem<Item> VARENYK_WITH_POTATO = ITEMS.register("varenyk_with_potato",
            () -> new Item(new Item.Properties().food(ModFoodProperties.VARENYK_WITH_POTATO)));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}