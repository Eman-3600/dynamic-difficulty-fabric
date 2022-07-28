package net.eman3600.dynamic_difficulty.cardinal_components;

import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import dev.onyxstudios.cca.api.v3.component.tick.ServerTickingComponent;
import net.eman3600.dynamic_difficulty.init.DifficultyComponents;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.Difficulty;

import java.util.List;

public class WorldDifficulty implements ServerTickingComponent, AutoSyncedComponent {


	private Scoreboard scoreboard;
	private MinecraftServer server;

	public WorldDifficulty(Scoreboard scoreboard, MinecraftServer server) {
		this.scoreboard = scoreboard;
		this.server = server;
	}

	public float getDifficulty() {
		List<ServerPlayerEntity> players = server.getPlayerManager().getPlayerList();

		if (players.size() > 1) {
			float combined_difficulty = 0;

			for (ServerPlayerEntity player: players) {
				combined_difficulty += DifficultyComponents.PLAYER_DIFFICULTY.get(player).getDifficulty();
			}

			float difficulty = combined_difficulty/players.size();
			return difficulty - (float)(difficulty % .01);
		} else if (players.size() == 1) {
			return DifficultyComponents.PLAYER_DIFFICULTY.get(players.get(0)).getDifficulty();
		} else {
			return PlayerDifficulty.MAX_DIFFICULTY/2;
		}

	}

	public Difficulty getIntendedDifficulty() {
		float difficulty = getDifficulty();

		return difficulty < 33.33f ? Difficulty.EASY : (difficulty > 66.67 ? Difficulty.HARD : Difficulty.NORMAL);
	}


	@Override
	public void serverTick() {
		if (server.getSaveProperties().getDifficulty() != getIntendedDifficulty()) {
			server.setDifficulty(getIntendedDifficulty(), true);
			server.setDifficultyLocked(true);
		}
	}

	@Override
	public void readFromNbt(NbtCompound tag) {
		server.setDifficulty(Difficulty.NORMAL, true);
	}

	@Override
	public void writeToNbt(NbtCompound tag) {

	}
}
