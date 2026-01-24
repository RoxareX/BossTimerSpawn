package dev.hytalemodding.ui;

import au.ellie.hyui.builders.HudBuilder;
import au.ellie.hyui.builders.HyUIHud;
import au.ellie.hyui.builders.LabelBuilder;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;

import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;

public class ToggleHTMLGUI {

    private static final Map<PlayerRef, HyUIHud> activePlayers = Collections.synchronizedMap(new WeakHashMap<>());
    private final PlayerRef playerRef;

    public ToggleHTMLGUI(PlayerRef playerRef) {
        this.playerRef = playerRef;
    }

    public void show(Ref<EntityStore> ref, int initialSeconds) {
        if (activePlayers.containsKey(playerRef)) {
            playerRef.sendMessage(Message.raw("[HUD] Toggle OFF."));
            activePlayers.remove(playerRef).remove();
            return;
        }
        playerRef.sendMessage(Message.raw("[HUD] Toggle ON."));


        final int[] seconds = {initialSeconds};
        HyUIHud hud = HudBuilder.hudForPlayer(playerRef)
                .fromHtml("<div " +
                        "style='" +
                        "text-align: right;" +
                        "anchor-width: 1000;" +
                        "horizontal-align: right;'><p id='label'>Hello World!</p></div>")
                .withRefreshRate(1000)
                    .onRefresh(h -> {
                        h.getById("label", LabelBuilder.class).ifPresent(label -> {
                            seconds[0]++;
                            label.withText("Seconds: " + seconds[0]);
                        });
                    })
                .show(ref.getStore());
        
        activePlayers.put(playerRef, hud);
    }

}
