package dev.hytalemodding;

import com.hypixel.hytale.component.Holder;
import com.hypixel.hytale.math.vector.Vector3d;
import com.hypixel.hytale.math.vector.Vector3f;
import com.hypixel.hytale.server.core.entity.UUIDComponent;
import com.hypixel.hytale.server.core.entity.entities.ProjectileComponent;
import com.hypixel.hytale.server.core.entity.nameplate.Nameplate;
import com.hypixel.hytale.server.core.modules.entity.component.*;
import com.hypixel.hytale.server.core.modules.entity.tracker.NetworkId;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;

///  ************************
///
///     USED TO SPAWN THE BOSS COUNT DOWN IN
///     THE SAME POSITION WHERE THE BOSS WILL SPAWN
///
///  ************************
public class EntitySpawnManager {
    public void spawnBossCounterHologram(World world) {
        Holder<EntityStore> holder = EntityStore.REGISTRY.newHolder();
        world.execute(() -> {
            ProjectileComponent projectileComponent = new ProjectileComponent("Projectile");
            holder.putComponent(ProjectileComponent.getComponentType(), projectileComponent);
            holder.putComponent(TransformComponent.getComponentType(), new TransformComponent(new Vector3d(257, 128, 311), new Vector3f(0, 0, 0)));
            holder.ensureComponent(UUIDComponent.getComponentType());

            if (projectileComponent.getProjectile() == null) {
                projectileComponent.initialize();
                if (projectileComponent.getProjectile() == null) {
                    return;
                }
            }
            holder.addComponent(NetworkId.getComponentType(), new NetworkId(world.getEntityStore().getStore().getExternalData().takeNextNetworkId()));
            holder.addComponent(Nameplate.getComponentType(), new Nameplate("No Boss Spawning"));

            world.getEntityStore().getStore().addEntity(holder, com.hypixel.hytale.component.AddReason.SPAWN);
        });
    }

    public static void updateHologramName(World world, Holder<EntityStore> holder, String newName) {
        world.execute(() -> {
            holder.addComponent(Nameplate.getComponentType(), new Nameplate(newName));
        });
    }
}
