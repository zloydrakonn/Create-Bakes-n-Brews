package com.zloydrakon.create_bnb.screen.custom; // Замініть на ваш актуальний пакет

import com.zloydrakon.create_bnb.block.ModBlocks;
import com.zloydrakon.create_bnb.block.custom.entity.MoldingMachineBlockEntity;
import com.zloydrakon.create_bnb.screen.ModMenuTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.SlotItemHandler;

public class MoldingMachineMenu extends AbstractContainerMenu {
    public final MoldingMachineBlockEntity blockEntity;
    private final Level level;
    private final ContainerData data;

    // Клієнтський конструктор
    public MoldingMachineMenu(int pContainerId, Inventory inv, FriendlyByteBuf extraData) {
        // Важливо: створюємо SimpleContainerData з правильною кількістю полів для клієнта,
        // але реальні дані будуть синхронізовані через addDataSlots(this.data)
        this(pContainerId, inv, getBlockEntityFromBuf(inv.player, extraData), new SimpleContainerData(2));
    }

    // Допоміжний метод для отримання BlockEntity на клієнті (з попередньої відповіді)
    private static MoldingMachineBlockEntity getBlockEntityFromBuf(Player player, FriendlyByteBuf buf) {
        final BlockEntity be = player.level().getBlockEntity(buf.readBlockPos());
        if (be instanceof MoldingMachineBlockEntity mbe) {
            return mbe;
        }
        throw new IllegalStateException("Incorrect BlockEntity type at pos! Expected MoldingMachineBlockEntity.");
    }


    // Серверний конструктор (і для внутрішнього виклику з клієнтського)
    public MoldingMachineMenu(int pContainerId, Inventory inv, BlockEntity entity, ContainerData data) {
        super(ModMenuTypes.MOLDING_MACHINE_MENU.get(), pContainerId);
        checkContainerSize(inv, 2); // 2 - кількість слотів у вашому BlockEntity (вхід+вихід)

        if (!(entity instanceof MoldingMachineBlockEntity)) {
            // Це може статися, якщо getBlockEntityFromBuf поверне null або неправильний тип
            throw new IllegalStateException("Incorrect BlockEntity instance provided: " + entity);
        }
        this.blockEntity = ((MoldingMachineBlockEntity) entity);
        this.level = inv.player.level();
        this.data = data;

        IItemHandler itemHandler = this.blockEntity.getItemHandlerCapability();

        if (itemHandler != null && itemHandler.getSlots() >= 2) { // Перевірка на null та кількість слотів
            this.addSlot(new SlotItemHandler(itemHandler, 0, 54, 34)); // Слот входу (індекс 0)
            this.addSlot(new SlotItemHandler(itemHandler, 1, 104, 34)); // Слот виходу (індекс 1)
        } else {
            // Можна додати логування помилки, якщо itemHandler не знайдено або має неправильну кількість слотів
            // throw new IllegalStateException("Item handler not available or has incorrect slot count for MoldingMachineBlockEntity");
        }

        addPlayerInventory(inv);
        addPlayerHotbar(inv);
        addDataSlots(data); // Важливо для синхронізації даних (progress, maxProgress)
    }

    public boolean isCrafting() {
        return data.get(0) > 0;
    }

    public int getScaledArrowProgress() {
        int progress = this.data.get(0);
        int maxProgress = this.data.get(1);
        int arrowPixelSize = 24;

        return maxProgress != 0 && progress != 0 ? progress * arrowPixelSize / maxProgress : 0;
    }

    // Індекси слотів для quickMoveStack
    private static final int MACHINE_SLOT_COUNT = 2; // Кількість слотів машини (вхід + вихід)
    private static final int PLAYER_INVENTORY_START_INDEX = MACHINE_SLOT_COUNT;
    private static final int PLAYER_INVENTORY_END_INDEX = PLAYER_INVENTORY_START_INDEX + 3 * 9 -1; // 27 слотів
    private static final int HOTBAR_START_INDEX = PLAYER_INVENTORY_END_INDEX + 1;
    private static final int HOTBAR_END_INDEX = HOTBAR_START_INDEX + 9 - 1;


    @Override
    public ItemStack quickMoveStack(Player playerIn, int pIndex) {
        Slot sourceSlot = slots.get(pIndex);
        if (sourceSlot == null || !sourceSlot.hasItem()) return ItemStack.EMPTY;
        ItemStack sourceStack = sourceSlot.getItem();
        ItemStack copyOfSourceStack = sourceStack.copy();

        if (pIndex < MACHINE_SLOT_COUNT) { // Клік у слоті машини (0 або 1)
            // Перемістити в інвентар гравця (від PLAYER_INVENTORY_START_INDEX до HOTBAR_END_INDEX + 1)
            if (!moveItemStackTo(sourceStack, PLAYER_INVENTORY_START_INDEX, HOTBAR_END_INDEX + 1, true)) {
                return ItemStack.EMPTY;
            }
        } else if (pIndex <= HOTBAR_END_INDEX) { // Клік в інвентарі гравця або хотбарі
            // Спробувати перемістити у вхідний слот машини (слот 0)
            if (!moveItemStackTo(sourceStack, 0, 1, false)) { // Тільки у слот 0
                return ItemStack.EMPTY;
            }
        } else {
            // System.out.println("Invalid slotIndex for quickMoveStack: " + pIndex);
            return ItemStack.EMPTY;
        }

        if (sourceStack.isEmpty()) {
            sourceSlot.set(ItemStack.EMPTY);
        } else {
            sourceSlot.setChanged();
        }

        if (sourceStack.getCount() == copyOfSourceStack.getCount()) {
            return ItemStack.EMPTY;
        }

        sourceSlot.onTake(playerIn, sourceStack);
        return copyOfSourceStack;
    }


    @Override
    public boolean stillValid(Player pPlayer) {
        return stillValid(ContainerLevelAccess.create(level, blockEntity.getBlockPos()),
                pPlayer, ModBlocks.MOLDING_MACHINE.get());
    }

    private void addPlayerInventory(Inventory playerInventory) {
        for (int i = 0; i < 3; ++i) { // 3 ряди
            for (int l = 0; l < 9; ++l) { // 9 колонок
                this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 8 + l * 18, 84 + i * 18));
            }
        }
    }

    private void addPlayerHotbar(Inventory playerInventory) {
        for (int i = 0; i < 9; ++i) { // 9 слотів хотбару
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
        }
    }
}