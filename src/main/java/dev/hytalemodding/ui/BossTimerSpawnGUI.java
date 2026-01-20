package dev.hytalemodding.ui;

import

import com.hypixel.hytale.server.core.ui.builder.UICommandBuilder;
import com.hypixel.hytale.server.core.universe.PlayerRef;

import javax.annotation.Nonnull;

public class BossTimerSpawnGUI {

    public BossTimerSpawnGUI(@Nonnull PlayerRef  ) { super(PlayerRef); }

    @Override
    protected void build(@Nonnull UICommandBuilder uiCommandBuilder) {
        uiCommandBuilder.append("Hud/BossTimerSpawnGUI.ui");
    }
}
