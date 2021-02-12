package org.citizen;

import cn.nukkit.plugin.PluginBase;
import lombok.Getter;
import org.citizen.controller.Controllers;
import org.citizen.controller.DefaultController;

@Getter
public class CitizenLibrary {
    private final PluginBase plugin;
    private final Factory factory;

    public CitizenLibrary(PluginBase plugin) {
        this.plugin = plugin;
        this.factory = new Factory();
        plugin.getServer().getPluginManager().registerEvents(new DefaultController(this), plugin);
    }

    public CitizenLibrary(PluginBase plugin, Controllers customControllers) {
        this.plugin = plugin;
        this.factory = new Factory();

        plugin.getServer().getPluginManager().registerEvents(customControllers, plugin);
    }
}
