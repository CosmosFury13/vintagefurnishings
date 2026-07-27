package net.cf.vintageefurn.registry;

import net.mehvahdjukaar.moonlight.api.set.wood.WoodType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public final class BeamsWoodTypeRegistry {

    public record Entry(String key, Item beamItem, ResourceLocation planksTexture, boolean resolved) {}
    private static final Map<String, Item> ITEMS = new HashMap<>();
    private static final Map<String, ResourceLocation> TEXTURES = new HashMap<>();

    private static final Map<String, Entry> ENTRIES = new LinkedHashMap<>();




    private static final Map<String, WoodType> WOOD_TYPES = new HashMap<>();

    public static void register(String id, Item beam, ResourceLocation texture, WoodType woodType) {
        ITEMS.put(id, beam);
        TEXTURES.put(id, texture);
        WOOD_TYPES.put(id, woodType);
    }

    public static WoodType getWoodType(String id) {
        return WOOD_TYPES.get(id);
    }

    public static void registerResolved(String key, Item beamItem, ResourceLocation planksTexture) {
        ENTRIES.put(key, new Entry(key, beamItem, planksTexture, true));
    }

    public static Item getItem(String key) {
        Entry e = ENTRIES.get(key);
        return e == null ? null : e.beamItem();
    }

    public static ResourceLocation getTexture(String key) {
        Entry e = ENTRIES.get(key);
        return e == null ? null : e.planksTexture();
    }

    private BeamsWoodTypeRegistry() {
    }
}
