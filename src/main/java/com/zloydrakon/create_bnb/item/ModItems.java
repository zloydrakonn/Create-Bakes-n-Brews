package com.zloydrakon.create_bnb.item;

import com.zloydrakon.create_bnb.CreateBnB;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;


public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(CreateBnB.MOD_ID);
    public static final DeferredItem<Item> SWEET_BERRIES_JUICE_BOTTLE = ITEMS.register("sweet_berries_juice_bottle",
            () -> new Item(new Item.Properties()));

//    public static final DeferredItem<Item> SWEET_BERRIES_JUICE_BUCKET = ITEMS.register("sweet_berries_juice_bucket",
//            () -> new Item(new Item.Properties()));
    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
