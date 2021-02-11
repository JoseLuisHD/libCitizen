package org.citizen.entity;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.entity.Entity;
import cn.nukkit.entity.data.ByteEntityData;
import cn.nukkit.entity.data.EntityMetadata;
import cn.nukkit.entity.data.Skin;
import cn.nukkit.item.Item;
import cn.nukkit.level.Position;
import cn.nukkit.network.protocol.AddPlayerPacket;
import cn.nukkit.network.protocol.PlayerSkinPacket;
import cn.nukkit.network.protocol.RemoveEntityPacket;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.citizen.attributes.InvokeAttribute;

import java.util.UUID;

@Getter
@Setter
public class Citizen {
    private final long entityId;
    private final UUID uuid;
    private Position position;
    private Skin skin;
    private float yaw;
    private float pitch;
    private InvokeAttribute invokeAttribute;

    public Citizen() {
        entityId = Entity.entityCount++;
        uuid = UUID.randomUUID();
    }

    public void callInvoke(Player player) {
        if (invokeAttribute != null)
            invokeAttribute.invoke(player);
    }

    public void spawn(@NonNull Player player) {
        AddPlayerPacket packet = new AddPlayerPacket();
        packet.username = "";
        packet.entityRuntimeId = getEntityId();
        packet.entityUniqueId = getEntityId();
        packet.uuid = getUuid();
        packet.pitch = getPitch();
        packet.yaw = getYaw();
        packet.x = (float) getPosition().getX();
        packet.y = (float) getPosition().getY();
        packet.z = (float) getPosition().getZ();
        packet.speedX = (float) 0;
        packet.speedY = (float) 0;
        packet.speedZ = (float) 0;
        packet.item = Item.get(Item.AIR);
        packet.putSkin(getSkin());

        EntityMetadata metadata = new EntityMetadata();
        metadata.put(new ByteEntityData(Entity.DATA_ALWAYS_SHOW_NAMETAG, 1));
        metadata.putString(Entity.DATA_NAMETAG, "");
        metadata.putLong(Entity.DATA_LEAD_HOLDER_EID, -1);

        packet.metadata = metadata;

        player.dataPacket(packet);

        PlayerSkinPacket skinPacket = new PlayerSkinPacket();
        skinPacket.skin = skin;
        skinPacket.newSkinName = getSkin().getSkinId();
        skinPacket.oldSkinName = getSkin().getSkinId();
        skinPacket.uuid = getUuid();

        Server.broadcastPacket(Server.getInstance().getOnlinePlayers().values(), skinPacket);
    }

    public void despawn(Player player) {
        RemoveEntityPacket packet = new RemoveEntityPacket();
        packet.eid = entityId;

        player.dataPacket(packet);
    }
}
