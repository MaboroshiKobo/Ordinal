<div align="center">
  <h3>Ordinal</h3>
  <p>The lightweight seniority and join order tracking solution for Paper servers</p>
</div>

***

**Ordinal** is a lightweight **utility plugin** designed to assign persistent, **sequential join IDs** (Ordinal Ranks) to every player on your server.

This plugin is ideal for servers wanting to implement a **seniority system** to recognize long-time members, display distinct ranks in chat, or simply maintain an organized player database.

### Features

Ordinal handles seniority tracking with a focus on these core functions:

* It assigns unique **sequential IDs** (e.g., `#1`, `#67`) based on strict join order.
* It includes a **smart migration** system that automatically calculates and backfills seniority for existing players based on historical join dates.
* It features native **PlaceholderAPI** support to display seniority ranks using `%ordinal_rank%`.
* It supports fully configurable messages using [MiniMessage](https://docs.papermc.io/adventure/minimessage/format) formatting.

### Prerequisites

To use this plugin, your server must be running **Paper**, **Purpur**, or **Folia** on `1.21` or higher. It requires **Java 21**.

#### Dependencies

* [PlaceholderAPI](https://placeholderapi.com/)

### Documentation & Support

For a complete guide on features, commands, and configuration, please visit our [Wiki](https://docs.maboroshi.org/). If you have questions or need to report a bug, join our [Discord server](https://discord.maboroshi.org) or open an issue on [GitHub](https://github.com/MaboroshiKobo/Ordinal/issues).

### Statistics

This plugin utilizes [bStats](https://bstats.org/plugin/bukkit/Ordinal/28615) to collect anonymous usage metrics.

![bStats Metrics](https://bstats.org/signatures/bukkit/Ordinal.svg)

## Building

If you wish to build the project from source, ensure you have a Java 21 environment configured.

```bash
./gradlew build
```
