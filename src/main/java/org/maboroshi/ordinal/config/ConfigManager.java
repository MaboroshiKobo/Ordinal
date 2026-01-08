package org.maboroshi.ordinal.config;

import de.exlll.configlib.ConfigLib;
import de.exlll.configlib.NameFormatters;
import de.exlll.configlib.YamlConfigurationProperties;
import de.exlll.configlib.YamlConfigurations;
import java.io.File;
import java.nio.file.Path;
import org.maboroshi.ordinal.config.data.OrdinalData;
import org.maboroshi.ordinal.config.settings.MainConfig;
import org.maboroshi.ordinal.config.settings.MainConfig.MainConfiguration;
import org.maboroshi.ordinal.config.settings.MessageConfig;
import org.maboroshi.ordinal.config.settings.MessageConfig.MessageConfiguration;

public class ConfigManager {
    private final File dataFolder;

    private MainConfiguration mainConfig;
    private MessageConfiguration messageConfig;

    public OrdinalData ordinalData;

    private Path dataPath;

    public ConfigManager(File dataFolder) {
        this.dataFolder = dataFolder;
    }

    public void load() {
        this.mainConfig = MainConfig.load(dataFolder);
        this.messageConfig = MessageConfig.load(dataFolder);

        YamlConfigurationProperties properties = ConfigLib.BUKKIT_DEFAULT_PROPERTIES.toBuilder()
                .setNameFormatter(NameFormatters.LOWER_KEBAB_CASE)
                .build();
        this.dataPath = new File(dataFolder, "data.yml").toPath();
        this.ordinalData = YamlConfigurations.update(dataPath, OrdinalData.class, properties);
    }

    public void updateNextOrdinal(int newValue) {
        this.ordinalData.nextOrdinal = newValue;
        saveData();
    }

    private void saveData() {
        YamlConfigurations.save(dataPath, OrdinalData.class, ordinalData);
    }

    public MainConfiguration getMainConfig() {
        return mainConfig;
    }

    public MessageConfiguration getMessageConfig() {
        return messageConfig;
    }

    public OrdinalData getOrdinalData() {
        return ordinalData;
    }
}
