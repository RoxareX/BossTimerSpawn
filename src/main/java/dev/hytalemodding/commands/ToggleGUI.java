package dev.hytalemodding.commands;

import au.ellie.hyui.builders.HudBuilder;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.command.system.AbstractCommand;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import dev.hytalemodding.ui.BossTimerSpawnGUI;
import dev.hytalemodding.ui.ToggleHTMLGUI;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.concurrent.CompletableFuture;

public class ToggleGUI extends AbstractPlayerCommand {

    public ToggleGUI(String name, String description) {
        super(name, description);
    }

    @Override
    protected void execute(@Nonnull CommandContext commandContext, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
        ToggleHTMLGUI toggleHTMLGUI = new ToggleHTMLGUI(playerRef);
        toggleHTMLGUI.show(ref, 0);
//        Player player = commandContext.senderAs(Player.class);
//        player.getWorldMapTracker().tick(0);
//
//        CompletableFuture.runAsync(() -> {
//            if (player.getHudManager().getCustomHud() == null ) {
//                player.getHudManager().setCustomHud(playerRef, new BossTimerSpawnGUI(playerRef));
//                playerRef.sendMessage(Message.raw("HUD Shown"));
//            } else {
//                player.getHudManager().resetHud(playerRef);
//                playerRef.sendMessage(Message.raw("HUD Hidden"));
//            }
//        }, world);
    }
}
