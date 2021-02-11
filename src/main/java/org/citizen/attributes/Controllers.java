package org.citizen.attributes;

import cn.nukkit.event.EventHandler;
import cn.nukkit.event.EventPriority;
import cn.nukkit.event.Listener;
import cn.nukkit.event.entity.EntityLevelChangeEvent;
import cn.nukkit.event.player.PlayerJoinEvent;
import cn.nukkit.event.player.PlayerQuitEvent;
import cn.nukkit.event.server.DataPacketReceiveEvent;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.citizen.CitizenLibrary;

@RequiredArgsConstructor
@Getter
public abstract class Controllers implements Listener {
    private final CitizenLibrary library;

    @EventHandler(priority = EventPriority.NORMAL)
    public abstract void handle(DataPacketReceiveEvent event);

    @EventHandler(priority = EventPriority.NORMAL)
    public abstract void handle(PlayerJoinEvent event);

    @EventHandler(priority = EventPriority.NORMAL)
    public abstract void handle(PlayerQuitEvent event);

    @EventHandler(priority = EventPriority.NORMAL)
    public abstract void handle(EntityLevelChangeEvent event);
}
