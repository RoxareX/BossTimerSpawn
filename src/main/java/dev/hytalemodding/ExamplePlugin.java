package dev.hytalemodding;

import au.ellie.hyui.builders.*;
import au.ellie.hyui.elements.LayoutModeSupported;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.server.core.event.events.player.PlayerReadyEvent;
import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import dev.hytalemodding.commands.ExampleCommand;
import dev.hytalemodding.commands.OpenGUI;
import dev.hytalemodding.commands.ToggleGUI;
import dev.hytalemodding.events.OpenGuiListener;
import dev.hytalemodding.ui.ToggleHTMLGUI;

import javax.annotation.Nonnull;

public class ExamplePlugin extends JavaPlugin {

    public ExamplePlugin(@Nonnull JavaPluginInit init) {
        super(init);
    }

//    public static int seconds = 0;

    @Override
    protected void setup() {
        this.getEventRegistry().registerGlobal(PlayerReadyEvent.class, OpenGuiListener::openGui);

        this.getCommandRegistry().registerCommand(new ExampleCommand("example", "Example command"));
        this.getCommandRegistry().registerCommand(new OpenGUI("opengui", "Opens the GUI"));
        this.getCommandRegistry().registerCommand(new ToggleGUI("togglecounter", "Toggles the GUI"));

        this.getEventRegistry().registerGlobal(PlayerReadyEvent.class, event -> {
           Ref<EntityStore> ref = event.getPlayerRef();
           PlayerRef playerRef = ref.getStore().getComponent(ref, PlayerRef.getComponentType());

            ToggleHTMLGUI toggleHTMLGUI = new ToggleHTMLGUI(playerRef);
            toggleHTMLGUI.show(ref, 0);

//            HudBuilder.hudForPlayer(playerRef)
//                    .addElement(
//                            GroupBuilder.group()
//                                    .withBackground(new HyUIPatchStyle().setColor("#FF0000(0)"))
//                                    .withLayoutMode(LayoutModeSupported.LayoutMode.MiddleCenter)
//                                    .withAnchor(new HyUIAnchor().setWidth(150).setHeight(50))
//                                    .addChild(
//                                            LabelBuilder.label()
//                                                    .withText("30mins")
//                                                    .withId("label")
//                                    )
//                    )
//                    .withRefreshRate(1000)
//                    .onRefresh(hud -> {
//                        hud.getById("label", LabelBuilder.class).ifPresent(label -> {
//                            seconds++;
//                            label.withText("Seconds: " + seconds);
//                        });
//                    })
//                    .show(ref.getStore());


        });


    }
}