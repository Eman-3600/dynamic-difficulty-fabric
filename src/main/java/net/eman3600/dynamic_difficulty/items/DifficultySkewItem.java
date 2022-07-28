package net.eman3600.dynamic_difficulty.items;

import net.eman3600.dynamic_difficulty.cardinal_components.PlayerDifficulty;
import net.eman3600.dynamic_difficulty.init.DifficultyComponents;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class DifficultySkewItem extends Item {
	private final int MAX_USE_TIME = 20;
	private int skew;

	public DifficultySkewItem(Settings settings, int skewAmount) {
		super(settings);
		skew = skewAmount;
	}

	@Override
	public int getMaxUseTime(ItemStack stack) {
		return MAX_USE_TIME;
	}

	@Override
	public UseAction getUseAction(ItemStack stack) {
		return UseAction.BOW;
	}

	@Override
	public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
		if (user instanceof PlayerEntity) {
			PlayerDifficulty difficulty = DifficultyComponents.PLAYER_DIFFICULTY.get(user);
			difficulty.add(skew);

			if (!((PlayerEntity)user).isCreative())
				stack.decrement(1);
		}

		return stack;
	}

	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		return ItemUsage.consumeHeldItem(world, user, hand);
	}
}
