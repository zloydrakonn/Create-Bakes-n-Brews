package com.zloydrakon.create_bnb.block.custom;

import com.mojang.serialization.MapCodec;
import com.simibubi.create.content.kinetics.base.KineticBlock; // Успадкування від KineticBlock
import com.simibubi.create.foundation.block.IBE; // Реалізація IBE
import com.zloydrakon.create_bnb.block.custom.entity.MoldingMachineBlockEntity;
import com.zloydrakon.create_bnb.block.custom.entity.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BaseEntityBlock; // Для MapCodec
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class MoldingMachineBlock extends KineticBlock implements IBE<MoldingMachineBlockEntity> {

    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final MapCodec<MoldingMachineBlock> CODEC = simpleCodec(MoldingMachineBlock::new);

    public MoldingMachineBlock(Properties pProperties) {
        super(pProperties); // Виклик конструктора KineticBlock
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING);
        super.createBlockStateDefinition(pBuilder); // Додайте, якщо KineticBlock також додає стани
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return this.defaultBlockState().setValue(FACING, pContext.getHorizontalDirection().getOpposite());
    }

    // --- Кінетичні властивості ---
    @Override
    public boolean hasKineticConnections(LevelReader world, BlockPos pos, BlockState state, Direction face) {
        // Дозволяємо підключення кінетичної енергії тільки ЗВЕРХУ
        return face == Direction.UP;
    }

    @Override
    public Direction.Axis getRotationAxis(BlockState state) {
        // Шестірня зверху буде обертатися навколо осі Y
        return Direction.Axis.Y;
    }
    // --- Кінець кінетичних властивостей ---

    // --- IBE реалізація ---
    @Override
    public Class<MoldingMachineBlockEntity> getBlockEntityClass() {
        return MoldingMachineBlockEntity.class;
    }

    @Override
    public BlockEntityType<? extends MoldingMachineBlockEntity> getBlockEntityType() {
        return ModBlockEntities.MOLDING_MACHINE_BE.get();
    }
    // --- Кінець IBE ---

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    // newBlockEntity вже реалізовано в IBE через getBlockEntityType().create(pos, state)
    // Але для BaseEntityBlock все ще може бути потрібен, якщо IBE не надає його
    // Якщо компілятор не скаржиться, можна прибрати. Якщо скаржиться - залиште.
    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return getBlockEntityType().create(pPos, pState);
        // Або return new MoldingMachineBlockEntity(pPos, pState); - якщо попередній рядок не працює.
    }


    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        if (pLevel.isClientSide()) {
            return null;
        }
        // Тепер тікер просто викликає безпараметричний метод tick() нашого BlockEntity
        return createTickerHelper(pBlockEntityType, ModBlockEntities.MOLDING_MACHINE_BE.get(),
                (level, pos, state, blockEntity) -> { // blockEntity тут - це екземпляр MoldingMachineBlockEntity
                    // Переконуємося, що це наш BlockEntity і викликаємо його метод tick()
                    if (blockEntity instanceof MoldingMachineBlockEntity mbe) {
                        mbe.tick();
                    }
                    // Або, якщо createTickerHelper достатньо розумний і blockEntity вже правильного типу:
                    // blockEntity.tick();
                });
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHitResult) {
        if (!pLevel.isClientSide()) {
            BlockEntity entity = pLevel.getBlockEntity(pPos);
            if (entity instanceof MoldingMachineBlockEntity moldingMachineEntity) {
                if (pPlayer instanceof ServerPlayer serverPlayer) {
                    serverPlayer.openMenu(moldingMachineEntity, buf -> buf.writeBlockPos(pPos));
                }
            } else {
                throw new IllegalStateException("Our MenuProvider (MoldingMachineBlockEntity) is missing!");
            }
        }
        return InteractionResult.sidedSuccess(pLevel.isClientSide());
    }

    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        if (pState.getBlock() != pNewState.getBlock()) {
            BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
            if (blockEntity instanceof MoldingMachineBlockEntity be) {
                be.drops();
            }
        }
        super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
    }
}