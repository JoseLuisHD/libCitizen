package org.citizen;

import cn.nukkit.plugin.PluginBase;
import lombok.Getter;

@Getter
public class CitizenLibrary {
    private final PluginBase plugin;

    public CitizenLibrary(PluginBase plugin) {
        this.plugin = plugin;
    }
}
