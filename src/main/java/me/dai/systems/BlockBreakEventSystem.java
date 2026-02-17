package me.dai.systems;

import com.hypixel.hytale.component.ArchetypeChunk;
import com.hypixel.hytale.component.CommandBuffer;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.component.query.Query;
import com.hypixel.hytale.component.system.EntityEventSystem;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.event.events.ecs.BreakBlockEvent;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.hypixel.hytale.server.core.util.Config;
import me.dai.config.BlockBreakConfig;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

import java.util.Arrays;

public class BlockBreakEventSystem extends EntityEventSystem<EntityStore, BreakBlockEvent> {

    private final Config<BlockBreakConfig> config;

    public BlockBreakEventSystem(Config<BlockBreakConfig> config) {
        super(BreakBlockEvent.class);
        this.config = config;
    }

    @Override
    public void handle(int index,
                       @NonNullDecl ArchetypeChunk<EntityStore> archetypeChunk,
                       @NonNullDecl Store<EntityStore> store,
                       @NonNullDecl CommandBuffer<EntityStore> commandBuffer,
                       @NonNullDecl BreakBlockEvent event) {
        // Archetype = a unique combination of component TYPES
        // (e.g. Player + Health + Inventory)

        /* ArchetypeChunk = a chunk of ENTITIES that all share ONE archetype
         *
         * Archetype: Player + Health + Inventory
         *
         * Chunk rows:
         * index 0 = entity A (Player, Health, Inventory data)
         * index 1 = entity B (Player, Health, Inventory data)
         */

        // index = the row of the entity inside THIS archetype chunk

        // Get the entity reference for the entity at this chunk row
        Ref<EntityStore> entityStoreRef = archetypeChunk.getReferenceTo(index);
        Player player = store.getComponent(entityStoreRef, Player.getComponentType());

        if(player == null) return;

       BlockBreakConfig blockBreakConfig = config.get();

        String blockId = event.getBlockType().getId();
        boolean notHasBrokenBlockId = Arrays.stream(blockBreakConfig.getAllowedBlocks())
                .noneMatch(id -> id.equalsIgnoreCase(blockId));
        if (notHasBrokenBlockId) return;


        //send message to player you mined thing
        player.sendMessage(Message.raw("You mined: %s".formatted(event.getBlockType().getId())));
    }

    @NullableDecl
    @Override
    public Query<EntityStore> getQuery() {
        return PlayerRef.getComponentType();
    }
}
