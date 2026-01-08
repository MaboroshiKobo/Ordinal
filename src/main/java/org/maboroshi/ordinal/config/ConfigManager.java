package org.maboroshi.ordinal.config;

import java.io.File;
import org.maboroshi.ordinal.config.settings.MainConfig;
import org.maboroshi.ordinal.config.settings.MainConfig.MainConfiguration;
import org.maboroshi.ordinal.config.settings.MessageConfig;
import org.maboroshi.ordinal.config.settings.MessageConfig.MessageConfiguration;

public class ConfigManager {
    private final File dataFolder;

    private MainConfiguration mainConfig;
    private MessageConfiguration messageConfig;

    public ConfigManager(File dataFolder) {
        this.dataFolder = dataFolder;
    }

    public void load() {
        this.mainConfig = MainConfig.load(dataFolder);
        this.messageConfig = MessageConfig.load(dataFolder);
    }

    public MainConfiguration getMainConfig() {
        return mainConfig;
    }

    public MessageConfiguration getMessageConfig() {
        return messageConfig;
    }
}
