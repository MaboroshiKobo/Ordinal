package org.maboroshi.ordinal;

import org.bukkit.plugin.java.JavaPlugin;
import org.maboroshi.ordinal.config.ConfigManager;
import org.maboroshi.ordinal.listener.JoinListener;
import org.maboroshi.ordinal.manager.OrdinalManager;
import org.bstats.bukkit.Metrics;

public final class Ordinal extends JavaPlugin {
    private static Ordinal plugin;
    private ConfigManager configManager;
    private OrdinalManager ordinalManager;

    @Override
    public void onEnable() {
        plugin = this;
        this.configManager = new ConfigManager(this);
        this.ordinalManager = new OrdinalManager(this);

        getServer().getPluginManager().registerEvents(new JoinListener(this), this);

        @SuppressWarnings("unused")
        Metrics metrics = new Metrics(this, 28615);
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public OrdinalManager getOrdinalManager() {
        return ordinalManager;
    }
}
