package com.zloydrakon.create_bnb.block.custom.entity;

import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import com.zloydrakon.create_bnb.recipe.MoldingMachineRecipe;
import com.zloydrakon.create_bnb.recipe.MoldingMachineRecipeInput;
import com.zloydrakon.create_bnb.recipe.ModRecipes;
import com.zloydrakon.create_bnb.screen.custom.MoldingMachineMenu;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level; // Додано для this.level
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState; // Додано для this.blockState
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class MoldingMachineBlockEntity extends KineticBlockEntity implements MenuProvider {

    final ItemStackHandler itemHandler = new ItemStackHandler(2) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }
    };

    protected int progress = 0;
    protected int maxProgress = 100;

    protected final ContainerData data;

    public MoldingMachineBlockEntity(BlockPos pPos, BlockState pState) {
        super(ModBlockEntities.MOLDING_MACHINE_BE.get(), pPos, pState);
        this.data = new ContainerData() {
            // ... (реалізація ContainerData залишається без змін) ...
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> MoldingMachineBlockEntity.this.progress;
                    case 1 -> MoldingMachineBlockEntity.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> MoldingMachineBlockEntity.this.progress = value;
                    case 1 -> MoldingMachineBlockEntity.this.maxProgress = value;
                }
            }

            @Override
            public int getCount() {
                return 2;
            }
        };
    }

    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(
                Capabilities.ItemHandler.BLOCK,
                ModBlockEntities.MOLDING_MACHINE_BE.get(),
                (blockEntity, context) -> blockEntity.itemHandler
        );
    }

    public IItemHandler getItemHandlerCapability() {
        return this.itemHandler;
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("container.create_bnb.molding_machine");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new MoldingMachineMenu(pContainerId, pPlayerInventory, this, this.data);
    }

    // --- Логіка KineticBlockEntity ---
    // getStressApplied() поки що прибираємо

    // Перевизначаємо безпараметричний метод tick()
    @Override
    public void tick() {
        super.tick(); // Дуже важливо викликати батьківський метод tick()

        // this.level, this.worldPosition, this.blockState доступні як поля класу
        if (this.level == null || this.level.isClientSide()) { // Перевірка на null для level
            return;
        }

        if (getSpeed() == 0) {
            if (progress > 0) {
                progress--;
                setChanged(); // Не забувайте викликати setChanged() після змін, що потребують збереження
            }
            return;
        }

        if (hasRecipe()) {
            progress++;
            setChanged();
            if (progress >= maxProgress) {
                craftItem();
                resetProgress();
            }
        } else {
            resetProgress();
        }
    }

    private void resetProgress() {
        if (this.progress > 0) {
            this.progress = 0;
            setChanged();
        }
    }

    private boolean hasRecipe() {
        if (this.level == null) return false; // Додаткова перевірка
        MoldingMachineRecipeInput recipeInput = new MoldingMachineRecipeInput(this.itemHandler.getStackInSlot(0));
        Optional<RecipeHolder<MoldingMachineRecipe>> recipe = this.level.getRecipeManager()
                .getRecipeFor(ModRecipes.MOLDING_MACHINE_TYPE.get(), recipeInput, this.level);

        if (recipe.isEmpty()) {
            return false;
        }

        ItemStack result = recipe.get().value().getResultItem(this.level.registryAccess());
        return canInsertAmountIntoOutputSlot(result.getCount()) && canInsertItemIntoOutputSlot(result);
    }

    private void craftItem() {
        if (this.level == null) return; // Додаткова перевірка
        MoldingMachineRecipeInput recipeInput = new MoldingMachineRecipeInput(this.itemHandler.getStackInSlot(0));
        Optional<RecipeHolder<MoldingMachineRecipe>> recipeHolder = this.level.getRecipeManager()
                .getRecipeFor(ModRecipes.MOLDING_MACHINE_TYPE.get(), recipeInput, this.level);

        if (recipeHolder.isEmpty()) {
            return;
        }

        MoldingMachineRecipe recipe = recipeHolder.get().value();
        ItemStack result = recipe.getResultItem(this.level.registryAccess());

        this.itemHandler.extractItem(0, 1, false);
        this.itemHandler.insertItem(1, result.copy(), false);
    }

    private boolean canInsertItemIntoOutputSlot(ItemStack itemStackToInsert) {
        ItemStack outputSlotStack = this.itemHandler.getStackInSlot(1);
        if (outputSlotStack.isEmpty()) {
            return true;
        }
        return ItemStack.isSameItemSameComponents(outputSlotStack, itemStackToInsert) &&
                outputSlotStack.getCount() + itemStackToInsert.getCount() <= outputSlotStack.getMaxStackSize() &&
                outputSlotStack.getCount() + itemStackToInsert.getCount() <= this.itemHandler.getSlotLimit(1);
    }

    private boolean canInsertAmountIntoOutputSlot(int amountToInsert) {
        ItemStack outputSlotStack = this.itemHandler.getStackInSlot(1);
        if (outputSlotStack.isEmpty()) {
            return true;
        }
        return outputSlotStack.getCount() + amountToInsert <= outputSlotStack.getMaxStackSize() &&
                outputSlotStack.getCount() + amountToInsert <= this.itemHandler.getSlotLimit(1);
    }

    // --- Оновлені методи збереження та завантаження NBT ---
    @Override
    protected void write(CompoundTag compound, HolderLookup.Provider registries, boolean clientPacket) {
        super.write(compound, registries, clientPacket);
        compound.put("inventory", itemHandler.serializeNBT(registries));
        compound.putInt("molding_machine.progress", progress);
    }

    @Override
    protected void read(CompoundTag compound, HolderLookup.Provider registries, boolean clientPacket) {
        super.read(compound, registries, clientPacket);
        itemHandler.deserializeNBT(registries, compound.getCompound("inventory"));
        progress = compound.getInt("molding_machine.progress");
    }

    public void drops() {
        if (this.level == null) return; // Додаткова перевірка
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }
        Containers.dropContents(this.level, this.worldPosition, inventory);
    }
}