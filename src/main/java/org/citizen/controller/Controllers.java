package org.citizen.controller;

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

    public abstract void handle(DataPacketReceiveEvent event);

    public abstract void handle(PlayerJoinEvent event);

    public abstract void handle(PlayerQuitEvent event);

    public abstract void handle(EntityLevelChangeEvent event);
}
