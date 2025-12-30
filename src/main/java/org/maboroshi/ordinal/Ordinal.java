package org.maboroshi.ordinal;

import org.bukkit.plugin.java.JavaPlugin;
import org.maboroshi.ordinal.config.ConfigManager;
import org.maboroshi.ordinal.hook.OrdinalExpansion;
import org.maboroshi.ordinal.listener.JoinListener;
import org.maboroshi.ordinal.manager.OrdinalManager;
import org.bstats.bukkit.Metrics;

public final class Ordinal extends JavaPlugin {
    private ConfigManager configManager;
    private OrdinalManager ordinalManager;

    @Override
    public void onEnable() {
        this.configManager = new ConfigManager(this);
        this.ordinalManager = new OrdinalManager(this);

        getServer().getPluginManager().registerEvents(new JoinListener(this), this);
        if (getServer().getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new OrdinalExpansion(this).register();
            getLogger().info("PlaceholderAPI hook enabled.");
        }

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
