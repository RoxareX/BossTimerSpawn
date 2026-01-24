package dev.hytalemodding.ui;

import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.entity.entities.player.hud.CustomUIHud;
import com.hypixel.hytale.server.core.ui.builder.UICommandBuilder;
import com.hypixel.hytale.server.core.universe.PlayerRef;

import javax.annotation.Nonnull;

public class BossTimerSpawnGUI extends CustomUIHud{

    public BossTimerSpawnGUI(@Nonnull PlayerRef playerRef ) { super(playerRef); }

    @Override
    protected void build(@Nonnull UICommandBuilder uiCommandBuilder) {
        uiCommandBuilder.append("BossTimerSpawnGUI.ui");
        uiCommandBuilder.set("#MyLabel.TextSpans", Message.raw("NewText :D"));
    }
}
