package net.eman3600.dynamic_difficulty.init;

import net.eman3600.dynamic_difficulty.DynamicDifficulty;
import net.eman3600.dynamic_difficulty.items.DifficultyMeterItem;
import net.eman3600.dynamic_difficulty.items.DifficultySkewItem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class DifficultyItems {
	private static final Item METER = registerItem("meter", new DifficultyMeterItem(new FabricItemSettings().group(ItemGroup.TOOLS).maxCount(1)));

	private static final Item EASY_SKEW_ITEM = registerItem("easy_skew", new DifficultySkewItem(new FabricItemSettings().group(ItemGroup.TOOLS), -30));
	private static final Item HARD_SKEW_ITEM = registerItem("hard_skew", new DifficultySkewItem(new FabricItemSettings().group(ItemGroup.TOOLS), 30));



	private static Item registerItem(String name, Item item) {
		return Registry.register(Registry.ITEM, new Identifier(DynamicDifficulty.MODID, name), item);
	}

	public static void registerItems() {
		System.out.println("Registering items for " + DynamicDifficulty.MODID);
	}
}
