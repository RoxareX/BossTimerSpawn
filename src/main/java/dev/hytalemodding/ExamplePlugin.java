package dev.hytalemodding;

import au.ellie.hyui.builders.*;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.server.core.event.events.player.PlayerReadyEvent;
import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.Universe;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import dev.hytalemodding.commands.ExampleCommand;
import dev.hytalemodding.ui.GlobalBossTimerHUD;

import javax.annotation.Nonnull;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

///  ************************
///
///     USED TO SPAWN THE BOSS WITH HEALTH AND SO ON
///
///  ************************
public class ExamplePlugin extends JavaPlugin {

    private final BossCountdownManager bossCountdownManager = new BossCountdownManager();
    private final EntitySpawnManager entitySpawnManager = new EntitySpawnManager();
    private final NPCSpawnManager npcSpawnManager = new NPCSpawnManager();
    private GlobalBossTimerHUD bossTimerHUD;

    public ExamplePlugin(@Nonnull JavaPluginInit init) {
        super(init);
    }

    @Override
    protected void setup() {
        this.bossTimerHUD = new GlobalBossTimerHUD(bossCountdownManager);


        // Start the global ticker
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(
                bossCountdownManager::tick, 1, 1, TimeUnit.SECONDS
        );

        this.getEventRegistry().registerGlobal(PlayerReadyEvent.class, event -> {
           Ref<EntityStore> ref = event.getPlayerRef();
           PlayerRef playerRef = ref.getStore().getComponent(ref, PlayerRef.getComponentType());

           bossTimerHUD.show(playerRef, ref);
        });

        this.getCommandRegistry().registerCommand(new ExampleCommand("example", "Example command", bossCountdownManager, npcSpawnManager));


    }
}