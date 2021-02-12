package org.citizen.entity;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.entity.Entity;
import cn.nukkit.entity.data.ByteEntityData;
import cn.nukkit.entity.data.EntityMetadata;
import cn.nukkit.level.Position;
import cn.nukkit.network.protocol.AddEntityPacket;
import cn.nukkit.network.protocol.RemoveEntityPacket;
import cn.nukkit.network.protocol.SetEntityDataPacket;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CitizenTag {
    private final Citizen root;
    private String nameTag;
    private final long entityId;
    private final UUID uuid;
    private Position position;

    public CitizenTag(Citizen citizen) {
        entityId = Entity.entityCount++;
        uuid = UUID.randomUUID();
        root = citizen;
    }

    public void sendNameTag(@NonNull Player player) {
        SetEntityDataPacket packet = new SetEntityDataPacket();
        packet.eid = entityId;
        packet.metadata = new EntityMetadata().putString(Entity.DATA_NAMETAG, nameTag);

        player.dataPacket(packet);
    }

    public void sendNameTag() {
        Server.getInstance().getOnlinePlayers().forEach((uuid, player) -> {
            if (root.getViwers().contains(player.getName()))
                sendNameTag(player);
        });
    }

    public CitizenTag change(String newTag) {
        nameTag = newTag;

        return this;
    }


    public void spawn(@NonNull Player player) {
        AddEntityPacket packet = new AddEntityPacket();
        packet.type = 33;
        packet.entityRuntimeId = getEntityId();
        packet.entityUniqueId = getEntityId();
        packet.yaw = 0;
        packet.headYaw = 0;
        packet.pitch = 0;
        packet.x = (float) getPosition().x;
        packet.y = (float) getPosition().y;
        packet.z = (float) getPosition().z;
        packet.speedX = (float) 0;
        packet.speedY = (float) 0;
        packet.speedZ = (float) 0;

        EntityMetadata metadata = new EntityMetadata();
        metadata.put(new ByteEntityData(Entity.DATA_ALWAYS_SHOW_NAMETAG, 1));
        metadata.putString(Entity.DATA_NAMETAG, getNameTag());
        metadata.putLong(Entity.DATA_LEAD_HOLDER_EID, -1);
        metadata.putFloat(Entity.DATA_BOUNDING_BOX_WIDTH, 0);
        metadata.putFloat(Entity.DATA_SCALE, 0.004f);

        packet.metadata = metadata;

        player.dataPacket(packet);
    }

    public void despawn(@NonNull Player player) {
        RemoveEntityPacket packet = new RemoveEntityPacket();
        packet.eid = entityId;

        player.dataPacket(packet);
    }
}
