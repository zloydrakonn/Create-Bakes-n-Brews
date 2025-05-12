package com.zloydrakon.create_bnb.item;

import com.zloydrakon.create_bnb.CreateBnB;
import com.zloydrakon.create_bnb.fluids.CreateBnBFluids;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.jetbrains.annotations.NotNull;


public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MOD_TAB =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, CreateBnB.MOD_ID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> CREATE_BNB = CREATIVE_MOD_TAB.register("create_bnb",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.SWEET_BERRIES_JUICE_BOTTLE.get()))
                    .title(Component.translatable("creativetab.create_bnb.create_bnb"))
                    .displayItems(new CreateBnBDisplayItemsGenerator())
                    .build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MOD_TAB.register(eventBus);
    }

    public static class CreateBnBDisplayItemsGenerator implements CreativeModeTab.DisplayItemsGenerator {
        public CreateBnBDisplayItemsGenerator() {}

        @Override
        public void accept(CreativeModeTab.@NotNull ItemDisplayParameters parameters, CreativeModeTab.@NotNull Output output) {
            output.accept(ModItems.SWEET_BERRIES_JUICE_BOTTLE);
            output.accept(ModItems.SWEET_BERRIES_ROLL);
            output.accept(ModItems.VARENYKY);
            output.accept(ModItems.PELMENI);
            output.accept(CreateBnBFluids.SWEET_BERRIES_JUICE.get().getBucket());
        }
    }
}
