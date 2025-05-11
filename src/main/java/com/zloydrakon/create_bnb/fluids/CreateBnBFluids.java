package com.zloydrakon.create_bnb.fluids;

import com.simibubi.create.AllFluids;
import com.simibubi.create.content.fluids.VirtualFluid;
import com.simibubi.create.infrastructure.config.AllConfigs;
import com.tterrag.registrate.builders.FluidBuilder;
import com.tterrag.registrate.util.entry.FluidEntry;
import com.zloydrakon.create_bnb.item.ModCreativeModeTabs;
import net.createmod.catnip.theme.Color;
import net.minecraft.core.BlockPos;

import net.minecraft.resources.ResourceLocation;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.material.FluidState;

import net.neoforged.neoforge.fluids.BaseFlowingFluid;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.joml.Vector3f;


import java.util.function.Supplier;

import static com.zloydrakon.create_bnb.CreateBnB.REGISTRATE;

public class CreateBnBFluids {

    static {
        REGISTRATE.setCreativeTab(ModCreativeModeTabs.CREATE_BNB);
    }

    public static final FluidEntry<BaseFlowingFluid.Flowing> SWEET_BERRIES_JUICE =
            REGISTRATE.standardFluid("sweet_berries_juice",
                            SolidRenderedPlaceableFluidType.create(0x9D0804,
                                    () -> 1f / 32f * AllConfigs.client().chocolateTransparencyMultiplier.getF()))
                    .properties(b -> b.viscosity(1500)
                            .density(1400))
                    .fluidProperties(p -> p.levelDecreasePerBlock(2)
                            .tickRate(25)
                            .slopeFindDistance(3)
                            .explosionResistance(100f))
                    .source(BaseFlowingFluid.Source::new)
                    .bucket()
                    .build()
                    .register();

    public static void register() {}

    private static class SolidRenderedPlaceableFluidType extends AllFluids.TintedFluidType {

        private Vector3f fogColor;
        private Supplier<Float> fogDistance;

        public static FluidBuilder.FluidTypeFactory create(int fogColor, Supplier<Float> fogDistance) {
            return (p, s, f) -> {
                SolidRenderedPlaceableFluidType fluidType = new SolidRenderedPlaceableFluidType(p, s, f);
                fluidType.fogColor = new Color(fogColor, false).asVectorF();
                fluidType.fogDistance = fogDistance;
                return fluidType;
            };
        }

        private SolidRenderedPlaceableFluidType(Properties properties, ResourceLocation stillTexture,
                                                ResourceLocation flowingTexture) {
            super(properties, stillTexture, flowingTexture);
        }

        @Override
        protected int getTintColor(FluidStack stack) {
            return NO_TINT;
        }


        @Override
        public int getTintColor(FluidState state, BlockAndTintGetter world, BlockPos pos) {
            return 0x00ffffff;
        }

        @Override
        protected Vector3f getCustomFogColor() {
            return fogColor;
        }

        @Override
        protected float getFogDistanceModifier() {
            return fogDistance.get();
        }
    }
}
