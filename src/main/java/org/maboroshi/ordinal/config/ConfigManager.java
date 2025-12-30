package org.maboroshi.ordinal.config;

import org.maboroshi.ordinal.Ordinal;

public class ConfigManager {
    private final Ordinal plugin;

    public ConfigManager(Ordinal plugin) {
        this.plugin = plugin;
        plugin.saveDefaultConfig();
    }

    public int getNextOrdinal() {
        return plugin.getConfig().getInt("registry.next-ordinal", 1);
    }
    
    public void setNextOrdinal(int nextOrdinal) {
        plugin.getConfig().set("registry.next-ordinal", nextOrdinal);
        plugin.saveConfig();
    }

    public boolean isMigrationComplete() {
        return plugin.getConfig().getBoolean("registry.migration-complete", false);
    }

    public void setMigrationComplete(boolean complete) {
        plugin.getConfig().set("registry.migration-complete", complete);
        plugin.saveConfig();
    }
}
