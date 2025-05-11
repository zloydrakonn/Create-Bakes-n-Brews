package com.zloydrakon.create_bnb.item;

import com.zloydrakon.create_bnb.CreateBnB;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MOD_TAB =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, CreateBnB.MOD_ID);

    public static final Supplier<CreativeModeTab> ZLOYMOD = CREATIVE_MOD_TAB.register("zloymod",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.SWEET_BERRIES_JUICE_BOTTLE.get()))
                    .title(Component.translatable("creativetab.create_bnb.create_bnb"))
                    .displayItems((parameters, output) -> {
                        output.accept(ModItems.SWEET_BERRIES_JUICE_BOTTLE);
//                        output.accept(ModItems.SWEET_BERRIES_JUICE_BUCKET);
                    }).build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MOD_TAB.register(eventBus);
    }
}
