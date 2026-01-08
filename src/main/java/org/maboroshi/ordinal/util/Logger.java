package org.maboroshi.ordinal.util;

import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.maboroshi.ordinal.Ordinal;
import org.maboroshi.ordinal.config.ConfigManager;

public class Logger {
    private final ConfigManager config;

    public Logger(Ordinal plugin) {
        this.config = plugin.getConfigManager();
    }

    private void log(String colorTag, String message) {
        String prefix;
        if (config != null && config.getMessageConfig() != null) prefix = config.getMessageConfig().messages.prefix;
        else prefix = "<color:#74c7ec><bold>Ordinal</bold></color> <dark_gray>➟</dark_gray>";
        Bukkit.getConsoleSender().sendMessage(MiniMessage.miniMessage().deserialize(prefix + colorTag + message));
    }

    public void debug(String message) {
        if (config != null && config.getMainConfig().debug) log("<gray>[DEBUG] </gray>", message);
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
