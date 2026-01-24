package dev.hytalemodding;

import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.math.vector.Vector3d;
import com.hypixel.hytale.math.vector.Vector3f;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.npc.INonPlayerCharacter;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.hypixel.hytale.server.npc.NPCPlugin;
import it.unimi.dsi.fastutil.Pair;

public class NPCSpawnManager {
    public void spawnBoss(Store<EntityStore> store, PlayerRef playerRef, String bossName) {
        Pair<Ref<EntityStore>, INonPlayerCharacter> result = NPCPlugin.get().spawnNPC(store, bossName, null, new Vector3d(257, 116, 311), new Vector3f(0, 0, 0));

        if (result != null) {
            Ref<EntityStore> npcRef = result.first();
            INonPlayerCharacter npc = result.second();

            playerRef.sendMessage(Message.raw("Successfully spawned NPC: " + bossName));

            // Proceed with customization...
//            setupNPCInventory(npcRef, store);
        }
    }
}
