package org.citizen.controller;

import cn.nukkit.Player;
import cn.nukkit.entity.Entity;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.EventPriority;
import cn.nukkit.event.entity.EntityLevelChangeEvent;
import cn.nukkit.event.player.PlayerJoinEvent;
import cn.nukkit.event.player.PlayerQuitEvent;
import cn.nukkit.event.server.DataPacketReceiveEvent;
import cn.nukkit.inventory.transaction.data.TransactionData;
import cn.nukkit.inventory.transaction.data.UseItemOnEntityData;
import cn.nukkit.level.Level;
import cn.nukkit.network.protocol.DataPacket;
import cn.nukkit.network.protocol.InventoryTransactionPacket;
import org.citizen.CitizenLibrary;
import org.citizen.entity.Citizen;

public class DefaultController extends Controllers {
    public DefaultController(CitizenLibrary library) {
        super(library);
    }

    @Override
    @EventHandler(priority = EventPriority.NORMAL)
    public void handle(DataPacketReceiveEvent event) {
        DataPacket packet = event.getPacket();
        Player player = event.getPlayer();

        if (packet instanceof InventoryTransactionPacket && ((InventoryTransactionPacket) packet).transactionType == InventoryTransactionPacket.TYPE_USE_ITEM_ON_ENTITY) {

            TransactionData data = ((InventoryTransactionPacket) packet).transactionData;

            if (data instanceof UseItemOnEntityData) {
                Citizen citizen = getLibrary().getFactory().getCitizen(((UseItemOnEntityData) data).entityRuntimeId);

                if (citizen == null) return;

                citizen.callInvoke(player);
            }
        }
    }

    @Override
    @EventHandler(priority = EventPriority.NORMAL)
    public void handle(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        getLibrary().getFactory().getCitizens().forEach((id, citizen) -> {
            if (citizen.getPosition().getLevel().getFolderName().equals(player.getLevel().getFolderName()))
                citizen.spawn(player);
        });
    }

    @Override
    @EventHandler(priority = EventPriority.NORMAL)
    public void handle(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        getLibrary().getFactory().getCitizens().forEach((id, citizen) -> citizen.despawn(player));
    }

    @Override
    @EventHandler(priority = EventPriority.NORMAL)
    public void handle(EntityLevelChangeEvent event) {
        Entity entity = event.getEntity();

        if (!(entity instanceof Player))
            return;

        Player player = ((Player) entity);
        Level origin = event.getOrigin();
        Level target = event.getTarget();

        getLibrary().getFactory().getCitizens().forEach((id, citizen) -> {
            String citizenLevelName = citizen.getPosition().getLevel().getFolderName();
            if (citizenLevelName.equals(origin.getFolderName()))
                citizen.despawn(player);

            if (citizenLevelName.equals(target.getFolderName()))
                citizen.spawn(player);
        });
    }
}
