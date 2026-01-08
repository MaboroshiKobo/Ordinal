package org.maboroshi.ordinal.config.settings;

import de.exlll.configlib.Comment;
import de.exlll.configlib.ConfigLib;
import de.exlll.configlib.Configuration;
import de.exlll.configlib.YamlConfigurationProperties;
import de.exlll.configlib.YamlConfigurations;
import java.io.File;
import java.nio.file.Path;

public final class MainConfig {

    public static MainConfiguration load(File dataFolder) {
        YamlConfigurationProperties properties = ConfigLib.BUKKIT_DEFAULT_PROPERTIES.toBuilder().build();
        Path configFile = new File(dataFolder, "config.yml").toPath();
        return YamlConfigurations.update(configFile, MainConfiguration.class, properties);
    }

    @Configuration
    public static class SenioritySettings {
        @Comment("Should the seniority tracking module be enabled?")
        public boolean enabled = true;

        @Comment("Has the one-time scan of historic players been completed? Do not change unless you want to re-run migration.")
        public boolean migrationComplete = false;
    }

    @Configuration
    public static class MainConfiguration {
        @Comment("Should debug mode be enabled for detailed logs?")
        public boolean debug = false;

        @Comment("Settings related to player join order and veteran status.")
        public SenioritySettings seniority = new SenioritySettings();
    }
}
