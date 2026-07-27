package net.cf.vintageefurn.registry;

import net.cf.vintageefurn.VintageFurn;
import net.cf.vintageefurn.blocks.beam.BeamAnchorBlock;
import net.cf.vintageefurn.blocks.railing.RailingBlock;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Supplier;

public final class BeamsBlocks {
    public static final String[] STONE_TYPES = {
            "andesite_cut",
            "granite_cut",
            "diorite_cut",
            "limestone_cut",
            "asurine_cut",
            "ochrum_cut",
            "crimsite_cut",
            "veridium_cut",
            "stone",
            "mud_bricks",
            "deepslate_tiles"
    };
    public static final String[] WOOD_TYPES = {
            "oak",
            "spruce",
            "birch",
            "jungle",
            "acacia",
            "dark_oak",
            "mangrove",
            "cherry",
            "crimson",
            "warped",
            "bamboo"
    };
    private static final Map<String, RegistryObject<Block>> STONE_GLASS_RAILING = new LinkedHashMap<>();
    private static final Map<String, RegistryObject<Block>> STONE_ARCHED_RAILING = new LinkedHashMap<>();
    private static final Map<String, RegistryObject<Block>> STONE_SIMPLE_RAILING = new LinkedHashMap<>();



    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, VintageFurn.MOD_ID);

    public static final RegistryObject<BeamAnchorBlock> BEAM_ANCHOR =
            BLOCKS.register(
                    "beam_anchor",
                    () -> new BeamAnchorBlock(beamAnchorProperties())
            );


    static {
        for (String stone : STONE_TYPES) {
            for (String wood : WOOD_TYPES) {

                String key = stone + "_" + wood;

                STONE_GLASS_RAILING.put(key,
                        registerBlock(
                                key + "_glass_railing",
                                () -> new RailingBlock(
                                        BlockBehaviour.Properties.copy(Blocks.STONE)
                                                .strength(2.0F)
                                                .noOcclusion()
                                )));

                STONE_ARCHED_RAILING.put(key,
                        registerBlock(
                                key + "_arched_railing",
                                () -> new RailingBlock(
                                        BlockBehaviour.Properties.copy(Blocks.STONE)
                                                .strength(2.0F)
                                                .noOcclusion()
                                )));

                STONE_SIMPLE_RAILING.put(key,
                        registerBlock(
                                key + "_simple_railing",
                                () -> new RailingBlock(
                                        BlockBehaviour.Properties.copy(Blocks.STONE)
                                                .strength(2.0F)
                                                .noOcclusion()
                                )));
            }
        }

    }

    private static BlockBehaviour.Properties beamAnchorProperties() {
        return BlockBehaviour.Properties.of()
                .mapColor(MapColor.WOOD)
                .sound(SoundType.WOOD)
                .strength(1.0F, 2.0F)
                .noOcclusion()
                .noCollission()
                .isValidSpawn((state, level, pos, entityType) -> false)
                .isRedstoneConductor((state, level, pos) -> false)
                .isSuffocating((state, level, pos) -> false)
                .isViewBlocking((state, level, pos) -> false)
                .pushReaction(PushReaction.DESTROY);
    }
    public static RegistryObject<Block> getStoneGlassRailing(String stone, String wood) {
        return STONE_GLASS_RAILING.get(stone + "_" + wood);
    }

    public static RegistryObject<Block> getStoneArchedRailing(String stone, String wood) {
        return STONE_ARCHED_RAILING.get(stone + "_" + wood);
    }

    public static RegistryObject<Block> getStoneSimpleRailing(String stone, String wood) {
        return STONE_SIMPLE_RAILING.get(stone + "_" + wood);
    }



    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> void registerBlockItem(String name, RegistryObject<T> block) {
        BeamsItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }

    private BeamsBlocks() {
    }
}
