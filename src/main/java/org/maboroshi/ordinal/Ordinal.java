package org.maboroshi.ordinal;

import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import org.bstats.bukkit.Metrics;
import org.bukkit.plugin.java.JavaPlugin;
import org.maboroshi.ordinal.command.OrdinalCommand;
import org.maboroshi.ordinal.config.ConfigManager;
import org.maboroshi.ordinal.hook.OrdinalExpansion;
import org.maboroshi.ordinal.listener.JoinListener;
import org.maboroshi.ordinal.manager.OrdinalManager;
import org.maboroshi.ordinal.util.Logger;
import org.maboroshi.ordinal.util.MessageUtils;
import org.maboroshi.ordinal.util.UpdateChecker;

public final class Ordinal extends JavaPlugin {
    private static Ordinal plugin;
    private Logger log;
    private ConfigManager configManager;
    private MessageUtils messageUtils;
    private OrdinalManager ordinalManager;

    @Override
    public void onEnable() {
        plugin = this;
        this.log = new Logger(this);
        if (!reload()) {
            log.error("Disabling plugin due to critical configuration error.");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        this.ordinalManager = new OrdinalManager(this);

        getServer().getPluginManager().registerEvents(new JoinListener(this), this);
        if (getServer().getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new OrdinalExpansion(this).register();
            getLogger().info("PlaceholderAPI hook enabled.");
        }

        @SuppressWarnings("unused")
        Metrics metrics = new Metrics(this, 28615);

        this.getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS, event -> {
            OrdinalCommand ordinalCommand = new OrdinalCommand(this);
            event.registrar().register(ordinalCommand.createCommand("ordinal"), "Main command");
        });

        new UpdateChecker(this).checkForUpdates();
    }

    public boolean reload() {
        try {
            if (this.configManager == null) {
                this.configManager = new ConfigManager(getDataFolder());
            }
            this.configManager.load();
            if (this.messageUtils == null) {
                this.messageUtils = new MessageUtils(this.configManager);
            }
            return true;
        } catch (Exception e) {
            log.error("Failed to load configuration: " + e.getMessage());
            return false;
        }
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public OrdinalManager getOrdinalManager() {
        return ordinalManager;
    }

    public MessageUtils getMessageUtils() {
        return messageUtils;
    }

    public Logger getPluginLogger() {
        return log;
    }
}
