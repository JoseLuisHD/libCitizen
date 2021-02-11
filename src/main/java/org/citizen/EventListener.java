package org.citizen;

import cn.nukkit.Player;
import cn.nukkit.entity.Entity;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.EventPriority;
import cn.nukkit.event.Listener;
import cn.nukkit.event.entity.EntityLevelChangeEvent;
import cn.nukkit.event.player.PlayerJoinEvent;
import cn.nukkit.event.player.PlayerQuitEvent;
import cn.nukkit.event.server.DataPacketReceiveEvent;
import cn.nukkit.inventory.transaction.data.TransactionData;
import cn.nukkit.inventory.transaction.data.UseItemOnEntityData;
import cn.nukkit.level.Level;
import cn.nukkit.network.protocol.DataPacket;
import cn.nukkit.network.protocol.InventoryTransactionPacket;
import lombok.RequiredArgsConstructor;
import org.citizen.entity.Citizen;

@RequiredArgsConstructor
public class EventListener implements Listener {
    private final CitizenLibrary library;

    @EventHandler(priority = EventPriority.NORMAL)
    public void onDamageEntities(DataPacketReceiveEvent event) {
        DataPacket packet = event.getPacket();
        Player player = event.getPlayer();

        if (packet instanceof InventoryTransactionPacket && ((InventoryTransactionPacket) packet).transactionType == InventoryTransactionPacket.TYPE_USE_ITEM_ON_ENTITY) {

            TransactionData data = ((InventoryTransactionPacket) packet).transactionData;

            if (data instanceof UseItemOnEntityData) {
                Citizen citizen = library.getFactory().getCitizen(((UseItemOnEntityData) data).entityRuntimeId);

                if (citizen == null) return;

                citizen.callInvoke(player);
            }
        }
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        library.getFactory().getCitizens().forEach((id, citizen) -> {
            if (citizen.getPosition().getLevel().getFolderName().equals(player.getLevel().getFolderName()))
                citizen.spawn(player);
        });
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        library.getFactory().getCitizens().forEach((id, citizen) -> citizen.despawn(player));
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onChangeLevel(EntityLevelChangeEvent event) {
        Entity entity = event.getEntity();

        if (!(entity instanceof Player))
            return;

        Player player = ((Player) entity);
        Level origin = event.getOrigin();
        Level target = event.getTarget();

        library.getFactory().getCitizens().forEach((id, citizen) -> {
            String citizenLevelName = citizen.getPosition().getLevel().getFolderName();
            if (citizenLevelName.equals(origin.getFolderName()))
                citizen.despawn(player);

            if (citizenLevelName.equals(target.getFolderName()))
                citizen.spawn(player);
        });
    }
}
