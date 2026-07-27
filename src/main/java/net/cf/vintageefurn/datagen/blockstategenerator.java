package net.cf.vintageefurn.datagen;

import net.cf.vintageefurn.VintageFurn;
import net.cf.vintageefurn.blocks.railing.RailingBlock;
import net.cf.vintageefurn.registry.BeamsBlocks;
import net.cf.vintageefurn.registry.BeamsItems;
import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class blockstategenerator extends BlockStateProvider {
    public blockstategenerator(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, VintageFurn.MOD_ID, exFileHelper);
    }
    public static final BooleanProperty NORTH = BlockStateProperties.NORTH;
    public static final BooleanProperty SOUTH = BlockStateProperties.SOUTH;
    public static final BooleanProperty EAST  = BlockStateProperties.EAST;
    public static final BooleanProperty WEST  = BlockStateProperties.WEST;


    @Override
    protected void registerStatesAndModels() {

        for (String stone : BeamsBlocks.STONE_TYPES) {

            ResourceLocation stoneTexture = BeamsItems.getStoneTexture(stone);

            for (String wood : BeamsBlocks.WOOD_TYPES) {

                ResourceLocation woodTexture = mcLoc("block/" + wood + "_planks");

                ModelFile glass = railingglassModel(
                        stone + "_" + wood + "_glass_railing",
                        stoneTexture,
                        woodTexture
                );

                ModelFile arched = railingarchedModel(
                        stone + "_" + wood + "_arched_railing",
                        stoneTexture,
                        woodTexture
                );

                ModelFile simple = railingsimpleModel(
                        stone + "_" + wood + "_simple_railing",
                        stoneTexture,
                        woodTexture
                );

                railingBlockState(
                        BeamsBlocks.getStoneGlassRailing(stone, wood),
                        glass
                );

                railingBlockState(
                        BeamsBlocks.getStoneArchedRailing(stone, wood),
                        arched
                );

                railingBlockState(
                        BeamsBlocks.getStoneSimpleRailing(stone, wood),
                        simple
                );
            }

    }


    }


    private void blockWithItem(RegistryObject<Block> blockRegistryObject) {
        simpleBlockWithItem(blockRegistryObject.get(), cubeAll(blockRegistryObject.get()));
    }
    private ModelFile railingglassModel(String name,
                              ResourceLocation base,
                              ResourceLocation rail) {

        return models().withExistingParent(name,
                        modLoc("block/railing/template_glass_railing"))
                .texture("base", base)
                .texture("rail", rail)
                .texture("holder", rail)
                .texture("glass", mcLoc("block/glass"))
                .texture("glass_edge", mcLoc("block/glass_pane_top"));
    }
    private ModelFile railingarchedModel(String name,
                                   ResourceLocation base,
                                   ResourceLocation rail) {

        return models().withExistingParent(name,
                        modLoc("block/railing/template_arched_railing"))
                .texture("base", base)
                .texture("rail", rail);
    }
    private ModelFile railingsimpleModel(String name,
                                    ResourceLocation base,
                                    ResourceLocation rail) {

        return models().withExistingParent(name,
                        modLoc("block/railing/template_simple_railing"))
                .texture("base", base)
                .texture("rail", rail);
    }
    private void railingBlockState(RegistryObject<? extends Block> block, ModelFile model){
        var builder = getMultipartBuilder(block.get());

                builder.part()
                        .modelFile(model)
                .rotationY(90)
                .addModel()
                .condition(NORTH, true);

                builder.part()
                        .modelFile(model)
                .rotationY(270) // -90
                .addModel()
                .condition(SOUTH, true);

                builder.part()
                        .modelFile(model)
                .rotationY(180)
                .addModel()
                .condition(EAST, true);

                builder.part()
                        .modelFile(model)
                .addModel()
                .condition(WEST, true);
    }

}
