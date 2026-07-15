[![Ordinal Banner](https://raw.githubusercontent.com/MaboroshiKobo/branding/refs/heads/main/projects/ordinal/banners/ordinal_2048.png)](https://docs.maboroshi.org/projects/ordinal)

<div align="center">
  <p>
    <img alt="paper" height="56" src="https://cdn.jsdelivr.net/npm/@intergrav/devins-badges@3/assets/cozy/supported/paper_vector.svg">
    <img alt="purpur" height="56" src="https://cdn.jsdelivr.net/npm/@intergrav/devins-badges@3/assets/cozy/supported/purpur_vector.svg">
    <img alt="spigot" height="56" src="https://cdn.jsdelivr.net/npm/@intergrav/devins-badges@3/assets/cozy/unsupported/spigot_vector.svg">
  </p>

  <p>
    <a href="https://github.com/MaboroshiKobo/Ordinal"><img alt="github" height="56" src="https://cdn.jsdelivr.net/npm/@intergrav/devins-badges@3/assets/cozy/available/github_vector.svg"></a>
    <a href="https://hangar.papermc.io/Maboroshi/Ordinal"><img alt="hangar" height="56" src="https://cdn.jsdelivr.net/npm/@intergrav/devins-badges@3/assets/cozy/available/hangar_vector.svg"></a>
    <a href="https://modrinth.com/plugin/ordinal"><img alt="modrinth" height="56" src="https://cdn.jsdelivr.net/npm/@intergrav/devins-badges@3/assets/cozy/available/modrinth_vector.svg"></a>
  </p>

  <p>
    <a href="https://docs.maboroshi.org/projects/ordinal"><img alt="generic" height="56" src="https://raw.githubusercontent.com/MaboroshiKobo/branding/refs/heads/main/socials/128x/domain_icon_bg.png"></a>
    <a href="https://discord.maboroshi.org"><img alt="discord-singular" height="56" src="https://raw.githubusercontent.com/MaboroshiKobo/branding/refs/heads/main/socials/128x/discord_icon_bg.png"></a>
  </p>
</div>

## Player join order tracking and persistent seniority ranking

Ordinal is a utility plugin that tracks player join numbers and assigns persistent, sequential IDs to everyone on the server. It allows administrators to display unique entry numbers or seniority ranks across chat, tablists, and scoreboards.

## Features

* Assign unique, sequential entry numbers based on historical join order.
* Automatically parse and backfill ranking data for existing players on setup.
* Customize rank displays in other plugins using the PlaceholderAPI integration.

## Prerequisites

Ordinal is compatible with the following plugins:

* [PlaceholderAPI](https://placeholderapi.com/) (Optional)

## Documentation & Support

For configurations, commands, and permissions, check out our [wiki](https://docs.maboroshi.org/projects/ordinal). For bugs, questions, or updates, visit our [Discord server](https://discord.maboroshi.org) or open a [GitHub Issue](https://github.com/MaboroshiKobo/Ordinal/issues).

## Statistics

This plugin utilizes [bStats](https://bstats.org/plugin/bukkit/Ordinal/28615) to collect anonymous usage metrics.

![bStats Metrics](https://bstats.org/signatures/bukkit/Ordinal.svg)

## Building

To build the project from source, ensure you have a Java 25 environment configured.

```bash
./gradlew build
```
