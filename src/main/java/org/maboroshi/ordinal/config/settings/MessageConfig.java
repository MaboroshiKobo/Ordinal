package org.maboroshi.ordinal.config.settings;

import de.exlll.configlib.Comment;
import de.exlll.configlib.ConfigLib;
import de.exlll.configlib.Configuration;
import de.exlll.configlib.NameFormatters;
import de.exlll.configlib.YamlConfigurationProperties;
import de.exlll.configlib.YamlConfigurations;
import java.io.File;
import java.nio.file.Path;

public final class MessageConfig {
    public static MessageConfiguration load(File dataFolder) {
        YamlConfigurationProperties properties = ConfigLib.BUKKIT_DEFAULT_PROPERTIES.toBuilder()
                .setNameFormatter(NameFormatters.LOWER_KEBAB_CASE)
                .build();
        Path configFile = new File(dataFolder, "messages.yml").toPath();
        return YamlConfigurations.update(configFile, MessageConfiguration.class, properties);
    }

    @Configuration
    public static class MessageSettings {
        @Comment("Prefix for all messages sent by the plugin.")
        public String prefix = "<color:#74c7ec><bold>Ordinal</bold></color> <dark_gray>➟</dark_gray>";

        @Comment("Message displayed when the plugin is reloaded.")
        public String reloadSuccess = "<prefix> <green>Plugin configuration has been reloaded successfully.</green>";

        @Comment("Message displayed when the plugin fails to reload.")
        public String reloadFail =
                "<prefix> <red>Failed to reload plugin configuration! Check console for errors.</red>";

        @Comment("Message displayed when a new version of the plugin is available.")
        public String updateAvailable =
                "<prefix> A new version is available! <gray>(Current: <red><current_version></red> | Latest: <green><latest_version></green>)</gray>";

        @Comment("Message shown when using /ordinal command without arguments.")
        public String helpInfo =
                "<green>🛈</green> <gray>Type <white>/ordinal reload</white> to reload the configuration.</gray>";

        @Comment("Message shown when a player is not found for the reset command.")
        public String playerNotFound = "<prefix> <red>Player not found.</red>";

        @Comment("Message shown when a player's ordinal is successfully reset.")
        public String resetSuccess =
                "<prefix> <green>Reset complete for <white><player></white>. New Rank: <yellow><rank></yellow></green>";

        public MessageSettings() {}

        public MessageSettings(String prefix, String reloadSuccess, String reloadFail, String updateAvailable) {
            this.prefix = prefix;
            this.reloadSuccess = reloadSuccess;
            this.reloadFail = reloadFail;
            this.updateAvailable = updateAvailable;
        }
    }

    @Configuration
    public static class MessageConfiguration {
        @Comment("Settings related to messages sent by the plugin.")
        public MessageSettings messages = new MessageSettings();
    }
}
