package net.eman3600.dynamic_difficulty.cardinal_components;

import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import dev.onyxstudios.cca.api.v3.component.tick.ServerTickingComponent;
import net.eman3600.dynamic_difficulty.init.DifficultyComponents;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.MathHelper;

import javax.annotation.Nonnegative;

public class PlayerDifficulty implements ServerTickingComponent, AutoSyncedComponent {
	public static final int MAX_DIFFICULTY = 100;
	private static final int UPTICK_TIME = 24000;
	private int difficulty = 50;
	private int uptick = 0;


	private PlayerEntity player;

	public PlayerDifficulty(PlayerEntity player) {
		this.player = player;
	}

	public void add(int amount) {
		difficulty += amount;
		normalize();
	}

	public int getDifficulty() {
		return difficulty;
	}

	public void normalize() {
		difficulty = MathHelper.clamp(difficulty, 0, MAX_DIFFICULTY);
		DifficultyComponents.PLAYER_DIFFICULTY.sync(player);
	}


	@Override
	public void serverTick() {
		uptick++;
		if (uptick >= UPTICK_TIME) {
			uptick -= UPTICK_TIME;
			difficulty++;
		}
	}

	@Override
	public void readFromNbt(NbtCompound tag) {
		difficulty = tag.getInt("difficulty");
		uptick = tag.getInt("uptick");
	}

	@Override
	public void writeToNbt(NbtCompound tag) {
		tag.putInt("difficulty", difficulty);
		tag.putInt("uptick", uptick);
	}
}
