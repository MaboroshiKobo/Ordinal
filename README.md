# Ordinal

Ordinal is a plugin that assigns persistent, sequential join IDs (Ordinal ranks) to players with PlaceholderAPI support. The purpose of the plugin is to provide a lightweight, database-free way to track join order and recognize veteran players on your server.

## Server Compatibility

Ordinal is compatible with **Paper** and its forks. It is recommended to use Paper to run your server.

This plugin supports Minecraft versions **1.21** and newer running **Java 21**.

## Installation

1. Download the latest JAR file from the **Releases** page.
2. Place the file into your server's `plugins` directory.
3. Restart the server.

*Note: [PlaceholderAPI](https://placeholderapi.com/) is required for placeholders to work.*

## Placeholders and Permissions

| Feature | Usage / Node | Description |
| :--- | :--- | :--- |
| **Ordinal Rank** | `%ordinal_rank%` | Displays the player's unique join number (e.g., `67`). |
| **Update Alerts** | `ordinal.admin` | Allows the player to receive notification messages when a new version is available. |

## Configuration

This plugin uses a simple configuration system located in the `plugins/Ordinal/` directory.

* `config.yml`: General settings for the ID registry and migration status.

## Building from Source

To build this project locally, ensure you have **JDK 21** or newer installed.

* On Linux or macOS: `./gradlew build`
* On Windows: `gradlew build`

The compiled artifact will be located in `build/libs/`.
