package net.eman3600.dynamic_difficulty.items;

import net.eman3600.dynamic_difficulty.cardinal_components.PlayerDifficulty;
import net.eman3600.dynamic_difficulty.init.DifficultyComponents;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class DifficultyMeterItem extends Item {
	public DifficultyMeterItem(Settings settings) {
		super(settings);
	}

	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		if (world instanceof ServerWorld)
			user.sendMessage(Text.translatable("item.dynamic_difficulty.meter.display", DifficultyComponents.PLAYER_DIFFICULTY.get(user).getDifficulty(), DifficultyComponents.WORLD_DIFFICULTY.get(world.getScoreboard()).getDifficulty()),true);

		return TypedActionResult.success(user.getStackInHand(hand));
	}
}
