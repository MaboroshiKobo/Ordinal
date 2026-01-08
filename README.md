**Ordinal** is a lightweight **utility plugin** created to assign persistent, **sequential join IDs** (Ordinal Ranks) to every player on your server!

This plugin is ideal for servers wanting to have a **seniority system** to recognize long-time members, display distinct ranks in chat, or simply maintain an organized player database.

### Features

**Ordinal** provides a modern approach to **seniority tracking** with these core capabilities:
* **Sequential Seniority Ranks:** Assigns unique IDs (e.g., `#1`, `#67`) based on strict join order.
* **Smart Migration:** automatically calculates and backfills **seniority** for existing players based on historical join dates.
* **Native PlaceholderAPI support** to display **seniority ranks** in chat, tablists, and scoreboards using `%ordinal_rank%`.
* **Fully configurable messages** using [MiniMessage](https://docs.papermc.io/adventure/minimessage/format) formatting.

### Documentation

For a complete guide on features, commands, permissions, and configuration, please visit our [Wiki](https://docs.maboroshi.org/).

### Support

If you have questions, want to report a bug, or need help, please join our [Discord server](https://discord.maboroshi.org). Alternatively, you may open an issue on [GitHub](https://github.com/MaboroshiKobo/Ordinal/issues).

### Statistics

This plugin utilizes [bStats](https://bstats.org/plugin/bukkit/Ordinal/28615) to collect anonymous usage metrics.

![bStats Metrics](https://bstats.org/signatures/bukkit/Ordinal.svg)

## Building

```bash
./gradlew build
```

The compiled JAR will be in `build/libs/`.
