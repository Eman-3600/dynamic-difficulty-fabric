package net.eman3600.dynamic_difficulty.init;

import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentInitializer;
import dev.onyxstudios.cca.api.v3.entity.PlayerCopyCallback;
import dev.onyxstudios.cca.api.v3.entity.RespawnCopyStrategy;
import dev.onyxstudios.cca.api.v3.scoreboard.ScoreboardComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.scoreboard.ScoreboardComponentInitializer;
import net.eman3600.dynamic_difficulty.DynamicDifficulty;
import net.eman3600.dynamic_difficulty.cardinal_components.PlayerDifficulty;
import net.eman3600.dynamic_difficulty.cardinal_components.WorldDifficulty;
import net.minecraft.util.Identifier;

public class DifficultyComponents implements EntityComponentInitializer, ScoreboardComponentInitializer {
	public static final ComponentKey<PlayerDifficulty> PLAYER_DIFFICULTY = ComponentRegistry.getOrCreate(new Identifier(DynamicDifficulty.MODID, "player_difficulty"), PlayerDifficulty.class);
	public static final ComponentKey<WorldDifficulty> WORLD_DIFFICULTY = ComponentRegistry.getOrCreate(new Identifier(DynamicDifficulty.MODID, "world_difficulty"), WorldDifficulty.class);


	@Override
	public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
		registry.registerForPlayers(PLAYER_DIFFICULTY, PlayerDifficulty::new, RespawnCopyStrategy.ALWAYS_COPY);

		PlayerCopyCallback.EVENT.register(((original, clone, lossless) -> {
			if (!lossless) {
				original.getComponent(PLAYER_DIFFICULTY).add(-15);
				clone.getComponent(PLAYER_DIFFICULTY).add(-15);
			}
		}));
	}

	@Override
	public void registerScoreboardComponentFactories(ScoreboardComponentFactoryRegistry registry) {
		registry.registerScoreboardComponent(WORLD_DIFFICULTY, WorldDifficulty::new);
	}
}
