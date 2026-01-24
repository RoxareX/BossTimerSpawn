package dev.hytalemodding.commands;

import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
import com.hypixel.hytale.server.core.modules.entity.EntityModule;
import com.hypixel.hytale.server.core.modules.entity.component.*;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;

import dev.hytalemodding.BossCountdownManager;
import dev.hytalemodding.NPCSpawnManager;

import java.util.concurrent.CompletableFuture;
import javax.annotation.Nonnull;

public class ExampleCommand extends AbstractPlayerCommand {

    private final BossCountdownManager manager;
    private final NPCSpawnManager npcSpawnManager;

    public ExampleCommand(@Nonnull String name, @Nonnull String description, BossCountdownManager manager, NPCSpawnManager npcSpawnManager) {
        super(name, description);
        this.manager = manager;
        this.npcSpawnManager = npcSpawnManager;
    }

    @Override
    protected void execute(@Nonnull CommandContext commandContext, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
        String bossName = "Zombie_Boss";
        int seconds = 10;

        manager.addCountdown(bossName, seconds, countdown -> {
            CompletableFuture.runAsync(() -> {
                playerRef.sendMessage(Message.raw("BOSS SPAWNED: " + bossName));

                TransformComponent transform = store.getComponent(playerRef.getReference(), EntityModule.get().getTransformComponentType());
                if (transform != null) {
                    npcSpawnManager.spawnBoss(store, playerRef, countdown.getBossName());
                } else {
                    playerRef.sendMessage(Message.raw("Failed to get transform component for spawning."));
                }
            }, world);
        });

        commandContext.sendMessage(Message.raw("Started countdown for " + bossName + " (" + seconds + "s)"));
    }
}
