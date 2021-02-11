package org.citizen.attributes;

import cn.nukkit.Player;
import cn.nukkit.Server;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.citizen.entity.Citizen;

@RequiredArgsConstructor
@Getter
public abstract class InvokeAttribute {
    private final Citizen citizen;

    public abstract void invoke(@NonNull Player player);

    public Server getServer() {
        return Server.getInstance();
    }
}
