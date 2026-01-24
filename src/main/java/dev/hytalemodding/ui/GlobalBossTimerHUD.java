package dev.hytalemodding.ui;

import au.ellie.hyui.builders.HudBuilder;
import au.ellie.hyui.builders.HyUIHud;
import au.ellie.hyui.builders.LabelBuilder;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import dev.hytalemodding.BossCountdownManager;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class GlobalBossTimerHUD {
    private static final Map<PlayerRef, HyUIHud> activeHuds = new ConcurrentHashMap<>();
    private final BossCountdownManager manager;

    public GlobalBossTimerHUD(BossCountdownManager manager) {
        this.manager = manager;
    }

    public void show(PlayerRef playerRef, Ref<EntityStore> ref) {
        if (activeHuds.containsKey(playerRef)) return;

        HyUIHud hud = HudBuilder.hudForPlayer(playerRef)
                .fromHtml("<div id='container' style=" +
                        "'text-align: right; " +
                        "anchor-width: 1000; " +
                        "horizontal-align: right; " +
                        "flex-direction: column;'>" +
                        "<p id='timer-list' style='color: #FFFFFF; font-size: 20;'>No Bosses Active</p>" +
                        "</div>")
                .withRefreshRate(1000)
                .onRefresh(h -> {
                    try {
                        List<BossCountdownManager.BossCountdown> countdowns = manager.getCountdowns();
                        h.getById("timer-list", LabelBuilder.class).ifPresent(label -> {
                            String text;
                            if (countdowns.isEmpty()) {
                                text = "No Active Countdowns";
                            } else {
                                text = countdowns.stream()
                                        .map(c -> c.getBossName() + ": " + c.getFormattedTime())
                                        .collect(Collectors.joining(" | "));
                            }
                            label.withText(text);
                        });
                    } catch (Exception e) {
                        System.err.println("[GlobalBossTimerHUD] Error in onRefresh()");
                        e.printStackTrace();
                    }
                })
                .show(ref.getStore());

        activeHuds.put(playerRef, hud);
    }

    public static void remove(PlayerRef playerRef) {
        HyUIHud hud = activeHuds.remove(playerRef);
        if (hud != null) {
            hud.remove();
        }
    }
}
