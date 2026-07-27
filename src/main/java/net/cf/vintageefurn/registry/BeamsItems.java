package net.cf.vintageefurn.registry;

import net.cf.vintageefurn.VintageFurn;
import net.cf.vintageefurn.items.BeamItem;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.LinkedHashMap;
import java.util.Map;

public final class BeamsItems {

    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, VintageFurn.MOD_ID);

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

    private static final Map<String, RegistryObject<BeamItem>> BEAM_ITEMS = new LinkedHashMap<>();

    static {
        for (String wood : WOOD_TYPES) {
            RegistryObject<BeamItem> item = ITEMS.register(
                    "beam_" + wood,
                    () -> new BeamItem(new Item.Properties().stacksTo(64), wood)
            );

            BEAM_ITEMS.put(wood, item);
        }
    }

    public static Item getBeamItem(String woodType) {
        RegistryObject<BeamItem> item = BEAM_ITEMS.get(woodType);

        if (item != null) {
            return item.get();
        }

        Item modded = BeamsWoodTypeRegistry.getItem(woodType);
        if (modded != null) {
            return modded;
        }

        return BEAM_ITEMS.get("oak").get();
    }

    public static Item getOakBeamItem() {
        return BEAM_ITEMS.get("oak").get();
    }
    public static Map<String, RegistryObject<BeamItem>> getBeamItems() {
        return BEAM_ITEMS;
    }
    public static Item getPlankBlock(String woodType) {
        Item item = ForgeRegistries.ITEMS.getValue(
                new ResourceLocation("minecraft", woodType + "_planks"));

        if (item == null || item == Items.AIR) {
            throw new IllegalArgumentException("Unknown wood type: " + woodType);
        }

        return item;
    }
    public static ResourceLocation getStoneTexture(String stoneType) {
        return switch (stoneType) {
            case "stone", "mud_bricks", "deepslate_tiles" ->
                    new ResourceLocation("minecraft", "block/" + stoneType);

            case "limestone", "scoria", "scorchia" ->
                    new ResourceLocation(
                            VintageFurn.MOD_ID,
                            "block/create/stone_types/" + stoneType
                    );

            default -> {
                String folder = stoneType.substring(stoneType.indexOf('_') + 1);

                yield new ResourceLocation(
                        VintageFurn.MOD_ID,
                        "block/create/stone_types/" + folder + "/" + stoneType
                );
            }
        };
    }
    public static Item getStoneBlock(String stoneType) {
        return switch (stoneType) {
            // Vanilla
            case "stone" -> Items.STONE;
            case "mud_bricks" -> Items.MUD_BRICKS;
            case "deepslate_tiles" -> Items.DEEPSLATE_TILES;

            // Create
            case "andesite_cut" ->
                    ForgeRegistries.ITEMS.getValue(new ResourceLocation("create", "cut_andesite"));
            case "granite_cut" ->
                    ForgeRegistries.ITEMS.getValue(new ResourceLocation("create", "cut_granite"));
            case "diorite_cut" ->
                    ForgeRegistries.ITEMS.getValue(new ResourceLocation("create", "cut_diorite"));
            case "limestone_cut" ->
                    ForgeRegistries.ITEMS.getValue(new ResourceLocation("create", "cut_limestone"));
            case "asurine_cut" ->
                    ForgeRegistries.ITEMS.getValue(new ResourceLocation("create", "cut_asurine"));
            case "ochrum_cut" ->
                    ForgeRegistries.ITEMS.getValue(new ResourceLocation("create", "cut_ochrum"));
            case "crimsite_cut" ->
                    ForgeRegistries.ITEMS.getValue(new ResourceLocation("create", "cut_crimsite"));
            case "veridium_cut" ->
                    ForgeRegistries.ITEMS.getValue(new ResourceLocation("create", "cut_veridium"));

            default -> throw new IllegalArgumentException("Unknown stone type: " + stoneType);
        };
    }


    private BeamsItems() {
    }
}
