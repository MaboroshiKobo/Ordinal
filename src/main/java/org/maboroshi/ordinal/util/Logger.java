package org.maboroshi.ordinal.util;

import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.maboroshi.ordinal.Ordinal;
import org.maboroshi.ordinal.config.ConfigManager;

public class Logger {
    private final Ordinal plugin;

    public Logger(Ordinal plugin) {
        this.plugin = plugin;
    }

    private void log(String colorTag, String message) {
        ConfigManager config = plugin.getConfigManager();
        String prefix = config.getMessageConfig().messages.prefix;
        Bukkit.getConsoleSender().sendMessage(MiniMessage.miniMessage().deserialize(prefix + colorTag + message));
    }

    public void debug(String message) {
        ConfigManager config = plugin.getConfigManager();
        if (config != null && config.getMainConfig().debug) {
            log("<gray>[DEBUG] </gray>", message);
        }
    }

    public void info(String message) {
        log("", message);
    }

    public void warn(String message) {
        log("<yellow>", message);
    }

    public void error(String message) {
        log("<red>", message);
    }
}
