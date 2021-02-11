package org.citizen;

import cn.nukkit.plugin.PluginBase;
import lombok.Getter;
import org.citizen.attributes.Controllers;

@Getter
public class CitizenLibrary {
    private final PluginBase plugin;
    private final Factory factory;

    public CitizenLibrary(PluginBase plugin) {
        this.plugin = plugin;
        this.factory = new Factory();
        plugin.getServer().getPluginManager().registerEvents(new EventListener(this), plugin);
    }

    public CitizenLibrary(PluginBase plugin, Controllers customControllers) {
        this.plugin = plugin;
        this.factory = new Factory();

        plugin.getServer().getPluginManager().registerEvents(customControllers, plugin);
    }
}
